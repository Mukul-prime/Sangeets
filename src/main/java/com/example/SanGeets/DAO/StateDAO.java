package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateDAO extends JpaRepository<State,Integer> {

    State findByStateName(String stateName);

    List<State> findByCountryCountryId(int countryId);
}
