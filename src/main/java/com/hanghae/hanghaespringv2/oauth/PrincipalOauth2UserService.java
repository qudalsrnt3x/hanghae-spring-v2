package com.hanghae.hanghaespringv2.oauth;

import com.hanghae.hanghaespringv2.config.auth.PrincipalDetails;
import com.hanghae.hanghaespringv2.model.user.Role;
import com.hanghae.hanghaespringv2.model.user.User;
import com.hanghae.hanghaespringv2.model.user.UserRepository;
import com.hanghae.hanghaespringv2.oauth.provider.GoogleUserInfo;
import com.hanghae.hanghaespringv2.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 구글로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-Client라이브러리) -> Access Token요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 강제로 회원가입 진행
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + oAuth2UserInfo.getEmail().split("@")[0];
        String password = passwordEncoder.encode("1234"); // 사용 X
        String email = oAuth2UserInfo.getEmail();

        // 이미 회원가입이 되어있는지 확인
        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {

            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(Role.ROLE_USER)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
