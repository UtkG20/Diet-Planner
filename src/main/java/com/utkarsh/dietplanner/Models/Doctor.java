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
@Table(name = "Doctor")
public class
Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer docId;
    private String name;
    private String password;
    private Integer age;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<Meal> mealList = new ArrayList<>();

    @ManyToMany(mappedBy = "doctors",cascade = CascadeType.ALL)
    private List<Client> clientList = new ArrayList<>();
}
