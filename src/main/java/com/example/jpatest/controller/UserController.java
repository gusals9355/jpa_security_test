package com.example.jpatest.controller;

import com.example.jpatest.model.User;
import com.example.jpatest.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @GetMapping({"/",""})
    public String index(){

        return "index";
    }
    @GetMapping("/user")
    public String user(){

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
