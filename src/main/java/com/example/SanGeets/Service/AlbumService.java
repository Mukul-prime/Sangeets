package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.AlbumDAO;
import com.example.SanGeets.DAO.AlbumSongIDDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DTO.Request.AlbumRequest;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.ImageNotInserted;
import com.example.SanGeets.Model.Album;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Utility.Transformers.AlbumTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {
    private final AlbumDAO albumDAO;
//    private final AlbumSongIDDAO albumSongIDDAO;
    private final ArtistDAO artistDAO;


    public String albumCreate(AlbumRequest albumRequest) throws IOException {

        Artist artist = artistDAO.findById(albumRequest.getArtistId())
                .orElseThrow(() -> new ArtistNotFound("Artist not found"));

        Album album = AlbumTransformer.albumRequestToAlbum(albumRequest);

        if (albumRequest.getImage() != null && !albumRequest.getImage().isEmpty()) {
            album.setImage(albumRequest.getImage().getBytes());
        }

        album.setArtist(artist);
        albumDAO.save(album);

        return "Album Created Successfully";
    }

}
