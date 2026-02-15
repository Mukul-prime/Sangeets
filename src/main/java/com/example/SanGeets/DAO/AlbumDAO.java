package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumDAO  extends JpaRepository<Album,Long> {
}
