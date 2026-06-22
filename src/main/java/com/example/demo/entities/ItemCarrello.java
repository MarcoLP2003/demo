package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Data

public class ItemCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Carrello carrello;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "isbn")
    private Libro libro;

    @Column(nullable = false)
    private int quantitaDaAcquistare;

    @Column(nullable = false)
    private double prezzoAlMomento;


}
