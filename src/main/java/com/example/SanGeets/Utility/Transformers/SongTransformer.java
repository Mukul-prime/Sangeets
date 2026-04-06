package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.SongRequest;
import com.example.SanGeets.DTO.Response.SongResponse;
import com.example.SanGeets.Model.Songs;
import org.springframework.beans.factory.annotation.Autowired;

public class SongTransformer {
    public static String convertDurationStrings(Long duration){
        String ans = null;
        long hours = duration/3600;
        long minutes = (duration%3600)/60;
        long seconds = (duration%60);
        if (hours > 0) {
            return hours + "Hours:" + minutes + "Minutes:" + seconds + "Seconds";
        }
        else if (minutes > 0) {
            return minutes + "Minutes:" + seconds + "Seconds";
        }
        else {
            return seconds + "Seconds";
        }

    }



    public static Songs SongResquestToSong(SongRequest songRequest){
        return Songs.builder()
                .title(songRequest.getTitle())
//                .duration(songRequest.getDuration())
                .build();
    }
    public static SongResponse SongToSongResponse(Songs songs){


        String ans = convertDurationStrings(songs.getDuration());
        return SongResponse.builder()
                .title(songs.getTitle())
                .duration(ans)
                .type(songs.getType())
                .ArtistName(songs.getArtist().getArtistName())
                .GenreName(songs.getGenre().getName())
                .audioUrl("/api/v1/song/audio/"+songs.getId())
                .bannerUrl("/api/v1/song/banner/"+songs.getId())
                .build();
    }



}
