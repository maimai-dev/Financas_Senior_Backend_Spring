package br.com.fiap.service;

import br.com.fiap.model.Categoria;
import br.com.fiap.model.Gasto;
import br.com.fiap.model.Usuario;
import br.com.fiap.repository.CategoriaRepository;
import br.com.fiap.repository.GastoRepository;
import br.com.fiap.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Gasto> findAll() {
        return gastoRepository.findAll();
    }

    public List<Gasto> findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("Usuário não encontrado com id: " + usuarioId);
        }
        return gastoRepository.findByUsuarioId(usuarioId);
    }

    public Gasto findById(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gasto não encontrado com id: " + id));
    }

    public Gasto save(Gasto gasto) {
        validarEAnexarEntidades(gasto);
        return gastoRepository.save(gasto);
    }

    public Gasto update(Long id, Gasto gastoAtualizado) {
        Gasto gasto = findById(id);

        validarEAnexarEntidades(gastoAtualizado);

        gasto.setValor(gastoAtualizado.getValor());
        gasto.setData(gastoAtualizado.getData());
        gasto.setDescricao(gastoAtualizado.getDescricao());
        gasto.setCategoria(gastoAtualizado.getCategoria());
        gasto.setUsuario(gastoAtualizado.getUsuario());

        return gastoRepository.save(gasto);
    }

    public void delete(Long id) {
        Gasto gasto = findById(id);
        gastoRepository.delete(gasto);
    }

    private void validarEAnexarEntidades(Gasto gasto) {
        if (gasto.getUsuario() == null || gasto.getUsuario().getId() == null) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório para salvar o gasto.");
        }
        if (gasto.getCategoria() == null || gasto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("O ID da categoria é obrigatório para salvar o gasto.");
        }

        Usuario usuario = usuarioRepository.findById(gasto.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + gasto.getUsuario().getId()));

        Categoria categoria = categoriaRepository.findById(gasto.getCategoria().getId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + gasto.getCategoria().getId()));

        gasto.setUsuario(usuario);
        gasto.setCategoria(categoria);
    }
}