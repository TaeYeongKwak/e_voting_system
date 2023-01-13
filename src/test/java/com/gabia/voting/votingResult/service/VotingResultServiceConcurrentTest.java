package com.gabia.voting.votingResult.service;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.item.type.VoteType;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.exception.ExceedLimitedVotingRightCountException;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.type.OpinionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

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

    private Item item;
    private Vote vote;

    private List<Client> clientList;
    private List<VotingRight> votingRightList;


    @BeforeEach
    private void setUp(){
        clientList = new ArrayList<>();
        votingRightList = new ArrayList<>();

        // 사용자 저장
        for (int i = 1; i <= USER_NUM; i++){
            Client client = Client.builder()
                    .clientId("testId" + i)
                    .password("testPassword")
                    .clientName("testUser" + i)
                    .build();
            clientList.add(client);

            VotingRight votingRight = new VotingRight(client, 10);
            votingRightList.add(votingRight);
        }

        clientRepository.saveAllAndFlush(clientList);
        votingRightRepository.saveAllAndFlush(votingRightList);
        
        // 안건 생성
        item = Item.builder()
                .itemTitle("testItem")
                .itemContent("testContent")
                .build();

        item = itemRepository.saveAndFlush(item);
    }

    @Test
    @DisplayName("선착순 방식 투표")
    public void first_served_limited_vote_test() throws InterruptedException {
        // given
        vote = Vote.builder()
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
        ExecutorService executor = Executors.newFixedThreadPool(USER_NUM * 5);
        CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);

        for(int i = 1; i <= USER_NUM; i++){
            Client client = clientList.get(i - 1);
            VoteRequestDTO voteRequestDTO = dtoList.get(i - 1);
            executor.submit(() -> {
                try{
                    votingResultService.useVotingRight(item.getItemPk(), client.getClientPk(), voteRequestDTO);
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        // then
        List<VotingResult> votingResultList = votingResultRepository.findAll();
        int count = votingResultList.stream().mapToInt(vr -> vr.getCount()).sum();
        assertThat(count).isEqualTo(10);
    }


}
