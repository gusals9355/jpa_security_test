package com.example.jpatest.repository;


import com.example.jpatest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//crud
public interface UserRepository extends JpaRepository<User, Integer> {
    //findBy규칙 -> Username문법
    //select * from user where username = ?(파라미터값) 이렇게 호출된다 JPA 쿼리 메소드
    public User findByUsername(String username);

}
