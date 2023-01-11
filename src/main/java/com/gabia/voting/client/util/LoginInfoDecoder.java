package com.gabia.voting.client.util;

import com.gabia.voting.client.dto.LoginRequestDTO;
import com.gabia.voting.client.exception.LoginInfoFailDecodingException;
import com.gabia.voting.client.exception.UnformattedHeaderException;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.StringTokenizer;

@Slf4j
public class LoginInfoDecoder {

    public static String BASIC_AUTH_PREFIX = "Basic ";

    public static LoginRequestDTO basicAuthDecoder(String encodeLoginInfo){
        if (encodeLoginInfo == null || !encodeLoginInfo.startsWith(BASIC_AUTH_PREFIX)){
            throw new UnformattedHeaderException();
        }

        try{
            byte[] decodingBytes = Base64.getDecoder().decode(encodeLoginInfo.substring(BASIC_AUTH_PREFIX.length()).getBytes());
            String decodedLoginInfo = new String(decodingBytes);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedLoginInfo, ":");
            return new LoginRequestDTO(stringTokenizer.nextToken(), stringTokenizer.nextToken());
        }catch (Exception e){
            log.debug(e.getMessage(), e);
            throw new LoginInfoFailDecodingException();
        }
    }
}
