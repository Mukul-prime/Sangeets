package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.GenreDAO;
import com.example.SanGeets.DTO.Request.GenreRequest;
import com.example.SanGeets.DTO.Response.GenreResponse;
import com.example.SanGeets.Exceptions.GenreAlreadyExisit;
import com.example.SanGeets.Model.Genre;
import com.example.SanGeets.Utility.Transformers.GenreTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {

    private final GenreDAO genreDAO;

    public GenreResponse createGenre(GenreRequest genreRequest) {
        Genre genre = genreDAO.findByName(genreRequest.getName());
        if(genre!= null){
            throw new GenreAlreadyExisit("Genre already exists");
        }

        Genre genre1 = genreDAO.save(GenreTransformer.genreRequestToGenre(genreRequest));
        return  GenreTransformer.genreToGenreResponse(genre1);
    }


    public List<GenreResponse> getallGenre(){
        List<Genre> genres = genreDAO.findAll();
        List<GenreResponse> genreResponses = new ArrayList<>();
        for (Genre genre : genres) {
            genreResponses.add(GenreTransformer.genreToGenreResponse(genre));
        }
        return genreResponses;
    }
}
