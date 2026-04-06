package com.example.SanGeets.DTO.Request;

import com.example.SanGeets.Utility.Enums.Types;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongRequest {
    private String title ;
    private String duration ;
//    private Long artistId;
    private Long genreid;
    private MultipartFile banner;
    private Types type;
    private MultipartFile thumbnail;
    private MultipartFile audio;
}
