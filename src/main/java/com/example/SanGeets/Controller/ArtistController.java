package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.ArtistRequest;
import com.example.SanGeets.DTO.Request.DeleteSongRequest;
import com.example.SanGeets.DTO.Response.ArtistReponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Service.ArtistService;
import com.example.SanGeets.Service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/Artist")
@Slf4j
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    @GetMapping("/")
    public String welcomeArtist(){
        return "welcome Artist";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> CreateArtist(@ModelAttribute ArtistRequest artistRequest){
       log.info(artistRequest.getArtistName());
       try{
           ArtistReponse response = artistService.createArtist(artistRequest);
           return new ResponseEntity<>(response, HttpStatus.CREATED);
       } catch (ArtistAlreadyin | ImageNotInserted | CountryNotFounded | IOException e) {

           return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
       }
    }


    @DeleteMapping("/")
    public ResponseEntity<?> artistRemoveSongs(@RequestBody DeleteSongRequest deletesongFromsRequest){
        log.info("Artist Remove Songs");
        try{
            String response =songService.artistRemoveSongs(deletesongFromsRequest);
            return  new ResponseEntity<>(response , HttpStatus.OK);
        }catch (ArtistNotFound  | SongNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

    }





}
