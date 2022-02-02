package com.hanghae.hanghaespringv2.service;

import com.hanghae.hanghaespringv2.dto.UserDTO;
import com.hanghae.hanghaespringv2.handler.ex.InvalidException;
import com.hanghae.hanghaespringv2.model.user.Role;
import com.hanghae.hanghaespringv2.model.user.User;
import com.hanghae.hanghaespringv2.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserDTO user) {

        String username = user.getUsername();
        String rawPassword = user.getPassword();
        String pwCheck = user.getPasswordCheck();

        // 유효성 체크
        if (!isPasswordMatched(username, rawPassword))
            throw new InvalidException("비밀번호에 아이디가 들어갈 수 없습니다.");

        if (!isExistUserName(username))
            throw new InvalidException("이미 존재하는 아이디 입니다.");

        if (!isDuplicatePassword(rawPassword, pwCheck)) {
            throw new InvalidException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        String encPassword = passwordEncoder.encode(rawPassword);

        User createUser = User.builder()
                .username(user.getUsername())
                .password(encPassword)
                .email(user.getEmail())
                .role(Role.ROLE_USER)
                .build();

        // 회원 저장
        userRepository.save(createUser);
    }

    private boolean isDuplicatePassword(String rawPassword, String pwCheck) {
        return rawPassword.equals(pwCheck);
    }

    private boolean isPasswordMatched(String username, String rawPassword) {
        return !rawPassword.contains(username);
    }

    public boolean isExistUserName(String username) {
        return userRepository.findByUsername(username) == null;
    }
}
