package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointment_id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "doc_id",nullable = false)
    private Doctor doctor;

    @NonNull
//    @Temporal(TemporalType.DATE)
    private LocalDate appointmentDate;

    @NonNull
//    @Temporal(TemporalType.TIME)
    private LocalTime appointmentTime;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String remarks;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public Appointment(Doctor doctor,Client client,String reason,Date appointmentDate){
        this.client = client;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.appointmentTime = appointmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        this.reason = reason;
        this.status = AppointmentStatus.REQUESTED;
    }

}
