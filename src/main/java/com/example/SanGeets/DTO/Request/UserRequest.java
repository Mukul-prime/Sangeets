package com.example.SanGeets.DTO.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserRequest {
    private String name ;
    private String email;
    private String password;
    private Long countryId;
    private Long languageId;
    private int stateId;

}
