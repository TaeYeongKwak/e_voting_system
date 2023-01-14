package com.gabia.voting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.voting.item.dto.ModifyVoteDTO;
import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.dto.SaveVoteDTO;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.item.service.ItemService;
import com.gabia.voting.item.type.VoteType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableMockMvc
@AutoConfigureRestDocs
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class ItemAPITest {

    private final String BASE_URI = "/api/v0";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("안건 등록")
    @WithMockUser(username = "TEST_MANAGER", password = "TEST_PASSWORD", roles = {"USER", "MANAGER"})
    public void registryItem_test() throws Exception {
        // given
        SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE", "TEST_CONTENT");

        String json = objectMapper.writeValueAsString(saveItemDTO);

        // when
        mvc.perform(RestDocumentationRequestBuilders.post( BASE_URI + "/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer {token}")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("item-registry",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("itemTitle").description("안건 제목"),
                                fieldWithPath("itemContent").description("안건 내용")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지")
                        )));

    }

    @Test
    @DisplayName("안건 목록 조회")
    @WithMockUser(username = "TEST_USER", password = "TEST_PASSWORD", roles = {"USER"})
    public void showItemList_test() throws Exception {
        // given
        for (int i = 1; i <= 3; i++){
            SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE_" + i, "TEST_CONTENT_" + i);
            itemService.registryItem(saveItemDTO);
        }

        // when
        FieldDescriptor[] item = new FieldDescriptor[]{
                fieldWithPath("itemPk").type(JsonFieldType.NUMBER).description("안건 번호"),
                fieldWithPath("itemTitle").type(JsonFieldType.STRING).description("안건 제목"),
                fieldWithPath("canVoting").type(JsonFieldType.BOOLEAN).description("투표 활성화 유무"),
                fieldWithPath("createdTime").type(JsonFieldType.STRING).description("안건 생성시간")
        };

        mvc.perform(RestDocumentationRequestBuilders.get( BASE_URI + "/item")
                        .header("Authorization", "Bearer {token}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("item-show-list",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지"),
                                subsectionWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")

                        ).andWithPrefix("data.[].", item)));

    }

    @Test
    @DisplayName("안건 상세 조회")
    @WithMockUser(username = "TEST_USER", password = "TEST_PASSWORD", roles = {"USER"})
    public void showDetailItem_test() throws Exception {
        // given
        SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE", "TEST_CONTENT");
        Item itemEntity = itemRepository.save(saveItemDTO.toEntity());

        // when
        FieldDescriptor[] item = new FieldDescriptor[]{
                fieldWithPath("itemPk").type(JsonFieldType.NUMBER).description("안건 번호"),
                fieldWithPath("itemTitle").type(JsonFieldType.STRING).description("안건 제목"),
                fieldWithPath("canVoting").type(JsonFieldType.BOOLEAN).description("투표 활성화 유무"),
                fieldWithPath("createdTime").type(JsonFieldType.STRING).description("안건 생성시간")
        };

        mvc.perform(RestDocumentationRequestBuilders.get( BASE_URI + "/item/{item-pk}", itemEntity.getItemPk())
                        .header("Authorization", "Bearer {token}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("item-show-detail",
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
                                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")

                        ).andWithPrefix("data.", item)));

    }

    @Test
    @DisplayName("안건 삭제")
    @WithMockUser(username = "TEST_USER", password = "TEST_PASSWORD", roles = {"USER", "MANAGER"})
    public void deleteItem_test() throws Exception {
        // given
        SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE", "TEST_CONTENT");
        Item itemEntity = itemRepository.save(saveItemDTO.toEntity());

        // when
        mvc.perform(RestDocumentationRequestBuilders.delete( BASE_URI + "/item/{item-pk}", itemEntity.getItemPk())
                        .header("Authorization", "Bearer {token}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("item-delete",
                        pathParameters(
                                parameterWithName("item-pk").description("안건 번호")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지")
                        )));

    }

    @Test
    @DisplayName("투표 등록")
    @WithMockUser(username = "TEST_USER", password = "TEST_PASSWORD", roles = {"USER", "MANAGER"})
    public void postVote_test() throws Exception {
        // given
        SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE", "TEST_CONTENT");
        Item itemEntity = itemRepository.save(saveItemDTO.toEntity());

        LocalDateTime now = LocalDateTime.now();
        SaveVoteDTO saveVoteDTO = new SaveVoteDTO(
                now.minusDays(1),
                now.plusDays(1),
                VoteType.FIRST_SERVED_LIMITED
        );

        String json = objectMapper.writeValueAsString(saveVoteDTO);

        // when
        mvc.perform(RestDocumentationRequestBuilders.post( BASE_URI + "/item/{item-pk}/vote", itemEntity.getItemPk())
                        .header("Authorization", "Bearer {token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("item-post-vote",
                        pathParameters(
                                parameterWithName("item-pk").description("안건 번호")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("startTime").description("투표 시작 시간"),
                                fieldWithPath("endTime").description("투표 종료 시간"),
                                fieldWithPath("voteType").description("투표 종류")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지")
                        )));

    }

    @Test
    @DisplayName("투표 시간 변경")
    @WithMockUser(username = "TEST_USER", password = "TEST_PASSWORD", roles = {"USER", "MANAGER"})
    public void modifyVote_test() throws Exception {
        // given
        SaveItemDTO saveItemDTO = new SaveItemDTO("TEST_TITLE", "TEST_CONTENT");
        Item itemEntity = itemRepository.save(saveItemDTO.toEntity());

        LocalDateTime now = LocalDateTime.now();
        SaveVoteDTO saveVoteDTO = new SaveVoteDTO(
                now.minusDays(1),
                now.plusDays(1),
                VoteType.FIRST_SERVED_LIMITED
        );

        itemService.postVote(itemEntity.getItemPk(), saveVoteDTO);

        ModifyVoteDTO modifyVoteDTO = new ModifyVoteDTO(now, now);

        String json = objectMapper.writeValueAsString(modifyVoteDTO);

        // when
        mvc.perform(RestDocumentationRequestBuilders.put( BASE_URI + "/item/{item-pk}/vote", itemEntity.getItemPk())
                        .header("Authorization", "Bearer {token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("item-modify-vote",
                        pathParameters(
                                parameterWithName("item-pk").description("안건 번호")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("startTime").description("투표 시작 시간"),
                                fieldWithPath("endTime").description("투표 종료 시간")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("상태 메시지")
                        )));

    }

}
