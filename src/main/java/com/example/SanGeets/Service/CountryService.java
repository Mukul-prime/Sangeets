package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.CountryDAO;
import com.example.SanGeets.DAO.StateDAO;
import com.example.SanGeets.DTO.Request.CountryRequest;
import com.example.SanGeets.DTO.Response.CountryResponse;
import com.example.SanGeets.DTO.Response.StateResponse;
import com.example.SanGeets.Exceptions.CountryAlreadyExist;
import com.example.SanGeets.Exceptions.CountryNotFounded;
import com.example.SanGeets.Model.Country;
import com.example.SanGeets.Model.State;
import com.example.SanGeets.Utility.Transformers.CountryTransfomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {

    private  final CountryDAO countryDAO;

    private final StateDAO stateDAO;

    public String createCountry(CountryRequest countryRequest){
        Country country = countryDAO.findBycountryname(countryRequest.getCountryName());
        if(country != null){
            throw new CountryAlreadyExist("Country already exists");
        }
        Country newContry = CountryTransfomer.CountryRequestToCountry(countryRequest);
         countryDAO.save(newContry);
        return "Created";

    }

    public List<CountryResponse> getAllCountries() {

        List<Country> countries = countryDAO.findAll();
        List<CountryResponse> countryResponses = new ArrayList<>();

        for (Country country : countries) {

            CountryResponse response =
                    new CountryResponse(
                          country.getCountryId(),
                            country.getCountryname()
                    );

            countryResponses.add(response);
        }

        return countryResponses;
    }

//    @GetMapping("/State/id/{id}")
    public List<StateResponse> getAllStates(int id){
        List<State> states = stateDAO.findByCountryCountryId(id);
        List<StateResponse> stateResponses = new ArrayList<>();
        for(State state : states){
            StateResponse response = new StateResponse(
                    state.getStateId(),
                    state.getStateName(),
                    state.getCountry().getCountryname()


            );
            stateResponses.add(response);
        }
       return stateResponses;
    }


}
