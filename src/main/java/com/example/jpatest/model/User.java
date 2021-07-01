package com.example.jpatest.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String pw;
    private String email;
    private String role;
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp reg_dt;

    @Builder
    public User(String username, String pw, String email, String role, String provider, String providerId, Timestamp reg_dt) {
        this.username=username;
        this.pw=pw;
        this.email=email;
        this.role=role;
        this.provider=provider;
        this.providerId=providerId;
        this.reg_dt=reg_dt;
    }
}
