package com.example.SanGeets.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Songs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    private long duration; // in seconds or milliseconds

    /* ================= RELATIONSHIPS ================= */

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "artist_id", nullable = false )
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    /* ================= MEDIA ================= */

    @Lob
    private byte[] image;   // album cover

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] audio;   // song file
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime CratedAt;
}
