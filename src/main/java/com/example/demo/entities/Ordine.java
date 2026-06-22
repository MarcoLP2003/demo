package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Data

public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Utente user;

    @ManyToMany
    @Column
    private List<LibroSnapshot> libriAcquistati = new ArrayList<>();

    @Column(nullable = false)
    private Long dataAcquisto;

    @Column
    private double prezzoTotale;
}
