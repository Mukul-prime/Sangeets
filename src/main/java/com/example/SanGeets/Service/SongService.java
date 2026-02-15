package com.example.SanGeets.Service;


//import com.example.SanGeets.Controller.DeletesongFromsRequest;
import com.example.SanGeets.DAO.*;
import com.example.SanGeets.DTO.Request.DeleteSongRequest;
import com.example.SanGeets.DTO.Request.SongRequest;
import com.example.SanGeets.DTO.Response.SongResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Genre;
import com.example.SanGeets.Model.Songs;

import com.example.SanGeets.Utility.Transformers.SongTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {

    private final SongDAO songDAO;
    private final ArtistDAO artistDAO;
    private final GenreDAO genreDAO;
    private final PlayListSongsDAO playListSongsDAO;
    private final AlbumSongIDDAO albumSongIDDAO;

    public SongResponse createSong(SongRequest songRequest) throws IOException {


        Artist artist = artistDAO.findById(songRequest.getArtistId())
                .orElseThrow(() -> new ArtistNotFound(
                        "Artist not found with id " + songRequest.getArtistId()
                ));



        Genre genre = genreDAO.findById(songRequest.getGenreid())
                .orElseThrow(() -> new GenreNotFound("Genre not found"));


        Songs existingSong = songDAO.findByTitle(songRequest.getTitle());
        if (existingSong != null) {
            throw new SongAlreadyExisit("Song already exists with title " + songRequest.getTitle());
        }


        Songs song = SongTransformer.SongResquestToSong(songRequest);


        if (songRequest.getBanner() != null && !songRequest.getBanner().isEmpty()) {
            song.setImage(songRequest.getBanner().getBytes());
        }
        if (songRequest.getAudio() != null) {
            System.out.println("Audio name: " + songRequest.getAudio().getOriginalFilename());
            System.out.println("Audio size: " + songRequest.getAudio().getSize());
        }



        if (songRequest.getAudio() != null && !songRequest.getAudio().isEmpty()) {
            MultipartFile audio = songRequest.getAudio();
            byte[] audioBytes = audio.getBytes();
            song.setAudio(audioBytes);


        }

        song.setArtist(artist);
        song.setGenre(genre);



        Songs savedSong = songDAO.save(song);


        SongResponse response = SongTransformer.SongToSongResponse(savedSong);

        return response;
    }

//    Delete a Song By artist
@Transactional
public String artistRemoveSongs(DeleteSongRequest deleteSongRequest){

    Songs songs = songDAO.findById(deleteSongRequest.getSongId())
            .orElseThrow(() -> new SongNotFound("Song Not Found"));

    // Security Check
    if(!songs.getArtist().getId().equals(deleteSongRequest.getArtistId())){
        throw new RuntimeException("You cannot delete this song");
    }

    playListSongsDAO.deleteBySong_Id(deleteSongRequest.getSongId());
    albumSongIDDAO.deleteBySongs_Id(deleteSongRequest.getSongId());

    songDAO.delete(songs);

    return "Successfully removed your song";
}



}
