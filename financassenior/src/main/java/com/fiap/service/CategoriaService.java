package br.com.fiap.service;

import br.com.fiap.model.Categoria;
import br.com.fiap.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = findById(id);
        categoria.setNome(categoriaAtualizada.getNome());
        return categoriaRepository.save(categoria);
    }

    public void delete(Long id) {
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
    }
}