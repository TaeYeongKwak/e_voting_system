package com.gabia.voting.votingResult.controller;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.service.VotingResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v0")
public class VotingResultController {

    private final VotingResultService votingResultService;

    @PostMapping(value = "/vote/{item-pk}/client/{client-pk}")
    public APIResponseDTO voting(@PathVariable("item-pk") Long itemPk,
                                 @PathVariable("client-pk") Long clientPk,
                                 @RequestBody VoteRequestDTO voteRequestDTO){
        votingResultService.useVotingRight(itemPk, clientPk, voteRequestDTO);
        return APIResponseDTO.success();
    }

    @GetMapping(value = "/vote/{item-pk}")
    public APIResponseDTO showVoteResult(@PathVariable("item-pk") Long itemPk){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return APIResponseDTO.success(votingResultService.getVoteResult(itemPk, Long.parseLong(auth.getName())));
    }

}
