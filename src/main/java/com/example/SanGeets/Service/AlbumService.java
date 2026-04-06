package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.AlbumDAO;
import com.example.SanGeets.DAO.AlbumSongIDDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.SongDAO;
import com.example.SanGeets.DTO.Request.AlbumRequest;
import com.example.SanGeets.DTO.Request.AlbumTitleChangeRequest;
import com.example.SanGeets.DTO.Request.ChangeAlbumImages;
import com.example.SanGeets.DTO.Request.RemoveAlbums;
import com.example.SanGeets.DTO.Response.AlbumSongcount;
import com.example.SanGeets.Exceptions.AlbumNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Model.Album;
import com.example.SanGeets.Model.AlbumsSongIDs;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Songs;
import com.example.SanGeets.Utility.Transformers.AlbumTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {
    private final AlbumDAO albumDAO;
    private final AlbumSongIDDAO albumSongIDDAO;
    private final ArtistDAO artistDAO;

    @Autowired public SongDAO songDAO;


    public String albumCreate(String email ,AlbumRequest albumRequest) throws IOException {

        Artist artist = artistDAO.findByEmail(email);
        if(artist==null){
            throw new ArtistNotFound(email);
        }

        Album album = AlbumTransformer.albumRequestToAlbum(albumRequest);

        if (albumRequest.getImage() != null && !albumRequest.getImage().isEmpty()) {
            album.setImage(albumRequest.getImage().getBytes());
        }

        album.setArtist(artist);
        albumDAO.save(album);

        return "Album Created Successfully";
    }

    public String ChangeAlbumTitle(String email , AlbumTitleChangeRequest albumTitleChangeRequest){
        Artist artist = artistDAO.findByEmail(email);
        if(artist == null){
            throw new ArtistNotFound("Artist not found");
        }
        Album album = albumDAO.findByTitle(albumTitleChangeRequest.getOldTitle());
        if(album == null){
            throw new AlbumNotFound("Album not found");
        }
        album.setTitle(albumTitleChangeRequest.getNewTitle());
        albumDAO.save(album);
        return "Album Title Changed Successfully";
    }

    public String ChangeAlbumImage(String email , ChangeAlbumImages albumTitleChangeRequest) throws  IOException{
        Artist artist = artistDAO.findByEmail(email);
        if(artist == null){
            throw new ArtistNotFound("Artist not found");
        }
        Album album = albumDAO.findByTitle(albumTitleChangeRequest.getOldTitle());
        if(album == null){
            throw new AlbumNotFound("Album not found");
        }
        if(albumTitleChangeRequest.getNewImage()!=null &&  !albumTitleChangeRequest.getNewImage().isEmpty()){
            album.setImage(albumTitleChangeRequest.getNewImage().getBytes());
        }

        albumDAO.save(album);
        return "Album Image Changed Successfully";
    }


    public String reomveAlbum (String email , RemoveAlbums removeAlbums){
        Artist artist = artistDAO.findByEmail(email);
        if(artist == null){
            throw new ArtistNotFound("Artist not found");
        }
        Album album = albumDAO.findByTitle(removeAlbums.getTitle());
        if(album == null){
            throw new AlbumNotFound("Album not found");
        }
        albumDAO.delete(album);
        return "Album Removed Successfully";
    }

    public long countalbums(String email){
        Artist artist = artistDAO.findByEmail(email);
        if(artist == null){
            System.out.println(46);
            throw new ArtistNotFound("Artist not found");
        }
        List<Album> albums = albumDAO.findAll();
        System.out.println(67);
        return albums.size();
    }

    public List<AlbumSongcount> getcounts(String email) {

        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) {
            throw new ArtistNotFound("Artist not found");
        }

        List<Album> albums = albumDAO.findByArtistId(artist.getId());
        List<AlbumSongcount> albumSongcounts = new ArrayList<>();

        for (Album album : albums) {

            long count = albumSongIDDAO.countByAlbum_Id(album.getId());

            AlbumSongcount albumSongcount = new AlbumSongcount();
            albumSongcount.setTitle(album.getTitle());
            albumSongcount.setCount(count);

            albumSongcounts.add(albumSongcount);
        }

        return albumSongcounts;
    }








}
