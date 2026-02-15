package com.example.SanGeets.DTO.Request;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder


public class PlayListRequest {
    private Long userId;
    private String title;
    private Long songId;

}
