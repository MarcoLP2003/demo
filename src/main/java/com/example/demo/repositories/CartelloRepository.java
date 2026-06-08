package com.example.demo.repositories;

import com.example.demo.entities.Carrello;
import com.example.demo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartelloRepository extends JpaRepository<Carrello, Long> {

    boolean existsByUtente(Utente utente);

    Carrello findByUtente(Utente utente);
}
