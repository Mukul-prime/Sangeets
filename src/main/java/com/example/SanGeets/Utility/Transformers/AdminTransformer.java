package com.example.SanGeets.Utility.Transformers;

import com.example.SanGeets.DTO.Request.AdminRequestCreate;
import com.example.SanGeets.Model.Admin;

public class AdminTransformer {
    public static Admin AdminRequestToAdmin(AdminRequestCreate adminRequestCreate){
        {

            return Admin.builder()

                    .email(adminRequestCreate.getEmail())
                    .Name(adminRequestCreate.getUsername())
                    .phonenumber(adminRequestCreate.getPhonenumber())
                    .build();

        }
    }
}
