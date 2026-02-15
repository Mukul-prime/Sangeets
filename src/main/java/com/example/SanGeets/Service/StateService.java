package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.CountryDAO;
import com.example.SanGeets.DAO.StateDAO;
import com.example.SanGeets.DTO.Request.StateRequest;
import com.example.SanGeets.DTO.Response.StateResponse;
import com.example.SanGeets.Exceptions.CountryAlreadyExist;
import com.example.SanGeets.Exceptions.CountryNotFounded;
import com.example.SanGeets.Exceptions.StateAlreadyExist;
import com.example.SanGeets.Model.Country;
import com.example.SanGeets.Model.State;
import com.example.SanGeets.Utility.Transformers.StateTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StateService {

    private final CountryDAO countryDAO;
    private final StateDAO stateDAO;

    public StateResponse createState(StateRequest stateRequest){
        Country country = countryDAO.findByCountryId(stateRequest.getCountry());
        if(country==null){
            throw new CountryNotFounded("Country Not Found");
        }

        State state = stateDAO.findByStateName(stateRequest.getStateName());
        if(state!=null){
            throw new StateAlreadyExist("State Already Exist");
        }

        State state1 = StateTransformer.stateRequestToState(stateRequest);
        state1.setCountry(country);
        State a = stateDAO.save(state1);
        return StateTransformer.stateToStateResponse(a);



    }
}
