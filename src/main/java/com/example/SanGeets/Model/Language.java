package com.example.SanGeets.Model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Language")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String languageCode;

    @Column(unique = true,nullable = false)
    private String languageName;


}
