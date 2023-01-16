package com.gabia.voting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.service.ClientService;
import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.dto.SaveVoteDTO;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.item.service.ItemService;
import com.gabia.voting.item.type.VoteType;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.service.VotingResultService;
import com.gabia.voting.votingResult.type.OpinionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@EnableMockMvc
@AutoConfigureRestDocs
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class VotingResultAPITest {

    private final String BASE_URI = "/api/v0";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VotingResultService votingResultService;

    private Item item;
    private Client client;

    @BeforeEach
    public void setUp(){
        SaveClientDTO saveClientDTO = SaveClientDTO.builder()
                .clientId("TEST_USERID")
                .password("TEST_PASSWORD")
                .clientName("SHAREHOLDER")
                .clientType(ClientType.ROLE_SHAREHOLDER)
                .votingRightCount(10)
                .build();

        clientService.registryClient(saveClientDTO);

        client = clientRepository.findClientByClientId(saveClientDTO.getClientId()).get();

        SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE", "TEST_CONTENT");
        item = itemRepository.save(saveItemDTO.toEntity());

        LocalDateTime now = LocalDateTime.now();
        SaveVoteDTO saveVoteDTO = new SaveVoteDTO(
                now.minusDays(1),
                now.plusDays(1),
                VoteType.FIRST_SERVED_LIMITED
        );

        Vote vote = voteRepository.save(saveVoteDTO.toEntity(item));
        item.setVote(vote);
    }

    @Test
    @DisplayName("의결권 사용")
    @WithMockUser(username = "TEST_MANAGER", password = "TEST_PASSWORD", roles = {"USER", "SHAREHOLDER"})
    public void voting_test() throws Exception {
        // given
        VoteRequestDTO voteRequestDTO = VoteRequestDTO.builder()
                .count(10)
                .opinion(OpinionType.AGREEMENT)
                .build();

        String json = objectMapper.writeValueAsString(voteRequestDTO);

        // when
        mvc.perform(RestDocumentationRequestBuilders.post( BASE_URI + "/vote/{item-pk}/client/{client-pk}",
                                item.getItemPk(),
                                client.getClientPk())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer {token}")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("use-voting-right",
                        pathParameters(
                                parameterWithName("item-pk").description("안건 번호"),
                                parameterWithName("client-pk").description("사용자 번호")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("count").description("사용할 의결권 수"),
                                fieldWithPath("opinion").description("의견")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지")
                        )));

    }

    @Test
    @DisplayName("투표 결과 조회")
    public void showVoteResult_test() throws Exception {
        // given
        Client manager = Client.builder()
                .clientId("TEST_MANAGER")
                .password("TEST_PASSWORD")
                .clientName("MANAGER")
                .clientType(ClientType.ROLE_MANAGER)
                .build();

        manager = clientRepository.save(manager);

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

        SecurityContext contextHolder = SecurityContextHolder.getContext();
        contextHolder.setAuthentication(new UsernamePasswordAuthenticationToken(manager.getClientPk(), "", authorities));

        VoteRequestDTO voteRequestDTO = VoteRequestDTO.builder()
                .count(10)
                .opinion(OpinionType.AGREEMENT)
                .build();

        votingResultService.useVotingRight(item.getItemPk(), client.getClientPk(), voteRequestDTO);

        // when
        mvc.perform(RestDocumentationRequestBuilders.get( BASE_URI + "/vote/{item-pk}",
                                item.getItemPk())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer {token}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("vote-result-voting",
                        pathParameters(
                                parameterWithName("item-pk").description("안건 번호")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지"),
                                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.shareholderResult").description("주주 권한 투표 결과"),
                                fieldWithPath("data.shareholderResult.agreementCount").description("안건 동의 의결권 수"),
                                fieldWithPath("data.shareholderResult.oppositionCount").description("안건 반대 의결권 수"),
                                fieldWithPath("data.shareholderResult.giveUpCount").description("안건 기권 의결권 수"),
                                subsectionWithPath("data.managerResult").type(JsonFieldType.ARRAY).description("관리자 권한 투표 결과"),
                                fieldWithPath("data.managerResult.[].clientName").description("주주 이름"),
                                fieldWithPath("data.managerResult.[].opinionType").description("주주 의견"),
                                fieldWithPath("data.managerResult.[].count").description("사용 의결권 수"),
                                fieldWithPath("data.managerResult.[].createdTime").description("투표 시간")
                        )));

    }



}
