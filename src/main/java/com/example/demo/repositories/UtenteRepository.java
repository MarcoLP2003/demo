package com.example.demo.repositories;

import com.example.demo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    //serve fare le ricerche per keycloak id (che ancora non so fare)
    List<Utente> findByMail(String mail);

    Utente findById(Long id);

    Utente findByKeycloakId(String keycloakId);

    boolean existsByMail(String mail);
    
    boolean existsById(Long id);
}
