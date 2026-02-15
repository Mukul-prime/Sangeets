package com.example.SanGeets.DTO.Request;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlayListUpdate {
 private String email;
 private Long playlistId;
 private Long song_Id;
}
