package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.CountryDAO;
import com.example.SanGeets.DTO.Request.ArtistRequest;
import com.example.SanGeets.DTO.Response.ArtistReponse;
import com.example.SanGeets.Exceptions.ArtistAlreadyin;
import com.example.SanGeets.Exceptions.CountryNotFounded;
import com.example.SanGeets.Exceptions.ImageNotInserted;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Country;
import com.example.SanGeets.Utility.Transformers.ArtistTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistDAO artistDAO;
    private final CountryDAO countryDAO;


    public ArtistReponse createArtist(ArtistRequest artistRequest) throws IOException {
        Country country = countryDAO.findBycountryname(String.valueOf(artistRequest.getCountry()));
        if (country != null) {
            throw new CountryNotFounded("Country Not Founded");
        }
        Artist artist = artistDAO.findByEmail(artistRequest.getEmail());
        if (artist != null) {
            throw new ArtistAlreadyin("Artist Already Exists");
        }

        Artist artist1 = ArtistTransformer.artistRequestToArtist(artistRequest);
        if (artistRequest.getCoverImage() != null && !artistRequest.getCoverImage().isEmpty()) {
            try {
                artist1.setCoverImage(artistRequest.getCoverImage().getBytes());
            } catch (ImageNotInserted e) {
                throw new ImageNotInserted("Image Not Inserted");
            }

        }

        Artist artist2 = artistDAO.save(artist1);

        return ArtistTransformer.artistToartistResponse(artist2);

    }




}
