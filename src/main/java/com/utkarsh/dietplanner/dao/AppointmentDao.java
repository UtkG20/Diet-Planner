package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDao extends JpaRepository<Appointment,Integer> {

}
