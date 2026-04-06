package com.example.SanGeets.Service;


//import com.example.SanGeets.Controller.DeletesongFromsRequest;

import com.example.SanGeets.DAO.*;
import com.example.SanGeets.DTO.Request.*;
import com.example.SanGeets.DTO.Response.SongResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Genre;
import com.example.SanGeets.Model.Songs;

import com.example.SanGeets.Model.Thubnails;
import com.example.SanGeets.Utility.Transformers.SongTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {

    private final SongDAO songDAO;
    private final ArtistDAO artistDAO;
    private final GenreDAO genreDAO;
    private final PlayListSongsDAO playListSongsDAO;
    private final AlbumSongIDDAO albumSongIDDAO;
    private final ThumbnailDAOe thumbnailDAOe;

    public SongResponse createSong(SongRequest songRequest, String email) throws IOException {


        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) throw new ArtistNotFound(email);


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
        Thubnails thubnails = new Thubnails();



        if(songRequest.getThumbnail() != null && !songRequest.getThumbnail().isEmpty()) {
            MultipartFile thumbnail = songRequest.getThumbnail();
            byte[] thumbnailBytes = thumbnail.getBytes();
            thubnails.setAudio(thumbnailBytes);

        }
        thubnails.setSongs(song);

        song.setThubnails(thubnails);



        Long duration = convertToSeconds(songRequest.getDuration());

        song.setArtist(artist);
        song.setGenre(genre);
        song.setDuration(duration);
        song.setType(songRequest.getType());
        Songs savedSong = songDAO.save(song);
        thumbnailDAOe.save(thubnails);
        SongResponse response = SongTransformer.SongToSongResponse(savedSong);



        return response;
    }

    //    Delete a Song By artist
    @Transactional
    public String artistRemoveSongs(String email, DeleteSongRequest deleteSongRequest) {

        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) throw new ArtistNotFound(email);


        Songs songs = songDAO.findById(deleteSongRequest.getSongId())
                .orElseThrow(() -> new SongNotFound("Song Not Found"));

        // Security Check
        if (!songs.getArtist().getId().equals(deleteSongRequest.getArtistId())) {
            throw new RuntimeException("You cannot delete this song");
        }

        playListSongsDAO.deleteBySong_Id(deleteSongRequest.getSongId());
        albumSongIDDAO.deleteBySongs_Id(deleteSongRequest.getSongId());

        songDAO.delete(songs);

        return "Successfully removed your song";
    }

    public long convertToSeconds(String duration) {
        String[] parts = duration.trim().split(":");

        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        System.out.println(9);


        try {
            if (parts.length == 3) {
                hours = Long.parseLong(parts[0]);
                minutes = Long.parseLong(parts[1]);
                seconds = Long.parseLong(parts[2]);
            }
            else if (parts.length == 2) {
                minutes = Long.parseLong(parts[0]);
                seconds = Long.parseLong(parts[1]);
            }
            else if (parts.length == 1) {
                seconds = Long.parseLong(parts[0]);
            }
            else {
                throw new IllegalArgumentException("Invalid duration format");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number in duration");
        }

        if (minutes >= 60 || seconds >= 60) {
            throw new IllegalArgumentException("Minutes and seconds must be < 60");
        }

        return (hours * 3600) + (minutes * 60) + seconds;
    }


    public String changeDuration(String email , ChangedurationRequest changedurationRequest){
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) throw new ArtistNotFound(email);

        System.out.println(78);

        Songs songs = songDAO.findById(changedurationRequest.getId()).orElseThrow(() -> new SongNotFound("Song Not Found"));
        System.out.println(354);
        Long duration = convertToSeconds(changedurationRequest.getDuration());
        System.out.println(45);
        songs.setDuration(duration);
        songDAO.save(songs);
        return "Successfully changed your duration";
    }

    public String changetitle(String email , ChangesongTitle changesongTitle){
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) throw new ArtistNotFound(email);

        Songs songs =  songDAO.findById(changesongTitle.getId()).orElseThrow(() -> new SongNotFound("Song Not Found"));
        songs.setTitle(changesongTitle.getTitle());
        songDAO.save(songs);
        return "Successfully changed your title";
    }

    public String ChangeBanner(String email , ChangeBanner changeBanner) throws IOException {
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) throw new ArtistNotFound(email);

        Songs songs = songDAO.findById(changeBanner.getId()).orElseThrow(() -> new SongNotFound("Song Not Found"));
        if (changeBanner.getBanner() != null && !changeBanner.getBanner().isEmpty()) {
            songs.setImage(changeBanner.getBanner().getBytes());
        }
        songDAO.save(songs);
        return "Successfully changed your banner";
    }

    public List<SongResponse> songResponseList() {
        List<Songs> songs = songDAO.findAll();
        List<SongResponse> songResponseList = songs.stream().map(SongTransformer::SongToSongResponse).collect(Collectors.toList());
        return songResponseList;

    }

    public String ChangeSongType(String email , Changetype changetype){
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) throw new ArtistNotFound(email);

        Songs songs = songDAO.findById(changetype.getId()).orElseThrow(() -> new SongNotFound("Song Not Found"));
        songs.setType(changetype.getType());
        songDAO.save(songs);
        return "Successfully changed your type";
    }








}
