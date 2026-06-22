package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

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

public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Utente utente;

    @ManyToOne
    @Column
    private List<ItemCarrello> items = new ArrayList<>();

    @Version
    private Long version;
}
