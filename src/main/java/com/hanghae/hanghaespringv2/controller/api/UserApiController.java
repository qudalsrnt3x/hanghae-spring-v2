package com.hanghae.hanghaespringv2.controller.api;

import com.hanghae.hanghaespringv2.dto.ResponseDTO;
import com.hanghae.hanghaespringv2.dto.UserDTO;
import com.hanghae.hanghaespringv2.model.user.UserRepository;
import com.hanghae.hanghaespringv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody UserDTO user, @Valid BindingResult bindingResult) { //TODO 유효성검사 및 응답결과 추가하기
        System.out.println("UserApiController.signup");

        // 회원가입 구현
        userService.signup(user);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED.value(), "회원가입 완료", null));

    }

    @PostMapping("/auth/idCheck")
    public int idCheck(@RequestBody UserDTO userDTO) {
        boolean isUsername = userRepository.findByUsername(userDTO.getUsername()).isPresent();
        return isUsername ? 1 : 0;
    }

}
