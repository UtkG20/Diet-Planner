package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.CustomUser;
import com.utkarsh.dietplanner.dao.CustomUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomUserDao customUserDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = customUserDao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found.."));

        return new User(customUser.getUsername(),customUser.getPassword(),new ArrayList<>());
    }
}
