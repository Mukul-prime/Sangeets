package com.example.SanGeets.Service;



import com.example.SanGeets.DAO.AdminDAO;
import com.example.SanGeets.DAO.ArtistDAO;
import com.example.SanGeets.DAO.UserDAO;
import com.example.SanGeets.Configuration.JwtUtilitys;
import com.example.SanGeets.Exceptions.AdminNotFound;
import com.example.SanGeets.Exceptions.ArtistNotFound;
import com.example.SanGeets.Exceptions.UserNotfound;
import com.example.SanGeets.DTO.Request.Loginrequest;
import com.example.SanGeets.DTO.Response.LoginResponse;
import com.example.SanGeets.Model.Admin;
import com.example.SanGeets.Model.Artist;
import com.example.SanGeets.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserDAO userDAO;
    private final AuthenticationManager authenticationManager;
    private final ArtistDAO artistDAO;
    private final AdminDAO adminDAO;
   @Autowired
   public JwtUtilitys jwtUtilitys;


    public LoginResponse login(Loginrequest loginrequest){


        User user = userDAO.findByEmail(loginrequest.getEmail());
        if(user == null){
            throw new UserNotfound("User not found");
        }
        Authentication authenticationManager1 = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginrequest.getEmail() , loginrequest.getPassword())
        );

        String key = jwtUtilitys.generateToken(String.valueOf(user.getRole()), user.getEmail());

        return LoginResponse.builder().token(key).email(user.getEmail()).role(String.valueOf(user.getRole())).build();
    }

    public LoginResponse loginartist(Loginrequest loginrequest){
        Artist artist =  artistDAO.findByEmail(loginrequest.getEmail());
        if(artist == null){
            throw new ArtistNotFound("Artist not found");
        }
        System.out.println("Login Artist");
        Authentication authenticationManager1 = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginrequest.getEmail() , loginrequest.getPassword())
        );
        String key = jwtUtilitys.generateToken(String.valueOf(artist.getRole()), artist.getEmail());

        return LoginResponse.builder().token(key).email(artist.getEmail()).role(String.valueOf(artist.getRole())).build();

    }



    public LoginResponse LoginAdmin(Loginrequest loginrequest){
        Admin admin = adminDAO.findByEmail(loginrequest.getEmail());
        if(admin == null){
            throw new AdminNotFound("Admin not found");
        }

        Authentication authenticationManager1 = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginrequest.getEmail() , loginrequest.getPassword())
        );
        String key = jwtUtilitys.generateToken(String.valueOf(admin.getRole()), admin.getEmail());
        return LoginResponse.builder().token(key).email(admin.getEmail()).role(String.valueOf(admin.getRole())).build();
    }
}
