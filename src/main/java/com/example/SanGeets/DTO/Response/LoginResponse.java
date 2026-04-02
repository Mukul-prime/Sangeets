package com.example.SanGeets.DTO.Response;


//import jakarta.validation.constraints.AssertTrue;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class LoginResponse {
    private String token;
    private String email ;
    private String role;
}
