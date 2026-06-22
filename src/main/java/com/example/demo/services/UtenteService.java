package com.example.demo.services;

import com.example.demo.entities.Utente;
import com.example.demo.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente findByKeycloakId(String keycloakId) {
        return utenteRepository.findByKeycloakId(keycloakId);
    }

    //PROVISION KEYCLOAK USER ????????
    @Transactional
    public Utente provisionKeycloakUser(String keycloakId, String email) {
        Utente existing = utenteRepository.findByKeycloakId(keycloakId);
        if ( existing != null ) {
            return existing;
        }
        Utente u = new Utente();
        u.setKeycloakId(keycloakId);
        u.setMail(email);
        // qui non serve password perché Keycloak gestisce il login
        return utenteRepository.save(u);
    }

    @Transactional(readOnly = true)
    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Utente> showAllUsers(int pageNumber, int pageSize, String sortBy) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Utente> pagedResult = utenteRepository.findAll(page);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
    }


    @Transactional(readOnly = true)
    public List<Utente> findByMail(String mail) {
        return utenteRepository.findByMail(mail);
    }

    @Transactional(readOnly = true)
    public Utente findById(Long id) {
        return utenteRepository.findById(id);
    }

    public boolean existById(Long userId) {
        return utenteRepository.existsById(userId);
    }

    //serve un file particolare inserito nel progetto in una cartella "auth", chiamato KeycloakAdminClient

    public String loginUser(String mail, String password) throws Exception {
        ResponseEntity<String> response = keycloakAdminClient.login(mail, password);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Login fallito");
        }
    }
}
