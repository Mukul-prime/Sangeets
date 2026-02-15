package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreDAO extends JpaRepository<Genre,Long> {
    Genre findByName(String name);
}
