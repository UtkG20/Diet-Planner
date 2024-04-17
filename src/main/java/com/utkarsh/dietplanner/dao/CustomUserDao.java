package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserDao extends JpaRepository<CustomUser,Integer> {
    Optional<CustomUser> findByUsername(String username);
}
