package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.PlayListSongs;
import com.example.SanGeets.Model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlayListSongsDAO extends JpaRepository<PlayListSongs, Long> {

    void deleteBySong_Id(Long songId);

    Optional<PlayListSongs>
    findByPlayList_PlaylistIdAndSong_Id(Long playlistId, Long songId);
}
