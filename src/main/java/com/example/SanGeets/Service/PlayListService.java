package com.example.SanGeets.Service;


import com.example.SanGeets.DAO.PlayListDAO;
import com.example.SanGeets.DAO.PlayListSongsDAO;
import com.example.SanGeets.DAO.SongDAO;
import com.example.SanGeets.DAO.UserDAO;
import com.example.SanGeets.DTO.Request.AddnewSongRequest;
import com.example.SanGeets.DTO.Request.PlayListRequest;
import com.example.SanGeets.DTO.Request.RemovePlaylist;
import com.example.SanGeets.DTO.Response.PlayListResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Model.PlayList;
import com.example.SanGeets.Model.PlayListSongs;
import com.example.SanGeets.Model.Songs;
import com.example.SanGeets.Model.User;
import com.example.SanGeets.Utility.Transformers.PlayListTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayListService {
    private final UserDAO userDAO;
    private final PlayListDAO playListDAO;
    private final SongDAO songDAO;
    private final PlayListSongsDAO playListSongsDAO;


    public String createPlayList(String email, PlayListRequest playListRequest) {

        // 1. User check
        User user = userDAO.findByEmail(email);
        if (user == null) {
            System.out.println("User not found");
            throw new UserNotfound("User not found");
        }

        // 2. Playlist duplicate check
        PlayList existingPlayList = playListDAO.findByTitle(playListRequest.getTitle());
        if (existingPlayList != null) {
            throw new PlayListAlreadyExist("PlayList already exist");
        }

        // 3. Song check
        Songs songs = songDAO.findById(playListRequest.getSongId())
                .orElseThrow(() -> new SongNotFound("Song not found"));

        // 4. Create playlist
        PlayList playList = PlayListTransformer.playlistRequestToPlayList(playListRequest);
        playList.setUser(user);

        PlayList savedPlayList = playListDAO.save(playList);

        // 5. Playlist-Song mapping
        PlayListSongs playListSongs = new PlayListSongs();
        playListSongs.setPlayList(savedPlayList);
        playListSongs.setSong(songs);

        playListSongsDAO.save(playListSongs);

        return "Success";
    }


    public String addnewSong(String email, AddnewSongRequest addnewSongRequest) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotfound("User not found");
        }

        PlayList playList =playListDAO.findByTitle(addnewSongRequest.getPlaylistName());
        if (playList == null) {
            System.out.println("playlist not found");
            throw new PlayListNotFounded("PlayList not founded");

        }



        Optional<Songs> songs = Optional.of(songDAO.findById(addnewSongRequest.getSongid())
                .orElseThrow(() -> new SongNotFound("Song Not founded")));

        PlayListSongs playListSongs = playListSongsDAO.findBySong_Id(addnewSongRequest.getSongid());
        if(playListSongs!=null){
            throw new SongalreadyExistinPlaylist("Song is present in a playList already exist");

        }

        PlayListSongs playListSong = new PlayListSongs();
        playListSong.setPlayList(playList);

        playListSong.setSong(songs.get());
        playListSongsDAO.save(playListSong);
        return "Success";

    }

    @Transactional
    public String removeSong(String email, AddnewSongRequest addnewSongRequest) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotfound("User not found");
        }

        PlayList playList = playListDAO.findByTitle(addnewSongRequest.getPlaylistName());
        if (playList == null) {
            throw new PlayListNotFounded("PlayList not found");
        }
        PlayListSongs playListSongs =  playListSongsDAO.findBySong_Id(addnewSongRequest.getSongid());
        if(playListSongs==null){
            throw new SongNotFound("Song was remove by artist");

        }

        playListSongsDAO.deleteBySong_Id(addnewSongRequest.getSongid());
        return "Success";
    }


    public String removePlaylist(String email, RemovePlaylist playlist) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotfound("User not found");
        }

        PlayList playList = playListDAO.findByTitle(playlist.getTitle());
        if (playList == null) {
            throw new PlayListNotFounded("PlayList not found");
        }
        List<PlayListSongs> listSongs = playListSongsDAO.findByPlayList_PlaylistId(playList.getPlaylistId());
        playListSongsDAO.deleteAll(listSongs);
        playListDAO.delete(playList);
        return "Success";
    }


    public String Changeplaylistitle(String email, RemovePlaylist removePlaylist) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotfound("User not found");
        }

        PlayList playList = playListDAO.findByTitle(removePlaylist.getTitle());
        if (playList == null) {
            throw new PlayListNotFounded("PlayList not found");
        }

        playList.setTitle(removePlaylist.getNewTitle());
        playListDAO.save(playList);
        return "Success";


    }


}
