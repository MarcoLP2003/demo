package com.example.demo.repositories;

import com.example.demo.entities.Carrello;
import com.example.demo.entities.ItemCarrello;
import com.example.demo.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCarrelloRepository extends JpaRepository<ItemCarrello, Long> {

    ItemCarrello findByCarrelloAndLibro(Carrello carrello, Libro libro);

    List<ItemCarrello> findByCarrello(Carrello carrello);

    ItemCarrello findByCarrelloAndLibroAndPrezzoAlMomento(Carrello carrello,Libro libro, double prezzo);

    void deleteByCarrelloAndLibro(Carrello carrello, Libro libro);

    boolean existsByCarrelloAndLibro(Carrello carrello, Libro libro);


}
