package com.utkarsh.dietplanner.controller;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.Models.JwtRequest;
import com.utkarsh.dietplanner.Models.JwtResponse;
import com.utkarsh.dietplanner.dataTransferObject.MealDTO;
import com.utkarsh.dietplanner.security.JwtHelper;
import com.utkarsh.dietplanner.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtHelper helper;

    @GetMapping("allClients")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }


    @PostMapping("signup")
    public int saveClient(@RequestBody Client client){
        try{
            int clientId = clientService.insertClient(client);
            System.out.println("ClientID: "+clientId +" Saved successfully");
            return clientId;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    @GetMapping("currentUser")
    public String getLoggedInUser(Principal principal){
        return principal.getName();
    }

    @GetMapping("currentUserAge")
    public Integer getAge(Principal principal){
        return clientService.getUserByUsername(principal.getName()).getAge();
    }

    @PutMapping("updateUser/{clientId}")
    public String updateUser(@PathVariable Integer clientId,@RequestBody Client user){
        return clientService.updateUser(clientId,user);
    }

    @DeleteMapping("deleteClient")
    public void deleteClient(@RequestBody Integer clientId){
        try{
            clientService.deleteClient(clientId);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @GetMapping("getSchedule/date/{date}")
    public List<MealDTO> getScheduleByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate date){
//        Integer clientId = clientService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getClientId();
        Client client = clientService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        return clientService.getScheduleByDate(date,clientId);
        return clientService.getScheduleByDate(date,client);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){

        log.info("Post call have been received at user/add with DTO " + request);

        UserDetails userDetails = clientService.loadUserByUsername(request.getUsername());
        log.info("userDetails " + userDetails);

        this.doAuthenticate(request.getUsername(),request.getPassword());

        String token = helper.generateToken(userDetails);

        JwtResponse response = new JwtResponse(token,userDetails.getUsername());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String username, String password){

        log.info("In doAuthenticate");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        try{
            log.info("In try block" + authenticationToken);
            manager.authenticate(authenticationToken);
            log.info("In try block after authenticate-->"+ authenticationToken);

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