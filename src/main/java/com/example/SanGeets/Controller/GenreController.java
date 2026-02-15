package com.example.SanGeets.Controller;

import com.example.SanGeets.DAO.GenreDAO;
import com.example.SanGeets.DTO.Request.GenreRequest;
import com.example.SanGeets.DTO.Response.GenreResponse;
import com.example.SanGeets.Exceptions.GenreAlreadyExisit;
import com.example.SanGeets.Service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ReflectiveScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Genre")
@Slf4j
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/")
    public String welcome(){
        return "Welcome to SanGeets!";
    }

    @PostMapping("/")
    public ResponseEntity<?> createGenre(@RequestBody GenreRequest genreRequest){
        log.info("Creating Genre");
        try{
            GenreResponse response = genreService.createGenre(genreRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (GenreAlreadyExisit e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.CONFLICT);
        }
    }

}
