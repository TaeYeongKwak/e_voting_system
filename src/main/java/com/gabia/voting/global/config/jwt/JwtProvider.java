package com.gabia.voting.global.config.jwt;

import com.gabia.voting.client.entity.Role;
import com.gabia.voting.client.exception.ClientDoesNotHaveRoleException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Slf4j
public class JwtProvider {

    private Key key;
    private long tokenValidMillisecond;

    public JwtProvider(String secretKey, long tokenValidMillisecond) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.tokenValidMillisecond = tokenValidMillisecond;
    }

    public String createToken(Long clientPk, Set<Role> roles){
        Claims claims = Jwts.claims().setSubject(Long.toString(clientPk));
        claims.put("roles", convertRoleString(roles));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = this.getTokenClaims(token).getBody();
        String roleString = claims.get("roles", String.class);
        Set<GrantedAuthority> authorities = convertAuthorities(roleString);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validToken(String token){
        try {
            Claims claims = this.getTokenClaims(token).getBody();
            return (claims != null) && claims.getExpiration().after(new Date());
        }catch(Exception e) {
            return false;
        }
    }

    private Jws<Claims> getTokenClaims(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException e) {
            log.info("JWT 서명이 옳바르지 않습니다. : " + token);
        } catch (MalformedJwtException e) {
            log.info("JWT 토큰이 옳바르지 않습니다. : " + token);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다. : " + token);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다. : " + token);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰 압축이 옳바르지 않습니다. : " + token);
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private String convertRoleString(Set<Role> roles){
        try{
            StringBuffer roleString = new StringBuffer();
            for (Role role : roles){
                roleString.append(role.getAuthorityName() + " ");
            }
            return roleString.toString();
        }catch (NullPointerException e){
            throw new ClientDoesNotHaveRoleException();
        }
    }

    private Set<GrantedAuthority> convertAuthorities(String roleString){
        StringTokenizer stringTokenizer = new StringTokenizer(roleString);
        Set<GrantedAuthority> authorities = new HashSet<>();
        while(stringTokenizer.hasMoreTokens()){
            authorities.add(new SimpleGrantedAuthority(stringTokenizer.nextToken()));
        }
        return authorities;
    }

}
