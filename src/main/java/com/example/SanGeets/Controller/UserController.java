package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.*;
import com.example.SanGeets.DTO.Response.UserResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


//@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        log.info("User created");
        System.out.println(userRequest);
        try{
            String response = userService.createUser(userRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (UserAlreadyPresent | AlreadyUserin |CountryNotFounded|StateNotFounded|LanguageNotfound a){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", true,
                            "message", a.getMessage()
                    ).toString());
        }
    }

    @PutMapping("/User/Email")
    public ResponseEntity<?> changeEmail(Authentication authentication , @RequestBody UpdateEmail updateEmail){
        log.info("Changing email");
        try{
            String email = authentication.getName();
            String response = userService.changeEmail(email,updateEmail);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (UserNotfound e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/User")
    public ResponseEntity<?> changePassword( Authentication authentication,@RequestBody UserEmailChange userEmailChange){
        log.info("Changing password");
        try{
            String email = authentication.getName();
            String response = userService.changePassword(email,userEmailChange);
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



    @PutMapping("/")
    public ResponseEntity<?> ChangeUsername(Authentication authentication, @RequestBody ChangeNamesRequest changeNamesRequest){
        log.info("Changing username");
        try{
            String email = authentication.getName();
            String response = userService.changeName(email,changeNamesRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (UserNotfound e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

    }



}
