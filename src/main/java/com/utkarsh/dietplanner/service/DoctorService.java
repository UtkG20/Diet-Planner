package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.Doctor;
import com.utkarsh.dietplanner.dao.DoctorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorDao doctorDao;

    public List<Doctor> getAllDoctors() {
        return doctorDao.findAll();
    }

    public int saveDoctor(Doctor doctor) {
        try{
            return doctorDao.save(doctor).getDocId();
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }
}
