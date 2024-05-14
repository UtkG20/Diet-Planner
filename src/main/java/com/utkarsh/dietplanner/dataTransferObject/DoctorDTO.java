package com.utkarsh.dietplanner.dataTransferObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    @JsonIgnore
    private Integer docId;
    private String username;
    private Integer age;
}
