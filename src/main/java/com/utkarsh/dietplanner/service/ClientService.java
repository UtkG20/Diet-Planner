package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    ClientDao clientDao;

    @Override
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


}
