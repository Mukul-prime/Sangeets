package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.CountryDAO;
import com.example.SanGeets.DTO.Request.CountryRequest;
import com.example.SanGeets.Exceptions.CountryAlreadyExist;
import com.example.SanGeets.Exceptions.CountryNotFounded;
import com.example.SanGeets.Model.Country;
import com.example.SanGeets.Utility.Transformers.CountryTransfomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {

    private  final CountryDAO countryDAO;


    public String createCountry(CountryRequest countryRequest){
        Country country = countryDAO.findBycountryname(countryRequest.getCountryName());
        if(country != null){
            throw new CountryAlreadyExist("Country already exists");
        }
        Country newContry = CountryTransfomer.CountryRequestToCountry(countryRequest);
         countryDAO.save(newContry);
        return "Created";

    }


}
