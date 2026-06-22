package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;
import java.util.List;

@Service
public class CarrelloService {
    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private ItemCarrelloRepository itemCarrelloRepository;

    //PER PROBLEMA COLLANA
    @PersistenceContext
    private EntityManager em;

    public Carrello creaCarrello(Long idUtente){
        Utente utente = utenteRepository.findById(idUtente);
        if( utente == null){
            throw new EntityNotFoundException("Utente per cui creare il carrello è null");
        }
        Carrello carrello = carrelloRepository.findByUtente(utente);
        if( carrello == null){
            carrello = new Carrello();
            carrello.setUtente(utente);
            carrello = carrelloRepository.save(carrello);
        }
        return carrello;
    }

    @Transactional
    public ItemCarrello aggiungiElemento(long idUtente, long isbn, int quantita) throws Exception {
        if(quantita <= 0){
            throw new IllegalArgumentException("Quantita elemento da aggiungere al carrello non valida");
        }
        Carrello carrello = creaCarrello(idUtente);
        Libro libro = em.find(Libro.class, isbn, LockModeType.OPTIMISTIC);
        if (libro == null){
            throw new EntityNotFoundException("Il libro da aggiungere al carrello è null");
        }
        if( libro.getQuantitaNegozio() < quantita){
            throw new Exception("Scorte non sufficienti");
        }
        double prezzo = libro.getPrezzo();
        try {
            libro.setQuantitaNegozio(libro.getQuantitaNegozio() - quantita);
            libroRepository.save(libro);//questo serve per il campo versione che non ho ancora fatto
        }catch(OptimisticLockingFailureException e){
            throw new ConcurrentModificationException("Modifiche concorrenti alla quantita disponibile in negozio");
        }
        ItemCarrello itemCarrello = itemCarrelloRepository.findByCarrelloAndLibroAndPrezzoAlMomento(carrello, libro, prezzo);
        if( itemCarrello != null){
            itemCarrello.setQuantitaDaAcquistare(itemCarrello.getQuantitaDaAcquistare() + quantita);
        } else{
            itemCarrello = new ItemCarrello();
            itemCarrello.setCarrello(carrello);
            itemCarrello.setLibro(libro);
            itemCarrello.setQuantitaDaAcquistare(quantita);
            itemCarrello.setPrezzoAlMomento(prezzo);
            carrello.getItems().add(itemCarrello);
        }
        ItemCarrello salvato = itemCarrelloRepository.save(itemCarrello);
        em.lock(carrello, LockModeType.OPTIMISTIC_FORCE_INCREMENT);//questo serve per il campo versione che non ho ancora fatto
        return salvato;
    }

    @Transactional
    public void rimuoviElemento(long idUtente, long isbn) throws Exception {
        Carrello carrello = creaCarrello(idUtente);
        Libro libro = em.find(Libro.class, isbn, LockModeType.OPTIMISTIC);
        if (libro == null){
            throw new EntityNotFoundException("Libro da rimuovere dal carrello è null");
        }
        ItemCarrello item = itemCarrelloRepository.findByCarrelloAndLibro(carrello, libro);
        if( item == null){
            throw new EntityNotFoundException("Libro da rimuovere dal carrello non è associato ad esso tramite ItemCarrello");
        }
        libro.setQuantitaNegozio(libro.getQuantitaNegozio() + item.getQuantitaDaAcquistare());
        libroRepository.save(libro);//questo serve per il campo versione che non ho ancora fatto
        itemCarrelloRepository.delete(item);
        em.lock(carrello, LockModeType.OPTIMISTIC_FORCE_INCREMENT); //questo serve per il campo versione che non ho ancora fatto
    }

    @Transactional
    public void svuotaCarrello (long idUtente) throws Exception {
        Carrello carrello = creaCarrello(idUtente);
        List<ItemCarrello> listaItems = itemCarrelloRepository.findByCarrello(carrello);
        for(ItemCarrello item : listaItems){
            Libro libro = item.getLibro();
            libro.setQuantitaNegozio(libro.getQuantitaNegozio() + item.getQuantitaDaAcquistare());
            libroRepository.save(libro);
            itemCarrelloRepository.delete(item);
        }
        em.lock(carrello, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }


    @Transactional
    public void checkout (long idUtente, long clientVersion){
        Carrello carrello = creaCarrello(idUtente);
        if(! carrello.getVersion().equals(clientVersion)){
            throw new IllegalStateException("Le versioni al momento del checkout non corrispondono");
        }
        List<ItemCarrello> listaItems = itemCarrelloRepository.findByCarrello(carrello);
        if(listaItems.isEmpty()){
            throw new IllegalArgumentException("Il carrello al momento di checkout è vuoto");
        }
        //controlli fatti, avvio dell'ordine
        Utente utente = carrello.getUtente();
        Ordine ordine = new Ordine();
        ordine.setUser(utente);
        double tot = 0;
        for(ItemCarrello item : listaItems){
            Libro libro = em.find(Libro.class, item.getLibro().getIsbn(), LockModeType.OPTIMISTIC);
            if(libro.getQuantitaNegozio() < item.getQuantitaDaAcquistare()){
                throw new IllegalArgumentException("Quantita da acquistare al momento di checkout non disponibile");
            }
            if (libro.getPrezzo() != item.getPrezzoAlMomento()){
                item.setPrezzoAlMomento(libro.getPrezzo());
                itemCarrelloRepository.save(item);
            }
            LibroSnapshot ls = new LibroSnapshot();
            item.setPrezzoAlMomento(libro.getPrezzo());

            ls.setOrdine(ordine);
            ls.setAutore(libro.getAutore());
            ls.setIsbn(libro.getIsbn());
            ls.setTitolo(libro.getTitolo());
            ls.setItem(item);
            ls.setPrezzoAquisto(item.getPrezzoAlMomento());
            ls.setQuantitaAquisto(item.getQuantitaDaAcquistare());

            ordine.getLibriAcquistati().add(ls);
            tot += item.getPrezzoAlMomento()*item.getQuantitaDaAcquistare();
        }

        ordine.setPrezzoTotale(tot);
        ordineRepository.save(ordine);
        itemCarrelloRepository.deleteAll(listaItems);
        em.lock(carrello, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

}
