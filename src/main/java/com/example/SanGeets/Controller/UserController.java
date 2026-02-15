package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.PlayListUpdate;
import com.example.SanGeets.DTO.Request.UserEmailChange;
import com.example.SanGeets.DTO.Request.UserRequest;
import com.example.SanGeets.DTO.Response.UserResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @GetMapping
    public String GetWelcome(){
        return "Welcome to SanGeet";
    }


    @PostMapping("/User")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        log.info("User created");
        try{
            UserResponse response = userService.createUser(userRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (UserAlreadyPresent | AlreadyUserin a){
            return new ResponseEntity<>(a, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/User/Email/{email}")
    public ResponseEntity<?> changeEmail(@PathVariable  String email){
        log.info("Changing email");
        try{
            String response = userService.changeEmail(email);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (UserNotfound e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/User")
    public ResponseEntity<?> changePassword(@RequestBody UserEmailChange userEmailChange){
        log.info("Changing password");
        try{
            String response = userService.changePassword(userEmailChange);
            return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
        }
        catch (UserNotfound e){
            return new  ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/User")
    public ResponseEntity<?> removSongfromPlayList(@RequestBody PlayListUpdate playListUpdate){
        log.info("Removing song from playlist");
        try{
            String response = userService.removeSongFromplayList(playListUpdate);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (UserNotfound | PlayListNotFounded | SongNotFound | SondDeleteAlready e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

    }



}
