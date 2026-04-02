package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.CountryRequest;
import com.example.SanGeets.Exceptions.CountryAlreadyExist;
import com.example.SanGeets.Service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/Country")
@Slf4j
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;


    @GetMapping("/")
    public String welcome(){
        return "Welcome to Country Controller";
    }

    @PostMapping("/Country")
    public ResponseEntity<?> getCountryByCode(@RequestBody CountryRequest countryRequest ){
        log.info(countryRequest.toString());
        try{
            String response = countryService.createCountry(countryRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (CountryAlreadyExist e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/Country")
    public ResponseEntity<?> CountryS(){
        return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.OK);
    }

    @GetMapping("/Country/id/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable int id){
        log.info(String.valueOf(id));
        return new ResponseEntity<>(countryService.getAllStates(id) ,HttpStatus.OK);
    }

}
