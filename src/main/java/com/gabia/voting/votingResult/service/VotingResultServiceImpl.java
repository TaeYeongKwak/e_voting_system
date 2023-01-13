package com.gabia.voting.votingResult.service;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.exception.ClientNotFoundException;
import com.gabia.voting.client.exception.VotingRightNotFoundException;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.exception.ItemNotFoundException;
import com.gabia.voting.item.exception.NotActiveVoteException;
import com.gabia.voting.item.exception.VoteNotFoundException;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.votingResult.dto.OpinionCountDTO;
import com.gabia.voting.votingResult.dto.SimpleVotingResultDTO;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.dto.VoteResultInfoDTO;
import com.gabia.voting.votingResult.exception.ExceedLimitedVotingRightCountException;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.strategy.VoteStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingResultServiceImpl implements VotingResultService{

    private final VotingResultRepository votingResultRepository;
    private final ItemRepository itemRepository;
    private final VoteRepository voteRepository;
    private final ClientRepository clientRepository;
    private final VotingRightRepository votingRightRepository;

    @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    @Transactional
    @Override
    public void useVotingRight(Long itemPk, Long clientPk, VoteRequestDTO voteRequestDTO) {
        Client client = clientRepository.findById(clientPk).orElseThrow(ClientNotFoundException::new);
        VotingRight votingRight = votingRightRepository.findByClient(client).orElseThrow(VotingRightNotFoundException::new);
        if (votingRight.getCount() < voteRequestDTO.getCount()) throw new ExceedLimitedVotingRightCountException();

        Item votingItem = itemRepository.findById(itemPk).orElseThrow(ItemNotFoundException::new);
        if (!votingItem.hasVote()) throw new VoteNotFoundException();

        Vote vote = votingItem.getVote();
        if (!vote.isActivation()) throw new NotActiveVoteException();

        if (votingResultRepository.existsByVotingRightAndVote(client.getVotingRight(), vote));

        voteRequestDTO.registryInfo(votingRight, vote);

        VoteStrategy voteStrategy = vote.getVoteType().createStrategy(votingResultRepository);
        voteStrategy.vote(voteRequestDTO);
    }

    @Override
    public VoteResultInfoDTO getVoteResult(Long itemPk, Long clientPk) {
        Item item = itemRepository.findById(itemPk).orElseThrow(ItemNotFoundException::new);
        if (!item.hasVote()) throw new VoteNotFoundException();

        OpinionCountDTO shareholderResult = OpinionCountDTO.of(item.getVote());
        VoteResultInfoDTO voteResultInfoDTO = new VoteResultInfoDTO(shareholderResult);

        Client client = clientRepository.findById(clientPk).orElseThrow(ClientNotFoundException::new);
        if (client.getClientType() == ClientType.ROLE_MANAGER){
            List<SimpleVotingResultDTO> managerResult = votingResultRepository.findAllByVote(item.getVote())
                    .stream()
                    .map(SimpleVotingResultDTO::of)
                    .collect(Collectors.toList());

            voteResultInfoDTO.setManagerResult(managerResult);
        }

        return voteResultInfoDTO;
    }
}
