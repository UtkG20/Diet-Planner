package com.utkarsh.dietplanner.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.utkarsh.dietplanner.Models.*;
import com.utkarsh.dietplanner.dao.ClientDao;
import com.utkarsh.dietplanner.dao.DoctorDao;
import com.utkarsh.dietplanner.dao.ScheduleDao;
import com.utkarsh.dietplanner.dataTransferObject.MealDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientService{

    @Autowired
    ClientDao clientDao;

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorDao doctorDao;

//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client;
        client = clientDao.findByUsername(username.toString());
        return client;
    }

    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    public int insertClient(Client client){
        Client result = clientDao.save(client);
        return result.getClientId();
    }

    public void deleteClient(Integer clientId) {
        try {
            Client client = clientDao.findById(clientId).get();
            clientDao.deleteById(client.getClientId());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String updateUser(Integer clientId, Client user) {
        Client client = clientDao.findById(clientId).get();

        client.setAge(user.getAge());
        client.setUsername(user.getUsername());
        client.setPassword((user.getPassword()));
        return client.getUsername() + " details updated successfully..";
    }

    public Client getUserByUsername(String name) {
        Client client = clientDao.findByUsername(name);
        return client;
    }


    public List<MealDTO> getScheduleByDate(LocalDate date, Client client) {
        List<Meal> mealList = new ArrayList<>();
        mealList = scheduleDao.findMealsByClientAndDate(client,date);

        List<MealDTO> meals = new ArrayList<>();
        for (Meal meal: mealList){
            meals.add(new MealDTO(meal.getMealId(),meal.getMealName(),meal.getMealDesc()));
        }
        return meals;
    }

    public ResponseEntity<MealDTO> getScheduleByDate(LocalDate date, Client client, MealType mealType) {
        Meal meal = scheduleDao.findMealsByClientAndDate(client,date,mealType);

//        return new MealDTO(meal.getMealId(),meal.getMealName(),meal.getMealDesc());
        try{
            return new ResponseEntity<>(new MealDTO(meal.getMealId(),meal.getMealName(),meal.getMealDesc()), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new MealDTO(),HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<String> createNewAppointment(Client client, AppointmentRequest request){

        Doctor doctor;
//        LocalDate localDate;
//        LocalTime localTime;
        try{
            doctor = doctorDao.findById(request.getDoc_id()).get();
        }catch (Exception e){
            return new ResponseEntity<>("Not a valid/active doctor",HttpStatus.NOT_FOUND);
        }

//        try {
//            localDate = request.getAppointmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//            localTime = request.getAppointmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
//        }catch (DateTimeException e){
//            return new ResponseEntity<>("Error in extracting date and time",HttpStatus.BAD_REQUEST);
//        }catch (Exception e){
//            return new ResponseEntity<>("Error in extracting date and time",HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        try {
            Appointment appointment = new Appointment(doctor,client,request.getReason(),request.getAppointmentDate());
            client.getAppointments().add(appointment);
            doctor.getAppointments().add(appointment);
            clientDao.save(client);
            doctorDao.save(doctor);
            return new ResponseEntity<>("Request processed Successfully",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Unexpected Error occured while processing request",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
