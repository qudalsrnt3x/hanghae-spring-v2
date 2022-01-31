package com.hanghae.hanghaespringv2.service;

import com.hanghae.hanghaespringv2.dto.UserDTO;
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
    public User signup(UserDTO user) {

        // 비밀번호 암호화
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        User createUser = User.builder()
                .username(user.getUsername())
                .password(encPassword)
                .email(user.getEmail())
                .role(Role.ROLE_USER)
                .build();

        // 회원 저장
        return userRepository.save(createUser);
    }
}
