package com.example.SanGeets.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "State")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stateId;

    @Column(unique = true , nullable = false)
    private String stateCode;

    @Column(unique = true , nullable = false)
    private String stateName ;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
//    @JsonBackReference
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="adminid")
    private Admin admin;



}
