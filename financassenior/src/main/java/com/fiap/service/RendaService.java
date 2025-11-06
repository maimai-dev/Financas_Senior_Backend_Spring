package br.com.fiap.service;

import br.com.fiap.model.Renda;
import br.com.fiap.model.Usuario;
import br.com.fiap.repository.RendaRepository;
import br.com.fiap.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendaService {

    @Autowired
    private RendaRepository rendaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Renda> findAll() {
        return rendaRepository.findAll();
    }

    public List<Renda> findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("Usuário não encontrado com id: " + usuarioId);
        }
        return rendaRepository.findByUsuarioId(usuarioId);
    }

    public Renda findById(Long id) {
        return rendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Renda não encontrada com id: " + id));
    }

    public Renda save(Renda renda) {
        validarEAnexarUsuario(renda);
        return rendaRepository.save(renda);
    }

    public Renda update(Long id, Renda rendaAtualizada) {
        Renda renda = findById(id);
        validarEAnexarUsuario(rendaAtualizada);

        renda.setValor(rendaAtualizada.getValor());
        renda.setData(rendaAtualizada.getData());
        renda.setDescricao(rendaAtualizada.getDescricao());
        renda.setUsuario(rendaAtualizada.getUsuario());

        return rendaRepository.save(renda);
    }

    public void delete(Long id) {
        Renda renda = findById(id);
        rendaRepository.delete(renda);
    }

    private void validarEAnexarUsuario(Renda renda) {
        if (renda.getUsuario() == null || renda.getUsuario().getId() == null) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório para salvar a renda.");
        }
        Usuario usuario = usuarioRepository.findById(renda.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + renda.getUsuario().getId()));
        renda.setUsuario(usuario);
    }
}