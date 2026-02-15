package com.example.SanGeets.DTO.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DeleteSongRequest {
    private Long artistId;
    private Long songId;
}
