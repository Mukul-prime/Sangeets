package com.example.SanGeets.Model;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;
//import org.hibernate.annotations.Table;

@Entity
@Table(name = "artist_genres")
public class ArtistGenres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "genre_id",nullable = false)
    private Genre genre;

    @Column(nullable = false , name = "added_at")
    private Date AddedAt;


}
