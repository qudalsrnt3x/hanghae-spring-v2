package com.hanghae.hanghaespringv2.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReplyDTO {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
}
