package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.Models.Doctor;
import com.utkarsh.dietplanner.Models.Meal;
import com.utkarsh.dietplanner.Models.Schedule;
import com.utkarsh.dietplanner.controller.ClientController;
import com.utkarsh.dietplanner.dao.ClientDao;
import com.utkarsh.dietplanner.dao.DoctorDao;
import com.utkarsh.dietplanner.dao.MealDao;
import com.utkarsh.dietplanner.dao.ScheduleDao;
import com.utkarsh.dietplanner.dataTransferObject.ClientDTO;
import com.utkarsh.dietplanner.dataTransferObject.MealDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    ClientDao clientDao;

    @Autowired
    MealDao mealDao;

    @Autowired
    ScheduleDao scheduleDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor;
        doctor = doctorDao.findByUsername(username.toString());
        return doctor;
    }

    public List<Doctor> getAllDoctors() {
        return doctorDao.findAll();
    }

    public String insertDoctor(Doctor doctor){
        try{
            Doctor doc = new Doctor(doctor.getUsername(), doctor.getPassword(),doctor.getAge());
            doctorDao.save(doc);
            return doc.getUsername() + "saved Successfully";
        }catch (IllegalArgumentException e){
            return "Please pass all the necessary details -> username, password and age";
        }

    }

    public void deleteDoctor(Integer docId) {
        try {
            Doctor doctor = doctorDao.findById(docId).get();
            doctorDao.deleteById(doctor.getDocId());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String updateUser(Integer docId, Doctor user) {
        try{
            if(doctorDao.findById(docId).isPresent()){
                Doctor doctor = doctorDao.findById(docId).get();
                doctor.setAge(user.getAge());
                doctor.setUsername(user.getUsername());
                doctor.setPassword((user.getPassword()));
                doctorDao.save(doctor);
                return doctor.getUsername() + " details updated successfully..";
            }else{
                return "User does not exist";
            }

        }catch (Exception e){
            return "Exeption";
        }

    }

    public Doctor getUserByUsername(String name) {
        Doctor doctor = doctorDao.findByUsername(name);
        return doctor;
    }

    public String addNewClient(Integer docId,Integer clientId){
        try{
            if(doctorDao.findById(docId).isPresent()){
                Doctor doctor = doctorDao.findById(docId).get();
                if(clientDao.findById(clientId).isPresent()){
                    Client client = clientDao.findById(clientId).get();
                    doctor.getClientList().add(client);
                    client.getDoctors().add(doctor);
                    doctorDao.save(doctor);
                    clientDao.save(client);
                    log.info("clientList: "+doctor.getClientList().toString());
                    return "Client added successfully";
                }else{
                    return "Client is not a valid user";
                }
            }else{
                return "Doctor is not a valid user";
            }
        }catch (DataIntegrityViolationException e){
            return "Client id already in the list";
        }catch (Exception e){
            return "Exception Occured "+e;
        }

    }

    public List<ClientDTO> getClientlist(Integer docId) {
        Doctor doctor = doctorDao.findById(docId).get();
        List<Client> clients =  doctor.getClientList();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for(Client client : clients){
            clientDTOs.add(convertToDTO(client));
        }
        return clientDTOs;
    }

    public String createMeal(Meal meal, Integer docId) {
        Doctor doctor = doctorDao.findById(docId).get();
        meal.setDoctor(doctor);
        mealDao.save(meal);
        String mealName = meal.getMealName();
        return mealName+" added successfully";
    }

    public List<MealDTO> getMealsList(Integer docId) {
        Doctor doctor = doctorDao.findById(docId).get();
        List<Meal> mealList = doctor.getMealList();
        List<MealDTO> meals = new ArrayList<>();
        for(Meal meal: mealList){
            meals.add(new MealDTO(meal.getMealId(),meal.getMealName(),meal.getMealDesc()));
        }
        return meals;
    }

    private ClientDTO convertToDTO(Client client){
        return new ClientDTO(client.getClientId(),client.getUsername(),client.getAge());
    }

    public boolean assignMeal(Integer clientId, Integer mealId, Schedule schedule) {
        try {
            schedule.setMeal(mealDao.findById(mealId).get());
            schedule.setClient(clientDao.findById(clientId).get());
            scheduleDao.save(schedule);
            return true;
        }catch (Exception e){
            log.info("Exception "+e);
            return false;
        }
    }
}
