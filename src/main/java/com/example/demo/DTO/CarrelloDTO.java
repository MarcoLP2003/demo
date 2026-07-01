package com.example.demo.DTO;

import com.example.demo.entities.ItemCarrello;
import com.example.demo.entities.Utente;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CarrelloDTO {

    private Long id;

    private Utente utente;

    private List<ItemCarrello> items = new ArrayList<>();

    private Long version;
}
