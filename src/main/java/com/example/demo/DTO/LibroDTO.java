package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroDTO {
    private Long isbn;

    private String titolo;

    private String autore;

    private Long quantitaNegozio;

    private double prezzo;
}
