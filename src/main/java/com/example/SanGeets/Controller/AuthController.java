package com.example.SanGeets.Controller;



import com.example.SanGeets.DTO.Response.LoginResponse;
import com.example.SanGeets.Exceptions.AdminNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.UserNotfound;
import com.example.SanGeets.DTO.Request.Loginrequest;
import com.example.SanGeets.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/Auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

   private final AuthService authService;
    @PostMapping("/Auth")
    public ResponseEntity<?> loginUser(@RequestBody Loginrequest loginrequest ){

        try{
            LoginResponse responseEntity = authService.login(loginrequest);
            return new ResponseEntity<>(responseEntity, HttpStatus.ACCEPTED);
        }
        catch (UserNotfound e){
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);

        }

    }
    @PostMapping("/Artist")
    public ResponseEntity<?> loginArtist(@RequestBody Loginrequest loginrequest){
        try{
            System.out.println("Login Artist");
            LoginResponse responseEntity = authService.loginartist(loginrequest);
            return new ResponseEntity<>(responseEntity, HttpStatus.ACCEPTED);
        }
        catch (ArtistNotFound e){
            return new ResponseEntity<>("Artist not found", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/Admin")
    public ResponseEntity<?> loginAdmin(@RequestBody Loginrequest loginrequest)
    {
        try
        {
            System.out.println("Login Admin");
            LoginResponse loginResponse = authService.LoginAdmin(loginrequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        }catch (AdminNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }


    }
}
