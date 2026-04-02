package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.CountryDAO;
import com.example.SanGeets.DTO.Request.*;
import com.example.SanGeets.DTO.Response.ArtistReponse;
import com.example.SanGeets.Exceptions.ArtistAlreadyin;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.CountryNotFounded;
import com.example.SanGeets.Exceptions.ImageNotInserted;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.Country;
import com.example.SanGeets.Utility.Enums.Role;
import com.example.SanGeets.Utility.Transformers.ArtistTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistDAO artistDAO;
    private final CountryDAO countryDAO;
    private final PasswordEncoder passwordEncoder;


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
        artist1.setPasswordHash(passwordEncoder.encode(artistRequest.getPassword()));
        artist1.setRole(Role.ARTIST);

        Artist artist2 = artistDAO.save(artist1);

        return ArtistTransformer.artistToartistResponse(artist2);

    }


    public String changeEmail(String email, ChangeEmail changeEmail) {
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) {
            throw new ArtistNotFound("Artist Not exisit");
        }
        artist.setEmail(changeEmail.getEmail());
        artistDAO.save(artist);
        return "Updated emails";
    }


    public String changepassword(String email , Changepassword changepassword) {
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) {
            throw new ArtistNotFound("Artist Not Exists");
        }
        artist.setPasswordHash(passwordEncoder.encode(changepassword.getNewPassword()));
        artistDAO.save(artist);
        return "Updated passwords";

    }


    public String changebio(String email , ChangeBio changeBio) {
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) {
            throw new ArtistNotFound("Artist Not Exists");
        }
        artist.setBio(changeBio.getBio());
        artistDAO.save(artist);
        return "Updated bio";
    }


    public String changeimage(String email , ChangeBackgroundImage changeBackgroundImage) throws IOException {
        Artist artist = artistDAO.findByEmail(email);
        if (artist == null) {
            throw new ArtistNotFound("Artist Not Exists");
        }

        if (changeBackgroundImage.getCoverImage() != null && !changeBackgroundImage.getCoverImage().isEmpty()) {
            try {
                artist.setCoverImage(changeBackgroundImage.getCoverImage().getBytes());
            } catch (ImageNotInserted e) {
                throw new ImageNotInserted("Image Not Inserted");
            }

        }
        return "Updated image";

    }







}
