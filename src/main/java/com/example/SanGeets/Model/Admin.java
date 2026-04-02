package com.example.SanGeets.Model;


import com.example.SanGeets.Utility.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
@ToString()
@Table(name = "Admin")
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String Name;

    @Column(nullable = false,unique = true)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false,unique = true)
    private String phonenumber;

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<State> state;

    @OneToMany(mappedBy = "admin" ,cascade = CascadeType.ALL)
    private List<Country> country;

    @OneToMany(mappedBy = "admin" ,cascade = CascadeType.ALL)
    private List<Language> languages;



}
