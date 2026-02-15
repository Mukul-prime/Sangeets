package com.example.SanGeets.Controller;


import com.example.SanGeets.DTO.Request.LanguageRequest;
import com.example.SanGeets.DTO.Response.LanguageResponse;
import com.example.SanGeets.Exceptions.LanguageAlreadyExist;
import com.example.SanGeets.Service.LanguageServiuce;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Language")
@Slf4j
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageServiuce languageServiuce;

    @GetMapping("/")
    public String welcome_language(){
        return "Welcome to SanGeets";
    }


    @PostMapping("/")
    public ResponseEntity<?> CreateLanguageResponse(@RequestBody LanguageRequest languageRequest){
        log.info("Creating Language Response");
        try{
            LanguageResponse languageResponse = languageServiuce.CreateLanguageResponse(languageRequest);
            return new ResponseEntity<>(languageResponse , HttpStatus.CREATED);
        }
        catch (LanguageAlreadyExist e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.CONFLICT);
        }
    }

}
