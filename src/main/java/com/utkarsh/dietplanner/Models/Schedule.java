package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client_meals",uniqueConstraints = {@UniqueConstraint(columnNames = {"meal_id","client_id","date","mealType"})})
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    public Schedule(Meal meal,Client client, LocalDate date,MealType mealType){
        this.meal = meal;
        this.client = client;
        this.dayOfWeek = this.getDay(date);
        this.date = date;
        this.mealType = mealType;
    }

    public void setDate(LocalDate date){
        this.date = date;
        this.dayOfWeek = this.getDay(date);
    }

    // Get the day of the week as a number (1 = Sunday, 2 = Monday, ..., 7 = Saturday)

    private DayOfWeek getDay(LocalDate localDate){
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DayOfWeek.values()[calendar.get(Calendar.DAY_OF_WEEK)];
    }
}
