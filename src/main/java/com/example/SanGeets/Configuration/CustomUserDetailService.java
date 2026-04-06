package com.example.SanGeets.Configuration;

import com.example.SanGeets.DAO.AdminDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.UserDAO;
import com.example.SanGeets.Model.Admin;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserDAO userDAO;
    private final ArtistDAO artistDAO;

    @Autowired public AdminDAO adminDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username);
        if (user != null) {
            return buildUserDetails(user.getEmail(), user.getPasswordHash(), user.getRole().name());
        }

        Artist artist = artistDAO.findByEmail(username);
        if (artist != null) {
            return buildUserDetails(artist.getEmail(), artist.getPasswordHash(), artist.getRole().name());
        }


        Admin admin = adminDAO.findByEmail(username);
        if (admin != null) {
            return buildUserDetails(admin.getEmail(), admin.getPassword(), admin.getRole().name());
        }
        throw new UsernameNotFoundException("User not found with email: " + username);
    }

    private UserDetails buildUserDetails(String email, String passwordHash, String role) {
        return new org.springframework.security.core.userdetails.User(
                email,
                passwordHash,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}

