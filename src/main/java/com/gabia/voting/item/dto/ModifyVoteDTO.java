package com.gabia.voting.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.voting.item.valid.VoteTimeCorrectCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@VoteTimeCorrectCheck(startTime = "startTime", endTime = "endTime")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyVoteDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message = "투표 시작 시간이 입력되어있지 않습니다.")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message = "투표 종료 시간이 입력되어있지 않습니다.")
    private LocalDateTime endTime;

}
