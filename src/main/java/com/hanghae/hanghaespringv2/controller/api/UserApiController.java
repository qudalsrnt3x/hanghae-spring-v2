package com.hanghae.hanghaespringv2.controller.api;

import com.hanghae.hanghaespringv2.dto.CMResponseDTO;
import com.hanghae.hanghaespringv2.dto.ResponseDTO;
import com.hanghae.hanghaespringv2.dto.UserDTO;
import com.hanghae.hanghaespringv2.handler.ex.CustomValidationException;
import com.hanghae.hanghaespringv2.model.user.UserRepository;
import com.hanghae.hanghaespringv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/auth/signup")
    public ResponseEntity<CMResponseDTO<?>> signup(@Valid @RequestBody UserDTO user,
                                                BindingResult bindingResult) { //TODO 유효성검사 및 응답결과 추가하기

        System.out.println("UserApiController.signup");

        // 유효성 체크
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("정보를 다시 입력해주세요.", errorMap);
        }

        // 회원가입 구현
        userService.signup(user);

        return ResponseEntity.ok(new CMResponseDTO<>(true, HttpStatus.CREATED.value(), "회원가입이 완료되었습니다.", null));

    }

    @PostMapping("/auth/idCheck")
    public int idCheck(@RequestBody UserDTO userDTO) {
        boolean isUsername = userRepository.findByUsername(userDTO.getUsername()).isPresent();
        return isUsername ? 1 : 0;
    }

}
