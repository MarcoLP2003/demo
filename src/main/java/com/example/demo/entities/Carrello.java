package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Data

public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User utente;

    @ManyToMany
    @Column
    private Map<Libro, Integer> libriNumero = new HashMap<Libro, Integer>();

    //CONSIDERARE POSSIBILITA DI CREARE ENTITA INTERMEDIA TRA LIBRO E CARRELLO PER SEGNARE LI LA QUANTITA
    //CONSIDERARE SOLUZIONI PROBLEMA COLLANA
}
