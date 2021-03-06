package com.example.jpatest.oauth;

import com.example.jpatest.auth.PrincipalDetails;
import com.example.jpatest.model.User;
import com.example.jpatest.provider.FacebookUserInfo;
import com.example.jpatest.provider.GoogleUserInfo;
import com.example.jpatest.provider.NaverUserInfo;
import com.example.jpatest.provider.Oauth2UserInfo;
import com.example.jpatest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); //registrationId로 어떤 oauth로 로그인했는지 확인가능
        System.out.println("액세스 토큰: "+userRequest.getAccessToken().getTokenValue());
        OAuth2User oauth2User = super.loadUser(userRequest);
        //구글 로그인버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code를 리턴(oauth-client라이브러리) -> access token 요청
        // userrequest 정보 -> loaduser함수 호출 -> 구글로 부터 회원프로필 받아줌
        System.out.println("getAttribute: " + oauth2User.getAttributes());

        Oauth2UserInfo oauth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oauth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oauth2UserInfo = new NaverUserInfo((Map<String, Object>) oauth2User.getAttributes().get("response"));
        }else{
            System.out.println("XXXX");
        }

        String provider = oauth2UserInfo.getProvider(); // google
        String providerId = oauth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;
        String role = "ROLE_USER";
        String pw = bCryptPasswordEncoder.encode("겟인데어");
        String email = oauth2UserInfo.getEmail();

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .pw(pw)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        //회원가입을 강제로 진행시킬것
        return new PrincipalDetails(userEntity,oauth2User.getAttributes());
    }
}
