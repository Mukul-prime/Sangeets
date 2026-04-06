package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.AlbumRequest;
import com.example.SanGeets.DTO.Request.AlbumTitleChangeRequest;
import com.example.SanGeets.DTO.Request.ChangeAlbumImages;
import com.example.SanGeets.DTO.Request.RemoveAlbums;
import com.example.SanGeets.DTO.Response.AlbumSongcount;
import com.example.SanGeets.Exceptions.AlbumNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Album")
@Slf4j
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/")
    public String welcome(){
        return "Welcome to SanGeets!";
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> albumCreate(Authentication authentication,@ModelAttribute AlbumRequest albumRequest){
        log.info("albumCreate");
        try{
            String email = authentication.getName();
            String response = albumService.albumCreate(email,albumRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ArtistNotFound | IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changealbumImage(Authentication authentication , @ModelAttribute ChangeAlbumImages changeAlbumImages){
        log.info("changealbumImage");
        try{
            String email =  authentication.getName();
            String response = albumService.ChangeAlbumImage(email,changeAlbumImages);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ArtistNotFound | IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> changeAlbumTitle(Authentication authentication , @RequestBody AlbumTitleChangeRequest changeRequest){
        log.info("changeAlbumTitle");
        try{
            String email =  authentication.getName();
            String response = albumService.ChangeAlbumTitle(email,changeRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ArtistNotFound | AlbumNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reomveit")
    public ResponseEntity<?> removeAlbum(Authentication authentication , @RequestBody RemoveAlbums removeAlbums){
        log.info("removeAlbum");
        try{
            String email =  authentication.getName();
            String response = albumService.reomveAlbum(email,removeAlbums);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ArtistNotFound | AlbumNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/Album")
    public ResponseEntity<?> countalbum(Authentication authentication){
        log.info("countalbum");
        try{
            String email =  authentication.getName();
            System.out.println(45);
            long resposne = albumService.countalbums(email);
            return new ResponseEntity<>("Count : "+resposne, HttpStatus.OK);
        }
        catch (ArtistNotFound | AlbumNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/Albums")
    public ResponseEntity<?>  getCountalbumsong(Authentication authentication){
        log.info("getCountalbumsong");
        try{
            String email =  authentication.getName();
            List<AlbumSongcount> data =albumService.getcounts(email);
            return new ResponseEntity<>(data, HttpStatus.OK);

        }catch (ArtistNotFound | AlbumNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
