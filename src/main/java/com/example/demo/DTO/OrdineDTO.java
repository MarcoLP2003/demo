package com.example.demo.DTO;

import com.example.demo.entities.LibroSnapshot;
import com.example.demo.entities.Utente;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrdineDTO {

    private Long id;

    private Utente user;

    private List<LibroSnapshot> libriAcquistati = new ArrayList<>();

    private Long dataAcquisto;

    private double prezzoTotale;
}
