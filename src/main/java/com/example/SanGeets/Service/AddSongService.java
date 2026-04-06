package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.AlbumDAO;
import com.example.SanGeets.DAO.AlbumSongIDDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.SongDAO;
import com.example.SanGeets.DTO.Request.AddAlbumSongsRequest;
import com.example.SanGeets.DTO.Response.GetAllSonginaAlbum;
import com.example.SanGeets.DTO.Response.SongResponse;
import com.example.SanGeets.Exceptions.AlbumNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.SongNotFound;
import com.example.SanGeets.Exceptions.SongalreadyExistinPlaylist;
import com.example.SanGeets.Model.Album;
import com.example.SanGeets.Model.AlbumsSongIDs;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Songs;
import com.example.SanGeets.Utility.Transformers.SongTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddSongService {
    private final AlbumSongIDDAO  albumSongIDDAO;
//    private final AlbumsSongIDs albumsSongIDs;
    private final ArtistDAO  artistDAO;
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;
    private final SongTransformer songTransformer;





    public String CreateAddsong( String email,AddAlbumSongsRequest addAlbumSongsRequest){
        Artist artist =  artistDAO.findByEmail(email);
        if(artist==null){
            throw new ArtistNotFound("Artist not found");
        }


        Songs songs = songDAO.findById(addAlbumSongsRequest.getSongID())
                .orElseThrow(()-> new SongNotFound("Song not exisit"));

        Album album = albumDAO.findById(addAlbumSongsRequest.getAlbumID())
                .orElseThrow(()->new AlbumNotFound("Album not exisit"));

        boolean albumsSongIDse =albumSongIDDAO.existsBySongs_Id(addAlbumSongsRequest.getSongID());
        if(albumsSongIDse){
            throw new SongalreadyExistinPlaylist("Song already exist in another album");
        }


        AlbumsSongIDs  albumsSongIDs = new AlbumsSongIDs();
        albumsSongIDs.setAlbum(album);
        albumsSongIDs.setArtist(artist);
        albumsSongIDs.setSongs(songs);

        albumSongIDDAO.save(albumsSongIDs);

        return "Album Added Successfully";


    }

    public List<GetAllSonginaAlbum> getsongsinaAlbum(String email){
        Artist artist =  artistDAO.findByEmail(email);
        if(artist==null){
            throw new ArtistNotFound("Artist not found");
        }
        List<GetAllSonginaAlbum> allSonginaAlbums = new ArrayList<>();
        List<Album> album = albumDAO.findByArtistId(artist.getId());
        List<SongResponse> songResponseslist = new ArrayList<>();
        for(Album album1 : album){
            List<AlbumsSongIDs> albumsSongIDs = albumSongIDDAO.findByAlbum_Id(album1.getId());
            GetAllSonginaAlbum getAllSonginaAlbum = new GetAllSonginaAlbum();
            for(AlbumsSongIDs albumsSongID : albumsSongIDs){
                Optional<Songs> song = songDAO.findById(albumsSongID.getSongs().getId());
                Songs songs = song.get();
                SongResponse  songResponse = SongTransformer.SongToSongResponse(songs);
                songResponseslist.add(songResponse);
            }
            getAllSonginaAlbum.setTitle(album1.getTitle());
            getAllSonginaAlbum.setSongResponseList(songResponseslist);
            allSonginaAlbums.add(getAllSonginaAlbum);

        }
        return allSonginaAlbums;
    }





}
