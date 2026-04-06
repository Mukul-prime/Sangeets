package com.example.SanGeets.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ChangeAlbumImages
{
    private String oldTitle;
    private MultipartFile newImage;
}
