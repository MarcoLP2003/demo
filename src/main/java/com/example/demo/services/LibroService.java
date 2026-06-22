package com.example.demo.services;

import com.example.demo.entities.Libro;
import com.example.demo.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Transactional(readOnly = false)
    public Libro addLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("Libro da aggiungere è null");
        }
        String titolo = libro.getTitolo();
        String autore = libro.getAutore();

        if (libroRepository.existsByTitoloAndAutore(titolo, autore)) {
            throw new IllegalArgumentException("Libro da aggiungere esiste gia");
        }

        if (libro.getPrezzo() <= 0) {
            throw new IllegalArgumentException("Libro da aggiungere ha prezzo non valido");
        }

        libro = libroRepository.save(libro);
        return libro;
    }

    @Transactional(readOnly = true)
    public Libro findLibroByIsbn(Long Isbn) {
        Libro l = libroRepository.findByIsbn(Isbn);
        if (l == null) {
            throw new IllegalArgumentException("Libro da trovare è null");
        }
        return l;
    }

    @Transactional(readOnly = true)
    public Libro findLibro(String titolo, String autore) {
        if (titolo == null || autore == null || titolo.isEmpty() || autore.isEmpty()) {
            throw new IllegalArgumentException("Titolo o autore da cercare null/empty");
        }
        if ( !libroRepository.existsByTitoloAndAutore(titolo, autore)) {
            throw new IllegalArgumentException("Libro da trovare non esiste");
        }
        return libroRepository.findByTitoloAndAutore(titolo, autore);
    }

    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }
}
