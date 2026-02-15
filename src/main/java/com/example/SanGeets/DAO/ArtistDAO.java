package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistDAO extends JpaRepository<Artist,Long> {

    Artist findByEmail(String email);
}
