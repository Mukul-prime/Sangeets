package com.example.SanGeets.DTO.Request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongRequest {
    private String title ;
    private Long duration ;
    private Long artistId;
    private Long genreid;
    private MultipartFile banner;
    private MultipartFile audio;



}
