package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.PlayListRequest;
import com.example.SanGeets.Service.PlayListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/PlayList")
@Slf4j
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @GetMapping("/")
    public String welcome(){

        return "Welcome to the PlayListController!";
    }


    @PostMapping("/Playlist")
    public ResponseEntity<?> createPlayList(@RequestBody PlayListRequest playListRequest){
        log.info("Creating playlist {}", playListRequest.getTitle());
        try{
            String response = playListService.createPlayList(playListRequest);
            return new ResponseEntity<>(response , HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
