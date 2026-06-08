package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Data

public class LibroSnapshot {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Libro libro;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Ordine ordine;
    
    @Id
    private Long isbn;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String autore;

    @Column(nullable = false)
    private double prezzoAquisto;

    @Column(nullable = false)
    private double quantitaAquisto;
}
