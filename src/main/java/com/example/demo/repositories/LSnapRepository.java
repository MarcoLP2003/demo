package com.example.demo.repositories;

import com.example.demo.entities.Libro;
import com.example.demo.entities.LibroSnapshot;
import com.example.demo.entities.Ordine;
import com.example.demo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LSnapRepository extends JpaRepository<LibroRepository, Long> {

    List<LibroSnapshot> findByOrdine(Ordine ordine);

    List<LibroSnapshot> findByPrezzoAcquisto(double prezzoAcquisto );

    List<LibroSnapshot> findByQuantitaAcquisto(double quantitaAcquisto );
}
