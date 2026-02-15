package com.example.SanGeets.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LanguageResponse {
    private String languageCode;
    private String languageName;

}
