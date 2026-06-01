package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Data

public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false,  unique = true)
    private String mail;

    @Column
    @OneToOne(cascade = CascadeType.ALL)
    private Carrello carrello;

    @Column
    @OneToMany
    private List<Ordine> ordini;

}
