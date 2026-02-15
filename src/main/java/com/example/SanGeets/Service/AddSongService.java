package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.AlbumDAO;
import com.example.SanGeets.DAO.AlbumSongIDDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.SongDAO;
import com.example.SanGeets.DTO.Request.AddAlbumSongsRequest;
import com.example.SanGeets.Exceptions.AlbumNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.SongNotFound;
import com.example.SanGeets.Model.Album;
import com.example.SanGeets.Model.AlbumsSongIDs;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Songs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddSongService {
    private final AlbumSongIDDAO  albumSongIDDAO;
//    private final AlbumsSongIDs albumsSongIDs;
    private final ArtistDAO  artistDAO;
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;


    public String CreateAddsong(AddAlbumSongsRequest addAlbumSongsRequest){
        Artist artist =  artistDAO.findById(addAlbumSongsRequest.getArtistID())
                .orElseThrow(()->new  ArtistNotFound("Artist not exisit"));


        Songs songs = songDAO.findById(addAlbumSongsRequest.getSongID())
                .orElseThrow(()-> new SongNotFound("Song not exisit"));

        Album album = albumDAO.findById(addAlbumSongsRequest.getAlbumID())
                .orElseThrow(()->new AlbumNotFound("Album not exisit"));


        AlbumsSongIDs  albumsSongIDs = new AlbumsSongIDs();
        albumsSongIDs.setAlbum(album);
        albumsSongIDs.setArtist(artist);
        albumsSongIDs.setSongs(songs);

        albumSongIDDAO.save(albumsSongIDs);

        return "Album Added Successfully";


    }
}
