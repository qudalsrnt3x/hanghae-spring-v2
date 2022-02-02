package com.hanghae.hanghaespringv2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hanghae.hanghaespringv2.dto.UserDTO;
import com.hanghae.hanghaespringv2.handler.ex.InvalidException;
import com.hanghae.hanghaespringv2.model.KakaoProfile;
import com.hanghae.hanghaespringv2.model.OAuthToken;
import com.hanghae.hanghaespringv2.model.user.Role;
import com.hanghae.hanghaespringv2.model.user.User;
import com.hanghae.hanghaespringv2.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
public class UserService {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

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



    public void kakaoLogin(String code) {

        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // Retrofit2
        // OkHttp
        // RestTemplate

        // HttpHeader 오브젝트 생성
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", "74db9f0835ac0be7fa34c1dfb64beafc");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - Post, response변수의 응답 받음
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // 데이터를 오브젝트에 담는다.
        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(oAuthToken.getAccess_token());

        RestTemplate restTemplate2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post, response변수의 응답 받음
        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        objectMapper2.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : username, password, email
        //System.out.println("카카오 아이디 : " + kakaoProfile.getId());
        //System.out.println("카카오 이메일 : " + kakaoProfile.getKakaoAccount().getEmail());

        // 가입되어있는지 확인
        User userEntity = userRepository.findByUsername("kakao_" + kakaoProfile.getId());


        if (userEntity == null) {
            // 강제로 회원가입 시켜주자.
            userEntity = User.builder()
                    .username("kakao_"+kakaoProfile.getId())
                    .password(passwordEncoder.encode("1234"))
                    .email(kakaoProfile.getKakaoAccount().getEmail())
                    .role(Role.ROLE_USER)
                    .provider("kakao")
                    .providerId(String.valueOf(kakaoProfile.getId()))
                    .build();

            userRepository.save(userEntity);
        }


        // 로그인처리
        // 세션 등록
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), "1234")
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    } // kakaoLogin()

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
