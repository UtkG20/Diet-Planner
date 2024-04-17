package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.Models.Doctor;
import com.utkarsh.dietplanner.Models.Meal;
import com.utkarsh.dietplanner.dao.ClientDao;
import com.utkarsh.dietplanner.dao.DoctorDao;
import com.utkarsh.dietplanner.dao.MealDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    @Autowired
    MealDao mealDao;

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    ClientDao clientDao;

    public List<Meal> getAllMeals() {
        return mealDao.findAll();
    }

    public List<Meal> getMealsOfDoctor(Integer doctorId) {
        try{
            Doctor doctor = doctorDao.findById(doctorId).get();
//            Integer docId = doctor.getDocId();
            return mealDao.findByDoctor(doctor);
        }catch (Exception e){
            System.out.println(e);
            List<Meal> list = new ArrayList<>();
            return list;
        }

    }

    public int saveMeal(Meal meal) {
        try{
            System.out.println(meal.getDoctor());
            Doctor doctor = doctorDao.findById(meal.getDoctor().getDocId()).get();
            return mealDao.save(meal).getMealId();
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

//    public List<Meal> getAllClientMeals(Integer clientId) {
//        try{
//            Client client = clientDao.findById(clientId).get();
//            return
//        }

//    }
}
