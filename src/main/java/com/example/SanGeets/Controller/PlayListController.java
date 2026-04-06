package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.AddnewSongRequest;
import com.example.SanGeets.DTO.Request.PlayListRequest;
import com.example.SanGeets.DTO.Request.RemovePlaylist;
import com.example.SanGeets.Exceptions.PlayListNotFounded;
import com.example.SanGeets.Exceptions.SongNotFound;
import com.example.SanGeets.Exceptions.SongalreadyExistinPlaylist;
import com.example.SanGeets.Exceptions.UserNotfound;
import com.example.SanGeets.Service.PlayListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/PlayList")
@Slf4j
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;


    @PostMapping("/Playlist")
    public ResponseEntity<?> createPlayList(Authentication authentication,@RequestBody PlayListRequest playListRequest) {
        log.info("Creating playlist {}", playListRequest.getTitle());
        try {
            String email  = authentication.getName();

            String response = playListService.createPlayList(email, playListRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/PlaylistSong")
    public ResponseEntity<?> AddnewSong(Authentication authentication, @RequestBody AddnewSongRequest addnewSongRequest) {
        log.info("Addnew song in a list");
        try {
            String email = authentication.getName();
            String response = playListService.addnewSong(email, addnewSongRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserNotfound | PlayListNotFounded | SongNotFound | SongalreadyExistinPlaylist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/PlayList")
    public ResponseEntity<?> removeSongs(Authentication authentication, @RequestBody AddnewSongRequest addnewSongRequest) {
        log.info("Remove songs in a list");
        try {
            String email = authentication.getName();
            String response = playListService.removeSong(email, addnewSongRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserNotfound | PlayListNotFounded | SongNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/PLaylistDelete")
    public ResponseEntity<?> RemovePlaylist(Authentication authentication , @RequestBody RemovePlaylist playlist){
        log.info("Remove playlist in a list");
        try{
            String email = authentication.getName();
            String response = playListService.removePlaylist(email, playlist);
            return new ResponseEntity<>(response, HttpStatus.CREATED);


        }
        catch (UserNotfound | PlayListNotFounded e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/PlayList")
    public ResponseEntity<?> ChangePlaylistTitle(Authentication authentication , @RequestBody RemovePlaylist removePlaylist){
        log.info("Change playlist title in a list");
        try{
            String email = authentication.getName();
            String response = playListService.Changeplaylistitle(email, removePlaylist);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (UserNotfound | PlayListNotFounded e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}
