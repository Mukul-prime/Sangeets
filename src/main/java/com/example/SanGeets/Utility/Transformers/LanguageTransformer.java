package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.LanguageRequest;
import com.example.SanGeets.DTO.Response.LanguageResponse;
import com.example.SanGeets.Model.Language;

public class LanguageTransformer {
    public static Language  languageRequestToLanguage(LanguageRequest languageRequest){
        return Language.builder()
                .languageCode(languageRequest.getLanguageCode())
                .languageName(languageRequest.getLanguageName())
                .build();
    }

    public static LanguageResponse laguageToLanguageResponse(Language language){
        return LanguageResponse.builder().languageId(language.getId())
                .languageCode(language.getLanguageCode())
                .languageName(language.getLanguageName())
                .build();
    }
}
