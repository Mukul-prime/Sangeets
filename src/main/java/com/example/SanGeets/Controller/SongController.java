package com.example.SanGeets.Controller;

import com.example.SanGeets.DAO.SongDAO;
import com.example.SanGeets.DTO.Request.ChangeBanner;
import com.example.SanGeets.DTO.Request.ChangedurationRequest;
import com.example.SanGeets.DTO.Request.ChangesongTitle;
import com.example.SanGeets.DTO.Request.SongRequest;
import com.example.SanGeets.DTO.Response.SongResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Model.Songs;
import com.example.SanGeets.Service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Song")
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class SongController {

    private final SongService songService;
    private final SongDAO songDAO;


    @GetMapping("/")
    public String welcome() {
        return "Songs are welcome";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createSong(Authentication authentication, @ModelAttribute SongRequest songRequest) {
        log.debug("Creating song");
        try {
            String email = authentication.getName();
            SongResponse response = songService.createSong(songRequest,email);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ArtistNotFound| GenreNotFound|SongAlreadyExisit| IOException  e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/audio/{id}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Long id) {

        Songs song = songDAO.findById(id)
                .orElseThrow(() -> new SongNotFound("Song not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(song.getAudio().length))
                .body(song.getAudio());
    }


    @GetMapping("/banner/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        Songs song = songDAO.findById(id)
                .orElseThrow(() -> new ImageNotInserted("Song not found"));

        byte[] image = song.getImage();

        if (image == null || image.length == 0) {
            throw new ImageNotInserted("Image not found for song id " + id);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(image.length))
                .body(image);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,path = "/")
    public ResponseEntity<?> ChangeBanner(Authentication authentication, @RequestBody ChangeBanner changeBanner) {
        log.debug("Changing banner to song");
        try{
            String email = authentication.getName();
            String response = songService.ChangeBanner(email,changeBanner);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException | ArtistNotFound | SongNotFound e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/Title")
    public ResponseEntity<?> ChangeTitle(Authentication authentication, @RequestBody ChangesongTitle changesongTitle){
        log.debug("Changing title to song");
        try{
            String email = authentication.getName();
            String response = songService.changetitle(email,changesongTitle);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ArtistNotFound | SongNotFound e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/Duration")
    public ResponseEntity<?> changeDuration(Authentication authentication, @RequestBody ChangedurationRequest changedurationRequest) {
        log.debug("Changing duration to song");
        try{
            String email = authentication.getName();
            String response = songService.changeDuration(email,changedurationRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ArtistNotFound | SongNotFound e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }






}
