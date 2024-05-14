package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.Models.Meal;
import com.utkarsh.dietplanner.dao.ClientDao;
import com.utkarsh.dietplanner.dao.ScheduleDao;
import com.utkarsh.dietplanner.dataTransferObject.MealDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientService{

    @Autowired
    ClientDao clientDao;

    @Autowired
    ScheduleDao scheduleDao;

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
}
