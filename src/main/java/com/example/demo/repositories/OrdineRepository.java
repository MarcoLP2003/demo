package com.example.demo.repositories;

import com.example.demo.entities.Ordine;
import com.example.demo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {

    List<Ordine> findByUtenteDataDecrescente(Utente utente);
}
