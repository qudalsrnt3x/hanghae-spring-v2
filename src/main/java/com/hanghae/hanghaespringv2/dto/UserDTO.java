package com.hanghae.hanghaespringv2.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    @NotNull(message = "유저네임 키값이 없습니다.")
    @NotBlank(message = "유저네임을 입력하세요.")
    @Size(min = 3, max = 10, message = "아이디는 3자 이상 10자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, max = 10, message = "비밀번호는 4자 이상 입력해주세요.")
    private String password;

    @NotNull(message = "이메일을 입력하세요.")
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
}
