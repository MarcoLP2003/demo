package com.example.demo.repositories;

import com.example.demo.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findByIsbn(int isbn);

    Libro findByTitolo(String titolo);

    List<Libro> findByAutore(String autore);

    List<Libro> findByPrezzo(int prezzo);

}
