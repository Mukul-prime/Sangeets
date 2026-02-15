package com.example.SanGeets.Model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String artistName;


    // FK from users table
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(length = 500)
    private String bio;

    @Lob
    private byte[] coverImage;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // One artist → many albums
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Album> albums;



    @OneToMany(mappedBy = "artist" , cascade = CascadeType.ALL)
    private List<ArtistGenres> artistGenresList;

}
