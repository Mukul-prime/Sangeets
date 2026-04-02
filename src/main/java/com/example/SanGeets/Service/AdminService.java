package com.example.SanGeets.Service;

import com.example.SanGeets.DAO.AdminDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.UserDAO;
import com.example.SanGeets.DTO.Request.AdminRequestCreate;
import com.example.SanGeets.Exceptions.AdminAlreadyCreated;
import com.example.SanGeets.Exceptions.ArtistAlreadyin;
import com.example.SanGeets.Exceptions.UserAlreadyPresent;
import com.example.SanGeets.Model.Admin;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.User;
import com.example.SanGeets.Utility.Enums.Role;
import com.example.SanGeets.Utility.Transformers.AdminTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDAO adminDAO;
    private final ArtistDAO artistDAO;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public String Admincreate(AdminRequestCreate adminRequestCreate){
        if (!StringUtils.hasText(adminRequestCreate.getPassword())) {
            throw new IllegalArgumentException("Password is required");
        }

        Admin admin =adminDAO.findByEmail(adminRequestCreate.getEmail());
        if(admin!=null){
            throw new AdminAlreadyCreated("Admin already exists");

        }
        Artist artist = artistDAO.findByEmail(adminRequestCreate.getEmail());
        if(artist!=null){
            throw new ArtistAlreadyin("Artist already exists");
        }

        User user = userDAO.findByEmail(adminRequestCreate.getEmail());
        if(user!=null){
            throw new UserAlreadyPresent("User already exists");
        }

        Admin admin1 = AdminTransformer.AdminRequestToAdmin(adminRequestCreate);
        admin1.setRole(Role.ADMIN);
        admin1.setPassword(passwordEncoder.encode(adminRequestCreate.getPassword()));
        adminDAO.save(admin1);
        return "Admin created successfully";

    }
}
