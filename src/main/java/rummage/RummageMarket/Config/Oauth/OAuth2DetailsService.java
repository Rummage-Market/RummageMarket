package rummage.RummageMarket.Config.Oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;

// 소셜로그인(페이스북) 진행
@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // username으로 확인, OAuth2User타입을 세션 생성
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> userInfo = oAuth2User.getAttributes();

        // 페이스북 소셜로그인시 유저네임
        String username = "facebook_" + (String) userInfo.get("id");
        // 패스워드
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        // 이메일
        String email = (String) userInfo.get("email");
        // 최초로그인시 닉네임은 이름으로 함.
        String nickname = (String) userInfo.get("name");

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) { // 페이스북 최초 로그인

            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());

        } else {
            
            return new PrincipalDetails(userEntity);
            
        }

    }

}
