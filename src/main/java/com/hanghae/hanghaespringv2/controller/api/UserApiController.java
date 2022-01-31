package com.hanghae.hanghaespringv2.controller.api;

import com.hanghae.hanghaespringv2.dto.ResponseDTO;
import com.hanghae.hanghaespringv2.dto.UserDTO;
import com.hanghae.hanghaespringv2.model.user.User;
import com.hanghae.hanghaespringv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody UserDTO user) { //TODO 유효성검사 및 응답결과 추가하기
        System.out.println("UserApiController.signup");

        // 회원가입 구현
        User savedUser = userService.signup(user);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED.value(), "회원가입 완료"));

    }
}
