package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.AlbumsSongIDs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumSongIDDAO extends JpaRepository<AlbumsSongIDs,Long> {
    void deleteBySongs_Id(Long songId);


}
