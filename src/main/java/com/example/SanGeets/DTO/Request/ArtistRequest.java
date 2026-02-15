package com.example.SanGeets.DTO.Request;

import com.example.SanGeets.Model.Country;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArtistRequest {
    private String artistName;
    private String email;
    private String password;
    private String bio;
    private MultipartFile coverImage;
    private Country country;
}
