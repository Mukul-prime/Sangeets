package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.UserRequest;
import com.example.SanGeets.DTO.Response.UserResponse;
import com.example.SanGeets.Model.User;

public class UserTransformer {

    public static User userRequestToUser(UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .passwordHash(userRequest.getPassword())

                .build();
    }

    public static UserResponse userRequestToUserResponse(User  user){
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
