package com.gabia.voting.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.type.VoteType;
import com.gabia.voting.item.valid.VoteTimeCorrectCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@VoteTimeCorrectCheck(startTime = "startTime", endTime = "endTime")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveVoteDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message = "투표 시작 시간이 입력되어있지 않습니다.")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message = "투표 종료 시간이 입력되어있지 않습니다.")
    private LocalDateTime endTime;

    @NotNull(message = "투표 종류가 입력되어있지 않습니다.")
    private VoteType voteType;

    public Vote toEntity(Item item){
        return Vote.builder()
                .item(item)
                .startTime(startTime)
                .endTime(endTime)
                .voteType(voteType)
                .build();
    }

}
