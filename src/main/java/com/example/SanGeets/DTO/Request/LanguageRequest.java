package com.example.SanGeets.DTO.Request;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LanguageRequest {
    private String languageCode;


    private String languageName;
}
