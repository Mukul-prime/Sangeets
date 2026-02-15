package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.*;
import com.example.SanGeets.DTO.Request.PlayListUpdate;
import com.example.SanGeets.DTO.Request.UserEmailChange;
import com.example.SanGeets.DTO.Request.UserRequest;
import com.example.SanGeets.DTO.Response.UserResponse;
import com.example.SanGeets.Exceptions.*;
import com.example.SanGeets.Model.*;
import com.example.SanGeets.Utility.Enums.Role;
import com.example.SanGeets.Utility.Transformers.UserTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final ArtistDAO artistDAO;
    private final CountryDAO countryDAO;
    private final LanguageDAO languageDAO;
    private final StateDAO stateDAO;
    private final SongDAO songDAO;
    private final PlayListSongsDAO playListSongsDAO;
    private final PlayListDAO playListDAO;


    public UserResponse createUser(UserRequest userRequest) {
        User user = userDAO.findByEmail(userRequest.getEmail());
        log.info(String.valueOf(userRequest));
        if (user != null) {
            throw new UserAlreadyPresent("User with email " + userRequest.getEmail() + " already exists");
        }

        Artist artist = artistDAO.findByEmail(userRequest.getEmail());
        if (artist != null) {
            throw new AlreadyUserin("Already have an another user " + userRequest.getEmail());
        }

        Country country = countryDAO.findById(userRequest.getCountryId())
                .orElseThrow(() ->
                        new CountryNotFounded("Country with id " + userRequest.getCountryId()));

        State state = stateDAO.findById(userRequest.getStateId()).orElseThrow(() ->
                new StateNotFounded("State with id " + userRequest.getStateId()));

        Language language = languageDAO.findById(Math.toIntExact(userRequest.getLanguageId()))
                .orElseThrow(() -> new LanguageNotfound("language not founded "));

        User newUser = UserTransformer.userRequestToUser(userRequest);
        newUser.setRole(Role.USER);
        newUser.setCountry(country);
        newUser.setState(state);
        newUser.setLanguage(language);
        User a = userDAO.save(newUser);
        return UserTransformer.userRequestToUserResponse(a);


    }

    public String changeEmail(String email) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotfound("User not created ");

        }
        user.setEmail(email);
        userDAO.save(user);
        return "Updated emails";

    }


    public String changePassword(UserEmailChange userEmailChange) {
        User user = userDAO.findByEmail(userEmailChange.getEmail());
        if (user == null) {
            throw new UserNotfound("User not created ");
        }

        user.setPasswordHash(userEmailChange.getPassword());
        userDAO.save(user);
        return "Updated passwords";
    }


    public String removeSongFromplayList(PlayListUpdate playListUpdate) {

        User user = userDAO.findByEmail(playListUpdate.getEmail());
        if (user == null) {
            throw new UserNotfound("User not created");
        }

        PlayListSongs playListSongs =
                playListSongsDAO
                        .findByPlayList_PlaylistIdAndSong_Id(
                                playListUpdate.getPlaylistId(),
                                playListUpdate.getSong_Id()
                        )
                        .orElseThrow(() ->
                                new SondDeleteAlready("Song already deleted"));

        playListSongsDAO.delete(playListSongs);

        return "Removed";
    }




}
