package com.example.SanGeets.DTO.Request;

import com.example.SanGeets.Model.Country;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StateRequest {
    private String stateCode;

//    @Column(unique = true , nullable = false)
    private String stateName ;

    private long country;
}
