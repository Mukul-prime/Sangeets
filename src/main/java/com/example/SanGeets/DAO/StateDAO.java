package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDAO extends JpaRepository<State,Integer> {

    State findByStateName(String stateName);
}
