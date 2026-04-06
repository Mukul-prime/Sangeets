package com.example.SanGeets.DTO.Request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAlbumSongsRequest {


    private Long songID;
    private Long albumID;
}
