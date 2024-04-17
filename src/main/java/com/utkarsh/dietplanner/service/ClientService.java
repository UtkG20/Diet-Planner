package com.utkarsh.dietplanner.service;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientDao clientDao;

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
}
