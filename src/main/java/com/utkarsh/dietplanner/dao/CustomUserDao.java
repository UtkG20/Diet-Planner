package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.CustomUser;
import com.utkarsh.dietplanner.Models.ExperimentCustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserDao extends JpaRepository<ExperimentCustomUser,Integer> {
    ExperimentCustomUser findByUsername(String username);
}
