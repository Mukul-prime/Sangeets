package com.example.SanGeets.DTO.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Loginrequest {

    private String email;
    private String password;
}
