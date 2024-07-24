package com.utkarsh.dietplanner.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AppointmentRequest {

    private String reason;

    @NonNull
    private Date appointmentDate;

    @NonNull
    private Integer doc_id;
}
