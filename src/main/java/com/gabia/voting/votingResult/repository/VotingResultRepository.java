package com.gabia.voting.votingResult.repository;

import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.dto.OpinionCountDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface VotingResultRepository extends JpaRepository<VotingResult, Long> {

//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select sum(v.count) FROM voting_result v where v.vote.votePk = :votePk")
    public int sumCountByVote(Long votePk);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public List<VotingResult> findAllByVote(Vote vote);

    @Query("select new com.gabia.voting.votingResult.dto.OpinionCountDTO(v.opinion, sum(v.count)) FROM voting_result v where v.vote.votePk = :votePk group by v.opinion")
    public List<OpinionCountDTO> searchOpinionCountVotingResultByVote(Long votePk);

    public boolean existsByVotingRightAndAndVote(VotingRight votingRight, Vote vote);

}
