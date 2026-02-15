package com.example.SanGeets.DTO.Request;


import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
//@Service
@Getter
@Builder
@Setter
public class AlbumRequest {

    private String title;
    private MultipartFile image;
    private Long artistId;
}
