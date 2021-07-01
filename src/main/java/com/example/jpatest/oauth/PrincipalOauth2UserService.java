package com.example.jpatest.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("액세스 토큰: "+userRequest.getAccessToken().getTokenValue());
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); //registrationId로 어떤 oauth로 로그인했는지 확인가능
        //구글 로그인버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code를 리턴(oauth-client라이브러리) -> access token 요청
        // userrequest 정보 -> loaduser함수 호출 -> 구글로 부터 회원프로필 받아줌
        System.out.println("getAttribute: " + super.loadUser(userRequest).getAttributes());

        //회원가입을 강제로 진행시킬것
        return super.loadUser(userRequest);
    }
}