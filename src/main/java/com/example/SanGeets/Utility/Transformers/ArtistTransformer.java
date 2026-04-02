package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.ArtistRequest;
import com.example.SanGeets.DTO.Response.ArtistReponse;
import com.example.SanGeets.Model.Artist;

import java.io.IOException;

public class ArtistTransformer {
    public static Artist artistRequestToArtist(ArtistRequest artistRequest)  {
        return Artist.builder()
                .artistName(artistRequest.getArtistName())
                .email(artistRequest.getEmail())

                .bio(artistRequest.getBio())
                .country(artistRequest.getCountry())
//                .coverImage(artistRequest.getCoverImage().getBytes())
                .build();
    }

    public static ArtistReponse artistToartistResponse(Artist artist){
        return ArtistReponse.builder()
                .name(artist.getArtistName())
                .email(artist.getEmail())
                .BIO(artist.getBio())
                .build();
    }
}
