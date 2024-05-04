package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer clientId;

    private String username;

    private String password;
    private Integer age;

    @ManyToMany
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList = new ArrayList<>();

//    @ManyToMany
//    private List<Meal> mealList = new ArrayList<>();

}
