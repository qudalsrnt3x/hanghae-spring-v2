package com.hanghae.hanghaespringv2.controller.api;

import com.hanghae.hanghaespringv2.dto.CMResponseDTO;
import com.hanghae.hanghaespringv2.dto.UserDTO;
import com.hanghae.hanghaespringv2.handler.ex.InvalidException;
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
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/auth/signup")
    public ResponseEntity<CMResponseDTO<?>> signup(@Valid @RequestBody UserDTO user,
                                                BindingResult bindingResult) {
        // 유효성 체크
        if (bindingResult.hasErrors())
            throw new InvalidException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());

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
