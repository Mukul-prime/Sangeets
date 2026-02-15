package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.PlayListRequest;
import com.example.SanGeets.DTO.Response.PlayListResponse;
import com.example.SanGeets.Model.PlayList;
import com.example.SanGeets.Model.PlayListSongs;

public class PlayListTransformer {

    public static PlayList playlistRequestToPlayList(PlayListRequest playListRequest){
        return PlayList.builder()
//                .user(playListRequest)
                .title(playListRequest.getTitle())
                .build();
    }

    public static PlayListResponse playlistToPlayListResponse(PlayList playList){
        return PlayListResponse.builder()
                .user(playList.getUser())
                .title(playList.getTitle())
                .build();
    }



}
