package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.AddAlbumSongsRequest;
import com.example.SanGeets.Exceptions.AlbumNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.SongNotFound;
import com.example.SanGeets.Service.AddSongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/Albumsong")
public class AddAlbumController {

    private final AddSongService addSongService;

    @GetMapping("/")
    public String welcome (){
        return "welcome album songs add";

    }


    @PostMapping("/Albumsong")
    public ResponseEntity<?> CreateAddsong(@RequestBody AddAlbumSongsRequest addAlbumSongsRequest){
        log.info("Adding songs to album");
        log.info("ArtistID = {}", addAlbumSongsRequest.getArtistID());
        log.info("SongID   = {}", addAlbumSongsRequest.getSongID());
        log.info("AlbumID  = {}", addAlbumSongsRequest.getAlbumID());

        try{
            String response = addSongService.CreateAddsong(addAlbumSongsRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ArtistNotFound | SongNotFound | AlbumNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
