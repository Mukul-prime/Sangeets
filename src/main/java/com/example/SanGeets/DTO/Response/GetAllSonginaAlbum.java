package com.example.SanGeets.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class GetAllSonginaAlbum {
    private String title;
    private List<SongResponse> songResponseList;
}
