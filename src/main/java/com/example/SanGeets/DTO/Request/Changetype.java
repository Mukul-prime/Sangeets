package com.example.SanGeets.DTO.Request;

import com.example.SanGeets.Utility.Enums.Types;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Changetype {
    private Long id;
    @JsonAlias("types")
    private Types type;
}
