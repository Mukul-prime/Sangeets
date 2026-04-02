package com.example.SanGeets.Controller;

import com.example.SanGeets.DAO.StateDAO;
import com.example.SanGeets.DTO.Request.StateRequest;
import com.example.SanGeets.DTO.Response.StateResponse;
import com.example.SanGeets.Exceptions.CountryNotFounded;
import com.example.SanGeets.Exceptions.StateAlreadyExist;
import com.example.SanGeets.Service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/State")
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;
    @GetMapping("/")
    public String welcome(){
        return  "Welcome to state SanGeets!";
    }

    @PostMapping("/")
    public ResponseEntity<?> createState( @RequestBody  StateRequest stateRequest){
        log.debug("createState");
        try{

            StateResponse stateResponse = stateService.createState(stateRequest);
            return new ResponseEntity<>(stateResponse , HttpStatus.CREATED);

        }
        catch (CountryNotFounded | StateAlreadyExist e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/States")
    public ResponseEntity<?> getAllStates(){
        return new ResponseEntity<>(stateService.getAllStates(), HttpStatus.OK);
    }





}
