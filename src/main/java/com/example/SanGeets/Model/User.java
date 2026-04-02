package com.example.SanGeets.Model;

import com.example.SanGeets.Utility.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
//import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )

    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false)
    private boolean active = true;

    private Date createdAt;

    private Date lastLogin;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name="Country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PremiumType premiumType;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PlayList> playlists;



}
