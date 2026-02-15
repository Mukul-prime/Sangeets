package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.GenreRequest;
import com.example.SanGeets.DTO.Response.GenreResponse;
import com.example.SanGeets.Model.Genre;

public class GenreTransformer {
    public static Genre genreRequestToGenre(GenreRequest genreRequest) {
        return Genre.builder().name(genreRequest.getName()).build();
    }

    public static GenreResponse genreToGenreResponse(Genre genre) {
        return GenreResponse.builder().genreName(genre.getName()).build();
    }
}
