package com.hanghae.hanghaespringv2.config.auth;

import com.hanghae.hanghaespringv2.model.user.User;
import com.hanghae.hanghaespringv2.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Configuration
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User principal = userRepository.findByUsername(username);

        if (principal != null)
            return new PrincipalDetails(principal);

        throw new UsernameNotFoundException("해당 아이디를 찾을 수 없습니다.");
    }
}
