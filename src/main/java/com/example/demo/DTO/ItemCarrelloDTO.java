package com.example.demo.DTO;

import com.example.demo.entities.Carrello;
import com.example.demo.entities.Libro;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemCarrelloDTO {


    private Long id;

    private Carrello carrello;

    private Libro libro;

    private int quantitaDaAcquistare;

    private double prezzoAlMomento;
}
