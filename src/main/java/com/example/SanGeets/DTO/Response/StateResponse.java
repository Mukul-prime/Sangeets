package com.example.SanGeets.DTO.Response;

import com.example.SanGeets.Model.Country;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class StateResponse {
    private int id;
    private String stateName ;

    private String country;
}
