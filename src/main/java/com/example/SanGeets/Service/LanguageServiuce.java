package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.LanguageDAO;
import com.example.SanGeets.DTO.Request.LanguageRequest;
import com.example.SanGeets.DTO.Response.LanguageResponse;
import com.example.SanGeets.Exceptions.LanguageAlreadyExist;
import com.example.SanGeets.Model.Language;
import com.example.SanGeets.Utility.Transformers.LanguageTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LanguageServiuce {

    private final LanguageDAO languageDAO;

    public LanguageResponse CreateLanguageResponse(LanguageRequest languageRequest) {
        log.info("LanguageServiuce CreateLanguageResponse");
        Language language = languageDAO.findBylanguageName(languageRequest.getLanguageName());
        if (language != null) {
            throw new LanguageAlreadyExist("Language Already Exist");
        }

        Language language1 = LanguageTransformer.languageRequestToLanguage(languageRequest);
        Language language2 = languageDAO.save(language1);
        return LanguageTransformer.laguageToLanguageResponse(language2);
    }

    public List<LanguageResponse> getAllLanguage(){
        List<Language> languages = languageDAO.findAll();
        List<LanguageResponse> languageResponses = new ArrayList<>();
        for (Language language : languages) {
            LanguageResponse languageResponse = new LanguageResponse(
                    language.getId(),
                    language.getLanguageCode(),
                    language.getLanguageName()
            );
            languageResponses.add(languageResponse);
        }

        return languageResponses;

    }
}
