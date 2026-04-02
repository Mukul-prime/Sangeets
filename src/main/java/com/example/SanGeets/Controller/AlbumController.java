package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.AlbumRequest;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Model.Album;
import com.example.SanGeets.Service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<?> albumCreate(@ModelAttribute AlbumRequest albumRequest){
        log.info("albumCreate");
        try{
            String response = albumService.albumCreate(albumRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ArtistNotFound | IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
