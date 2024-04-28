package com.utkarsh.dietplanner.controller;

import com.utkarsh.dietplanner.Models.ExperimentCustomUser;
import com.utkarsh.dietplanner.Models.JwtRequest;
import com.utkarsh.dietplanner.Models.JwtResponse;
import com.utkarsh.dietplanner.security.JwtHelper;
import com.utkarsh.dietplanner.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;
    
    @PostMapping("signup")
    public String signUp(@RequestBody ExperimentCustomUser user){
        return customUserDetailsService.saveUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){

        log.info("Post call have been received at user/add with DTO " + request);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        log.info("userDetails " + userDetails);


        this.doAuthenticate(request.getUsername(),request.getPassword());

//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        log.info("userDetails " + userDetails);
        String token = helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private  void doAuthenticate(String username, String password){
        log.info("In doAuthenticate");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
        try{
            log.info("In try block" + authentication);

            manager.authenticate(authentication);
            //authenticate verifies the credentials from userDetailsService whichever you are using for this API

            log.info("In try block after authenticate-->"+ authentication);
        }catch (BadCredentialsException e){
            log.info("In catch block" + e);
            throw new BadCredentialsException("Invalid Username or Password !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Invalid Credentials..";
    }

}
