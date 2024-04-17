package com.utkarsh.dietplanner.controller;

import com.utkarsh.dietplanner.Models.Doctor;
import com.utkarsh.dietplanner.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("allDoctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @PostMapping("saveDoctor")
    public int saveDoctor(@RequestBody Doctor doctor){
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping("current-doctor")
    public String getLoggedInUser(Principal principal){
        return principal.getName();

    }
}
