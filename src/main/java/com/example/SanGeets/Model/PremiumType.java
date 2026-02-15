package com.example.SanGeets.Model;

import com.example.SanGeets.Utility.Enums.Types;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Premiums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Types types;

    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;


    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
