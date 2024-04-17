package com.utkarsh.dietplanner.config;

import com.utkarsh.dietplanner.Models.CustomUser;
import com.utkarsh.dietplanner.controller.AuthController;
import org.hibernate.annotations.Bag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails userDetails = User.builder().username("utkarsh").password(passwordEncoder().encode("goyal")).roles("ADMIN").build();
//        UserDetails userDetails1 = User.builder().username("Jatin").password(passwordEncoder().encode("garg")).roles("USER").build();
//        UserDetails userDetails2 = CustomUser.builder().username("ishita").password(passwordEncoder().encode("verma")).roles("USER").build();
//        return new InMemoryUserDetailsManager(userDetails,userDetails1,userDetails2);
//    }

    private final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        log.info("Inside manager");
        return builder.getAuthenticationManager();
    }
}
