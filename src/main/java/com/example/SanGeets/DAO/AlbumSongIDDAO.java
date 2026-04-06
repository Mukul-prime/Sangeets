package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.AlbumsSongIDs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumSongIDDAO extends JpaRepository<AlbumsSongIDs,Long> {
    void deleteBySongs_Id(Long songId);
    long countByAlbum_Id(Long albumId);
    boolean existsBySongs_Id(Long songId);
    List<AlbumsSongIDs> findByAlbum_Id(Long albumId);



}
