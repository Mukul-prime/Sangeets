package com.example.SanGeets.DTO.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangedurationRequest {
    private long id;

    private String duration;
}
