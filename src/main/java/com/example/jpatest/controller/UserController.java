package com.example.jpatest.controller;

import com.example.jpatest.auth.PrincipalDetails;
import com.example.jpatest.model.User;
import com.example.jpatest.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){
//        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//
//        System.out.println("authentication : " + principal.getUser());
        System.out.println(principalDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User){
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        System.out.println("authentication : " + oAuth2User.getAttributes());
        System.out.println("oAuth2User : " + oAuth2User.getAttributes());

        return "oauth 세션 정보 확인하기";
    }

    @GetMapping({"/",""})
    public String index(){

        return "index";
    }
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println(principalDetails.getUser());

        return "user";
    }
    @GetMapping("/admin")
    public String admin(){

        return "admin";
    }
    @GetMapping("/loginForm")
    public String loginF(){

        return "login";
    }
    @PostMapping("/login")
    public void loginP(){

    }
    @GetMapping("/join")
    public String join(){

        return "join";
    }
    @PostMapping("/join")
    public String joinP(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPw();
        String hashPw = bCryptPasswordEncoder.encode(rawPassword);
        user.setPw(hashPw);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }
}
