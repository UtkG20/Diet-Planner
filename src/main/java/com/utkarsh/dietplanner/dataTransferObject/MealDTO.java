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
public class MealDTO {
    @JsonIgnore
    private Integer mealId;
    private String mealName;
    private String mealDesc;
}
