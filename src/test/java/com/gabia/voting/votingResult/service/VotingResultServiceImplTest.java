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
import com.gabia.voting.votingResult.dto.OpinionCountDTO;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.dto.VoteResultInfoDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.type.OpinionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VotingResultServiceImplTest {


    @InjectMocks
    private VotingResultServiceImpl votingResultService;

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private VotingRightRepository votingRightRepository;
    @Mock
    private VotingResultRepository votingResultRepository;

    private Client client;
    private VotingRight votingRight;
    private Item item;
    private Vote vote;
    private VotingResult votingResult;

    @BeforeEach
    public void setUp(){
        client = Client.builder()
                .clientPk(1L)
                .clientId("testId")
                .password("testPassword")
                .clientName("testUser")
                .build();

        votingRight = new VotingRight(1L, client, 5);

        vote = Vote.builder()
                .votePk(1L)
                .item(item)
                .voteType(VoteType.FIRST_SERVED_LIMITED)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().plusDays(1))
                .build();

        item = Item.builder()
                .itemPk(1L)
                .itemTitle("testItem")
                .itemContent("testContent")
                .vote(vote)
                .build();

        votingResult = VotingResult.builder()
                .votingResultPk(1L)
                .votingRight(votingRight)
                .vote(vote)
                .count(5)
                .opinion(OpinionType.AGREEMENT)
                .build();
    }

    @Test
    public void userVotingRight_success_test(){
        // given
        VoteRequestDTO voteRequestDTO = VoteRequestDTO.builder()
                .count(5)
                .opinion(OpinionType.AGREEMENT)
                .build();

        Long itemPk = item.getItemPk();
        Long clientPk = client.getClientPk();

        given(clientRepository.findById(any())).willReturn(Optional.of(client));
        given(votingRightRepository.findByClient(any())).willReturn(Optional.of(votingRight));
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        given(votingResultRepository.findAllByVote(any())).willReturn(Collections.emptyList());
        given(votingResultRepository.save(any())).willReturn(votingResult);

        // when
        votingResultService.useVotingRight(itemPk, clientPk, voteRequestDTO);

        // then
        verify(votingResultRepository, times(1)).save(any());
    }
}
