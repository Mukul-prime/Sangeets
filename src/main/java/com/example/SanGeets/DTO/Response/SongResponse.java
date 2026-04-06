package com.example.SanGeets.DTO.Response;

import com.example.SanGeets.Utility.Enums.Types;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongResponse {
    private String title;
    private String duration;
    private Types type;
    private String ArtistName;
    private String GenreName;
    private String audioUrl;
    private String bannerUrl;

}
