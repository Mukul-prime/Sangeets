package com.example.SanGeets.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column(nullable = false ,unique = true)
    private String countryname;

    @OneToMany(mappedBy = "country",cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<State> states;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminid")
    private Admin admin;

}
