package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


//@Entity(name="users")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Getter
@Setter
public class CustomUser extends User implements UserDetails {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Integer age;

    public CustomUser() {


        super("xyz","xyz",true,true,
                true,true,new ArrayList<>());

//        this.age = 0;
    }


    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer age) {
        super(username, password, authorities);
        this.age = age;
    }
}
