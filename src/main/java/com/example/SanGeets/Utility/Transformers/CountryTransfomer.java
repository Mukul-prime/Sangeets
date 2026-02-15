package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.CountryRequest;
import com.example.SanGeets.Model.Country;

public class CountryTransfomer {
    public static Country CountryRequestToCountry(CountryRequest countryRequest){
        return Country.builder().countryname(countryRequest.getCountryName()).build();
    }
}
