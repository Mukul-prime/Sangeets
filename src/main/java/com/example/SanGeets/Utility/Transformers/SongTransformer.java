package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.SongRequest;
import com.example.SanGeets.DTO.Response.SongResponse;
import com.example.SanGeets.Model.Songs;

public class SongTransformer {
    public static Songs SongResquestToSong(SongRequest songRequest){
        return Songs.builder()
                .title(songRequest.getTitle())
                .duration(songRequest.getDuration())
                .build();
    }
    public static SongResponse SongToSongResponse(Songs songs){
        return SongResponse.builder()
                .title(songs.getTitle())
                .duration(songs.getDuration())
                .ArtistName(songs.getArtist().getArtistName())
                .GenreName(songs.getGenre().getName())
                .audioUrl("/api/v1/song/audio/"+songs.getId())
                .bannerUrl("/api/v1/song/banner/"+songs.getId())
                .build();
    }
}
