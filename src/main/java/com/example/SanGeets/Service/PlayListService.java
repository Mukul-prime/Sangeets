package com.example.SanGeets.Service;


import com.example.SanGeets.DAO.PlayListDAO;
import com.example.SanGeets.DAO.PlayListSongsDAO;
import com.example.SanGeets.DAO.SongDAO;
import com.example.SanGeets.DAO.UserDAO;
import com.example.SanGeets.DTO.Request.PlayListRequest;
import com.example.SanGeets.DTO.Response.PlayListResponse;
import com.example.SanGeets.Exceptions.PlayListAlreadyExist;
import com.example.SanGeets.Exceptions.SongNotFound;
import com.example.SanGeets.Exceptions.UserNotfound;
import com.example.SanGeets.Model.PlayList;
import com.example.SanGeets.Model.PlayListSongs;
import com.example.SanGeets.Model.Songs;
import com.example.SanGeets.Model.User;
import com.example.SanGeets.Utility.Transformers.PlayListTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayListService {
    private final UserDAO userDAO;
    private final PlayListDAO playListDAO;
    private final SongDAO songDAO;
    private final PlayListSongsDAO playListSongsDAO;


    public String createPlayList(PlayListRequest playListRequest) {

        // 1. User check
        User user = userDAO.findById(playListRequest.getUserId())
                .orElseThrow(() -> new UserNotfound("User not found"));

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


//    public String removesongfromeplayList(){
//
//    }


}
