package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.Doctor;
import com.utkarsh.dietplanner.Models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealDao extends JpaRepository<Meal,Integer> {

    List<Meal> findByDoctor(Doctor doctor);
}
