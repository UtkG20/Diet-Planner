package com.utkarsh.dietplanner.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Entity(name="users")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Getter
@Setter
public class CustomUser extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    private String username;

    private String password;

    private Integer age;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,Integer userId,Integer age) {
        super(username, password, authorities);
        this.userId = userId;
        this.age = age;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }
}
