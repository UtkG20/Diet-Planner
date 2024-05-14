package com.utkarsh.dietplanner.controller;

import com.utkarsh.dietplanner.Models.*;
import com.utkarsh.dietplanner.dataTransferObject.ClientDTO;
import com.utkarsh.dietplanner.dataTransferObject.MealDTO;
import com.utkarsh.dietplanner.security.JwtHelper;
import com.utkarsh.dietplanner.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    DoctorService doctorService;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtHelper helper;

    @GetMapping("allDoctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @PostMapping("signup")
    public String saveClient(@RequestBody Doctor doctor){
        try{
            return doctorService.insertDoctor(doctor);
        }catch (DataIntegrityViolationException e){
            return "User already exist";
        }
        catch (Exception e){
            return "Exception"+e;
        }
    }

    @GetMapping("currentDoctor")
    public String getLoggedInUser(Principal principal){
        return principal.getName();
    }

    @GetMapping("currentUserAge")
    public Integer getAge(Principal principal){
        return doctorService.getUserByUsername(principal.getName()).getAge();
    }

    @PutMapping("updateUser/{docId}")
    public String updateUser(@PathVariable Integer docId,@RequestBody Doctor user){
        log.info("PathVariable "+ docId);
        return doctorService.updateUser(docId,user);
    }

    @DeleteMapping("deleteDoctor")
    public void deleteDoctor(@RequestBody Integer docId){
        try{
            doctorService.deleteDoctor(docId);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("addClient/{clientId}")
    public String addClient(@PathVariable Integer clientId){
        Integer docId = doctorService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getDocId();
        return doctorService.addNewClient(docId,clientId);
    }

    @GetMapping("clientList")
    public List<ClientDTO> getClients(){
        Integer docId = doctorService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getDocId();
        return doctorService.getClientlist(docId);
    }

    @PostMapping("createMeal")
    public String createMeal(@RequestBody Meal meal){
        Integer docId = doctorService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getDocId();
        return doctorService.createMeal(meal,docId);
    }

    @GetMapping("mealList")
    public List<MealDTO> getMealsList(){
        Integer docId = doctorService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getDocId();
        return doctorService.getMealsList(docId);
    }

    @PostMapping("assignMeal/client/{clientId}/meal/{mealId}")
    public boolean assignMeal(@PathVariable Integer clientId, @PathVariable Integer mealId, @RequestBody Schedule schedule){
        return doctorService.assignMeal(clientId,mealId,schedule);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){

        log.info("Post call have been received at user/add with DTO " + request);
        try{
            UserDetails userDetails = doctorService.loadUserByUsername(request.getUsername());
            log.info("userDetails " + userDetails);

            this.doAuthenticate(request.getUsername(),request.getPassword());

            String token = helper.generateToken(userDetails);

            JwtResponse response = new JwtResponse(token,userDetails.getUsername());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new JwtResponse("Not a valid user",request.getUsername()),HttpStatus.UNAUTHORIZED);
        }
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
}
