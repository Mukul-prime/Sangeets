package com.example.SanGeets.DAO;

import com.example.SanGeets.DTO.Request.SongRequest;
import com.example.SanGeets.Model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongDAO extends JpaRepository<Songs,Long> {
    Songs findByTitle (String Title);
}
