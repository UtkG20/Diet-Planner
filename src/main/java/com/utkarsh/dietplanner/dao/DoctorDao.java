package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDao extends JpaRepository<Doctor,Integer> {
    Doctor findByUsername(String username);
}
