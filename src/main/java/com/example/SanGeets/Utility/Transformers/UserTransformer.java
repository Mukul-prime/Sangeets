package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.UserRequest;
import com.example.SanGeets.DTO.Response.UserResponse;
import com.example.SanGeets.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

//@RequiredArgsConstructor
public class UserTransformer {



    public static User userRequestToUser(UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
//                .passwordHash()

                .build();
    }

    public static UserResponse userRequestToUserResponse(User  user){
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
