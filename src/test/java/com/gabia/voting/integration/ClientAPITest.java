package com.gabia.voting.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.type.ClientType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
        ResultActions resultActions = signupTest(json);

        // then
        resultActions.andReturn();

    }

    private ResultActions signupTest(String saveJson) throws Exception {
        return mvc.perform(RestDocumentationRequestBuilders.post( BASE_URI + "/client")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(saveJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("client-signup",
                        requestFields(
                                fieldWithPath("clientId").description("사용자 아이디"),
                                fieldWithPath("password").description("사용자 비밀번호"),
                                fieldWithPath("clientName").description("사용자 이름"),
                                fieldWithPath("clientType").description("사용자 종류"),
                                fieldWithPath("votingRightCount").description("주주 의결권 수").optional()
                        )));

    }

}
