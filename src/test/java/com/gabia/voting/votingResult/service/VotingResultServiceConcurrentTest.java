package com.gabia.voting.votingResult.service;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.item.type.VoteType;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.type.OpinionType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class VotingResultServiceConcurrentTest {

    private final int USER_NUM = 5;

    @Autowired
    private VotingResultService votingResultService;

    @Autowired
    private VotingResultRepository votingResultRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private VotingRightRepository votingRightRepository;

    private List<Client> clientList;
    private List<VotingRight> votingRightList;


    @BeforeAll
    private void setUp(){
        clientList = new ArrayList<>();
        votingRightList = new ArrayList<>();

        // 사용자 저장
        for (int i = 1; i <= USER_NUM; i++){
            Client client = Client.builder()
                    .clientId("testId" + i)
                    .password("testPassword")
                    .clientName("testUser" + i)
                    .clientType(ClientType.ROLE_SHAREHOLDER)
                    .build();

            clientList.add(client);

            VotingRight votingRight = new VotingRight(client, 10);
            votingRightList.add(votingRight);
        }

        clientRepository.saveAll(clientList);
        votingRightRepository.saveAll(votingRightList);

    }

    @Test
    @DisplayName("선착순 방식 투표")
    public void first_served_limited_vote_test() throws InterruptedException {
        // given
        Item item = Item.builder()
                .itemTitle("testItem_first_served")
                .itemContent("testContent_first_served")
                .build();

        item = itemRepository.saveAndFlush(item);

        Vote vote = Vote.builder()
                .item(item)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().plusDays(1))
                .voteType(VoteType.FIRST_SERVED_LIMITED)
                .build();

        vote = voteRepository.save(vote);

        List<VoteRequestDTO> dtoList = new ArrayList<>();
        for(int i = 1; i <= USER_NUM; i++){
            VotingRight votingRight = votingRightList.get(i - 1);
            VoteRequestDTO voteRequestDTO = VoteRequestDTO.builder()
                    .count(3)
                    .opinion(i % 2 == 0? OpinionType.AGREEMENT : OpinionType.OPPOSITION)
                    .build();
            dtoList.add(voteRequestDTO);
        }

        // when
        ExecutorService executor = Executors.newFixedThreadPool(USER_NUM);
        CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);

        for(int i = 1; i <= USER_NUM; i++){
            Client client = clientList.get(i - 1);
            VoteRequestDTO voteRequestDTO = dtoList.get(i - 1);
            Item finalItem = item;
            executor.submit(() -> {
                try{
                    votingResultService.useVotingRight(finalItem.getItemPk(), client.getClientPk(), voteRequestDTO);
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        // then
        List<VotingResult> votingResultList = votingResultRepository.findAllByVote(vote);
        int count = votingResultList.stream().mapToInt(vr -> vr.getCount()).sum();
        assertThat(count).isEqualTo(10);
    }

    @Test
    @DisplayName("무제한 방식")
    public void unlimited_vote_test() throws InterruptedException {
        // given
        Item item = Item.builder()
                .itemTitle("testItem_unlimited")
                .itemContent("testContent_unlimited")
                .build();

        item = itemRepository.saveAndFlush(item);

        Vote vote = Vote.builder()
                .item(item)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().plusDays(1))
                .voteType(VoteType.UNLIMITED)
                .build();

        vote = voteRepository.save(vote);

        List<VoteRequestDTO> dtoList = new ArrayList<>();
        for(int i = 1; i <= USER_NUM; i++){
            VotingRight votingRight = votingRightList.get(i - 1);
            VoteRequestDTO voteRequestDTO = VoteRequestDTO.builder()
                    .count(3)
                    .opinion(i % 2 == 0? OpinionType.AGREEMENT : OpinionType.OPPOSITION)
                    .build();
            dtoList.add(voteRequestDTO);
        }

        // when
        ExecutorService executor = Executors.newFixedThreadPool(USER_NUM);
        CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);

        for(int i = 1; i <= USER_NUM; i++){
            Client client = clientList.get(i - 1);
            VoteRequestDTO voteRequestDTO = dtoList.get(i - 1);
            Item finalItem = item;
            executor.submit(() -> {
                try{
                    votingResultService.useVotingRight(finalItem.getItemPk(), client.getClientPk(), voteRequestDTO);
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        // then
        List<VotingResult> votingResultList = votingResultRepository.findAllByVote(vote);
        int count = votingResultList.stream().mapToInt(vr -> vr.getCount()).sum();
        assertThat(count).isEqualTo(15);
    }

}
