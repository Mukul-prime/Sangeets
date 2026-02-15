package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.StateRequest;
import com.example.SanGeets.DTO.Response.StateResponse;
import com.example.SanGeets.Model.State;

public class StateTransformer {
    public static State stateRequestToState(StateRequest stateRequest) {
        return State.builder()
                .stateCode(stateRequest.getStateCode())
                .stateName(stateRequest.getStateName())

                .build();
    }

    public static StateResponse stateToStateResponse(State state) {
        return StateResponse.builder()
                .stateName(state.getStateName())
                .country(state.getCountry().getCountryname())
                .build();
    }
}
