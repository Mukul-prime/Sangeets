package com.example.SanGeets.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArtistReponse {
    private String name ;
    private String email;
//    private String password;

    private String BIO;

}
