package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDAO extends JpaRepository<Country, Long> {
    Country findBycountryname(String countryname);
    Country findByCountryId(Long countryId);
}
