package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "client_meals")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schedule_id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    
    private String mealType;

}
