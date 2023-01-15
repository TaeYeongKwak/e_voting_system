package com.gabia.voting.global.aspect;

import com.gabia.voting.votingResult.entity.VotingResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class VoteLogAspect {

    @AfterReturning(value = "execution(* com.gabia.voting.votingResult.service.VotingResultService.useVotingRight(..))", returning = "votingResult")
    public void loggingVote(JoinPoint joinPoint, VotingResult votingResult){
        log.info("{}", getVoteLogMessage(votingResult));
    }

    private String getVoteLogMessage(VotingResult votingResult){
        StringBuffer stringBuffer = new StringBuffer("[voting_result_info]");
        stringBuffer.append(" clientId: " + votingResult.getVotingRight().getClient().getClientId());
        stringBuffer.append(", itemPk: "  + votingResult.getVote().getItem().getItemPk());
        stringBuffer.append(", opinion: " + votingResult.getOpinion());
        stringBuffer.append(", count: " + votingResult.getCount());
        stringBuffer.append(", timestamp: " + votingResult.getCreatedTime());
        return stringBuffer.toString();
    }

}
