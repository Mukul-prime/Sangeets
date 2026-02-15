package com.example.SanGeets.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongResponse {
    private String title;
    private Long duration;
    private String ArtistName;
    private String GenreName;
    private String audioUrl;
    private String bannerUrl;

}
