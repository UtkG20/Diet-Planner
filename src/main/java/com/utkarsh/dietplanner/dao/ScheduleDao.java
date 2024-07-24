package com.utkarsh.dietplanner.dao;

import com.utkarsh.dietplanner.Models.Client;
import com.utkarsh.dietplanner.Models.Meal;
import com.utkarsh.dietplanner.Models.MealType;
import com.utkarsh.dietplanner.Models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ScheduleDao extends JpaRepository<Schedule,Integer> {

    @Query("SELECT s.meal FROM Schedule s where s.client = :client AND s.date = :date")
    List<Meal> findMealsByClientAndDate(@Param("client") Client client, @Param("date") LocalDate date);

    @Query("SELECT s.meal FROM Schedule s where s.client = :client AND s.date = :date AND s.mealType = :mealType")
    Meal findMealsByClientAndDate(@Param("client") Client client, @Param("date") LocalDate date, @Param("mealType")MealType mealType);
}
