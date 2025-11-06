package br.com.fiap.controller;

import br.com.fiap.model.Renda;
import br.com.fiap.service.RendaService;
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
@RequestMapping("/api/rendas")
@CrossOrigin(origins = "http://localhost:3000")
public class RendaController {

    @Autowired
    private RendaService rendaService;

    @GetMapping
    public ResponseEntity<List<Renda>> listAll() {
        return ResponseEntity.ok(rendaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Renda> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(rendaService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Renda>> findByUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(rendaService.findByUsuarioId(usuarioId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Renda renda, UriComponentsBuilder uriBuilder) {
        try {
            Renda rendaSalva = rendaService.save(renda);
            URI location = uriBuilder.path("/api/rendas/{id}").buildAndExpand(rendaSalva.getId()).toUri();
            return ResponseEntity.created(location).body(rendaSalva);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Renda rendaAtualizada) {
        try {
            Renda renda = rendaService.update(id, rendaAtualizada);
            return ResponseEntity.ok(renda);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            rendaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}