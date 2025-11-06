package br.com.fiap.controller;

import br.com.fiap.model.Categoria;
import br.com.fiap.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listAll() {
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.findById(id);
            return ResponseEntity.ok(categoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria, UriComponentsBuilder uriBuilder) {
        Categoria categoriaSalva = categoriaService.save(categoria);
        URI location = uriBuilder.path("/api/categorias/{id}").buildAndExpand(categoriaSalva.getId()).toUri();
        return ResponseEntity.created(location).body(categoriaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody Categoria categoriaAtualizada) {
        try {
            Categoria categoria = categoriaService.update(id, categoriaAtualizada);
            return ResponseEntity.ok(categoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            categoriaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}