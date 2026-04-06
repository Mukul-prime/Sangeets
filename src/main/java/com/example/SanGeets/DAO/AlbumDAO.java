package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumDAO  extends JpaRepository<Album,Long> {
    Album findByTitle(String title);
    List<Album> findByArtistId(long id);

}
