package com.gabia.voting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.service.ClientService;
import com.gabia.voting.client.type.ClientType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import java.util.Base64;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@EnableMockMvc
@AutoConfigureRestDocs
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class ClientAPITest {

    private final String BASE_URI = "/api/v0";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    @DisplayName("주주 회원가입")
    public void signup_shareholder_test() throws Exception {
        // given
        SaveClientDTO saveClientDTO = SaveClientDTO.builder()
                .clientId("TEST_USERID")
                .password("TEST_PASSWORD")
                .clientName("SHAREHOLDER")
                .clientType(ClientType.ROLE_SHAREHOLDER)
                .votingRightCount(10)
                .build();

        String json = objectMapper.writeValueAsString(saveClientDTO);

        // when
        signupTest(json, "shareholder");
    }

    @Test
    @DisplayName("관리자 회원가입")
    public void signup_manager_test() throws Exception {
        // given
        SaveClientDTO saveClientDTO = SaveClientDTO.builder()
                .clientId("TEST_USERID")
                .password("TEST_PASSWORD")
                .clientName("MANAGER")
                .clientType(ClientType.ROLE_MANAGER)
                .build();

        String json = objectMapper.writeValueAsString(saveClientDTO);

        // when
        signupTest(json, "manager");
    }

    private ResultActions signupTest(String saveJson, String clientType) throws Exception {
        return mvc.perform(RestDocumentationRequestBuilders.post( BASE_URI + "/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(saveJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("client-signup-"+clientType,
                        requestFields(
                                fieldWithPath("clientId").description("사용자 아이디"),
                                fieldWithPath("password").description("사용자 비밀번호"),
                                fieldWithPath("clientName").description("사용자 이름"),
                                fieldWithPath("clientType").description("사용자 종류"),
                                fieldWithPath("votingRightCount").description("주주 의결권 수").optional()
                        ),
                        responseFields(
                            fieldWithPath("success").description("성공 여부"),
                            fieldWithPath("code").description("상태 코드"),
                            fieldWithPath("message").description("상태 메시지")
                        )));

    }

    @Test
    @DisplayName("사용자 로그인")
    public void login_test_test() throws Exception {
        // given
        SaveClientDTO saveClientDTO = SaveClientDTO.builder()
                .clientId("TEST_USERID")
                .password("TEST_PASSWORD")
                .clientName("SHAREHOLDER")
                .clientType(ClientType.ROLE_SHAREHOLDER)
                .votingRightCount(10)
                .build();

        clientService.registryClient(saveClientDTO);
        String credentials = saveClientDTO.getClientId() + ":" + saveClientDTO.getPassword();
        String encodingCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // when
        mvc.perform(RestDocumentationRequestBuilders.post( BASE_URI + "/clients/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + encodingCredentials))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("client-login", requestHeaders(
                        headerWithName("Authorization").description("Basic auth credentials")
                ), responseFields(
                        fieldWithPath("success").description("성공 여부"),
                        fieldWithPath("code").description("상태 코드"),
                        fieldWithPath("message").description("상태 메시지"),
                        fieldWithPath("data").description("응답 데이터")
                )));
    }

}
