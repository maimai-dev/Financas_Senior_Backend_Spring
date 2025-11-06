package br.com.fiap.controller;

import br.com.fiap.model.Investimento;
import br.com.fiap.service.InvestimentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/investimentos")
@CrossOrigin(origins = "http://localhost:3000")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @GetMapping
    public ResponseEntity<List<Investimento>> listAll() {
        return ResponseEntity.ok(investimentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investimento> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(investimentoService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Investimento>> findByUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(investimentoService.findByUsuarioId(usuarioId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Investimento investimento, UriComponentsBuilder uriBuilder) {
        try {
            Investimento investimentoSalvo = investimentoService.save(investimento);
            URI location = uriBuilder.path("/api/investimentos/{id}").buildAndExpand(investimentoSalvo.getId()).toUri();
            return ResponseEntity.created(location).body(investimentoSalvo);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Investimento investimentoAtualizado) {
        try {
            Investimento investimento = investimentoService.update(id, investimentoAtualizado);
            return ResponseEntity.ok(investimento);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            investimentoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}