package com.gabia.voting.votingResult.service;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.dto.VoteResultInfoDTO;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class VotingResultServiceImpl implements VotingResultService{

    private final VotingResultRepository votingResultRepository;
    private final ItemRepository itemRepository;
    private final VoteRepository voteRepository;
    private final ClientRepository clientRepository;
    private final VotingRightRepository votingRightRepository;

    @Transactional
    @Override
    public void useVotingRight(Long itemPk, Long clientPk, VoteRequestDTO voteRequestDTO) {

    }

    @Override
    public VoteResultInfoDTO getVoteResult(Long itemPk) {
        return null;
    }
}
