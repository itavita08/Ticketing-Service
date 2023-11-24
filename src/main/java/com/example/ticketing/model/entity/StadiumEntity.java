package com.example.ticketing.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stadium")
public class StadiumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stadium_name")
    private String stadiumName;

    @Column(name = "address")
    private String address;

    @Column(name = "capacity")
    private Integer capacity;


}
