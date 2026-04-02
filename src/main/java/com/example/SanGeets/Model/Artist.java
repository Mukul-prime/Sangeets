package com.example.SanGeets.Model;


import com.example.SanGeets.Utility.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
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



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    protected LocalDateTime createdDate;

    // One artist → many albums
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Album> albums;



    @OneToMany(mappedBy = "artist" , cascade = CascadeType.ALL)
    private List<ArtistGenres> artistGenresList;

}
