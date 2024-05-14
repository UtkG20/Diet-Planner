package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mealId;
    private String mealName;
    private String mealDesc;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "meal",cascade = CascadeType.ALL)
    private List<Schedule> scheduleList = new ArrayList<>();

    @PrePersist
    private void onCreate(){
        creationDate = new Date();
    }

}
