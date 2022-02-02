package com.hanghae.hanghaespringv2.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDTO {

    @NotBlank(message = "유저네임을 입력하세요.")
    @Size(min = 3, max = 10, message = "아이디는 3자 이상 10자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, max = 10, message = "비밀번호는 4자 이상 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호를 한번 더 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
}
