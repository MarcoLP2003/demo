package com.example.demo.controllers;

import com.example.demo.entities.Libro;
import com.example.demo.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Libri")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseEntity createLibro(@RequestBody Libro libro) {
        try{
            System.out.println("Creando Libro");
            System.out.println(libro);

            Libro saved = libroService.addLibro(libro);
            return ResponseEntity.ok(saved);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by_isbn")
    public ResponseEntity getByIsbn(@RequestParam Long isbn){
        try{
            System.out.println("Cercando per isbn: " + isbn);
            Libro libro = libroService.findLibroByIsbn(isbn);
            return ResponseEntity.ok(libro);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Libro>> getAllLibri(){
        System.out.println("Prendo tutti i libri");
        List<Libro> libri = libroService.findAll();
        if(libri.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libri);
    }

    @GetMapping("/by_titoloAndAutore")
    public ResponseEntity getByTitoloAndAutore(@RequestParam String titolo, String autore){
        try {
            System.out.println("cerco per autore e titolo");
            Libro libro = libroService.findLibro(titolo, autore);
            return ResponseEntity.ok(libro);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
