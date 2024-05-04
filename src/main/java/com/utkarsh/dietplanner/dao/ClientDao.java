package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client,Integer> {
    Client findByUsername(String username);
}
