package br.com.fiap.controller;

import br.com.fiap.model.Gasto;
import br.com.fiap.service.GastoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
@CrossOrigin(origins = "http://localhost:3000")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping
    public ResponseEntity<List<Gasto>> listAll() {
        return ResponseEntity.ok(gastoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gastoService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Gasto>> findByUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(gastoService.findByUsuarioId(usuarioId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Gasto gasto, UriComponentsBuilder uriBuilder) {
        try {
            Gasto gastoSalvo = gastoService.save(gasto);
            URI location = uriBuilder.path("/api/gastos/{id}").buildAndExpand(gastoSalvo.getId()).toUri();
            return ResponseEntity.created(location).body(gastoSalvo);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Gasto gastoAtualizado) {
        try {
            Gasto gasto = gastoService.update(id, gastoAtualizado);
            return ResponseEntity.ok(gasto);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            gastoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}