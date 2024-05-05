package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.CustomUser;
import com.utkarsh.dietplanner.Models.ExperimentCustomUser;
import com.utkarsh.dietplanner.controller.AuthController;
import com.utkarsh.dietplanner.dao.CustomUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    CustomUserDao customUserDao;

    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        ExperimentCustomUser customUser = customUserDao.findByUsername(username.toString());

            return customUser;


    }

    public ExperimentCustomUser getUserByUsername(String username) throws UsernameNotFoundException {


        ExperimentCustomUser customUser = customUserDao.findByUsername(username.toString());

        return customUser;


    }

    public String saveUser(ExperimentCustomUser user){
        String username = customUserDao.save(user).getUsername();
        return username + " signed up successfully";
    }

    public List<ExperimentCustomUser> getAllUsers(){
        return customUserDao.findAll();
    }

    public String updateUser(Integer userId, ExperimentCustomUser user){
        try{
            Optional<ExperimentCustomUser> customUser = customUserDao.findById(userId);
            if(customUser.isPresent()){
                ExperimentCustomUser newUser = customUser.get();
                newUser.setAge(user.getAge());
                newUser.setUsername(user.getUsername());
                newUser.setPassword(user.getPassword());
                customUserDao.save(newUser);
                return newUser.getUsername()+" details updated successfully..";
            }else {
                return "User not found..";
            }
        }catch (Exception e){
            return "Some issue in updating the user information";
        }

    }
}
