package com.utkarsh.dietplanner.controller;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("allClients")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }


    @PostMapping("saveClient")
    public int saveClient(@RequestBody Client client){
        try{
            int clientId = clientService.insertClient(client);
            System.out.println("ClientID: "+clientId +" Saved successfully");
            return clientId;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    @DeleteMapping("deleteClient")
    public void deleteClient(@RequestBody Integer clientId){
        try{
            clientService.deleteClient(clientId);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}