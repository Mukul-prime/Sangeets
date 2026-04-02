package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.*;
import com.example.SanGeets.DTO.Response.ArtistReponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Service.ArtistService;
import com.example.SanGeets.Service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<?> artistRemoveSongs(Authentication authentication,@RequestBody DeleteSongRequest deletesongFromsRequest){
        log.info("Artist Remove Songs");
        try{
            String email = authentication.getName();
            String response =songService.artistRemoveSongs(email,deletesongFromsRequest);
            return  new ResponseEntity<>(response , HttpStatus.OK);
        }catch (ArtistNotFound  | SongNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/Artist/banner")
    public ResponseEntity<?> changeimage(Authentication authentication , ChangeBackgroundImage changeBackgroundImage) throws  IOException{
        log.info("Artist Change Image");
        try{
            String email =  authentication.getName();
            String response = artistService.changeimage(email,changeBackgroundImage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ArtistNotFound | ImageNotInserted e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/Artist/Bio")
    public ResponseEntity<?> changeBio(Authentication authentication , ChangeBio changeBio){
        log.info("Artist Change Bio");
        try{
            String email =  authentication.getName();
            String response = artistService.changebio(email,changeBio);
            return new ResponseEntity<>(response, HttpStatus.OK);


        }catch (ArtistNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/Artist/password")
    public ResponseEntity<?> changepassword(Authentication authentication , Changepassword changepassword){
        log.info("Artist Change Password");
        try{
            String email =  authentication.getName();
            String response = artistService.changepassword(email,changepassword);
            return new  ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ArtistNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/Artist/email")
    public ResponseEntity<?> changeemail(Authentication authentication , ChangeEmail changeEmail){
        log.info("Artist Change Email");
        try{
            String email =  authentication.getName();
            String response = artistService.changeEmail(email,changeEmail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ArtistNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }












}
