package com.example.demo.controllers;


import com.example.demo.DTO.OrdineDTO;
import com.example.demo.entities.Ordine;
import com.example.demo.repositories.OrdineRepository;
import com.example.demo.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ordini")
@CrossOrigin(origins = "*")
public class OrdineController {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public ResponseEntity<List<OrdineDTO>> getOrdiniUtente() {
        //fare keycloak
    }
}
