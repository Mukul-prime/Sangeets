package com.example.SanGeets.DTO.Request;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class CountryRequest {
    private String countryName;
}
