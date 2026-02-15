package com.example.SanGeets.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

    private String answer= "Success";
    private String name;
    private String email;
}
