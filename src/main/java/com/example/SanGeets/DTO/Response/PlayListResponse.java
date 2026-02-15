package com.example.SanGeets.DTO.Response;


import com.example.SanGeets.Model.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlayListResponse {

    private User user;
    private String title;

}
