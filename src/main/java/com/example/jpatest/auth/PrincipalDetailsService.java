package com.example.jpatest.auth;


import com.example.jpatest.model.User;
import com.example.jpatest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //시큐리티 세션(내부 authentication(내부 (Userdetails))이렇게 들어감 결론.  = authentication(내부 return시 userDetails 들어감) => userDetails
    @Override
    public UserDetails loadUserByUsername(String username /*로그인 폼의 name값을 username이랑 동일하게 해야함*/) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        System.out.println(userEntity);
        if(userEntity != null ){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
