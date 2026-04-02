package com.example.SanGeets.Controller;

import com.example.SanGeets.DTO.Request.AdminRequestCreate;
import com.example.SanGeets.Exceptions.AdminAlreadyCreated;
import com.example.SanGeets.Exceptions.ArtistAlreadyin;
import com.example.SanGeets.Exceptions.UserAlreadyPresent;
import com.example.SanGeets.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping()
    public ResponseEntity<?> createadmin(@Valid @RequestBody AdminRequestCreate adminRequestCreate) {
        log.debug("AdminRequestCreate");
        try{
            String response = adminService.Admincreate(adminRequestCreate);
            return ResponseEntity.ok().body(response);
        }
        catch (AdminAlreadyCreated | ArtistAlreadyin | UserAlreadyPresent | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
