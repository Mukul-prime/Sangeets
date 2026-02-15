package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.AlbumRequest;
import com.example.SanGeets.Model.Album;

public class AlbumTransformer {
    public static Album albumRequestToAlbum(AlbumRequest albumRequest){
        return Album.builder().title(albumRequest.getTitle()).build();
    }
}
