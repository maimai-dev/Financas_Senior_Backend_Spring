package br.com.fiap.service;

import br.com.fiap.model.Investimento;
import br.com.fiap.model.Usuario;
import br.com.fiap.repository.InvestimentoRepository;
import br.com.fiap.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Investimento> findAll() {
        return investimentoRepository.findAll();
    }

    public List<Investimento> findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("Usuário não encontrado com id: " + usuarioId);
        }
        return investimentoRepository.findByUsuarioId(usuarioId);
    }

    public Investimento findById(Long id) {
        return investimentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Investimento não encontrado com id: " + id));
    }

    public Investimento save(Investimento investimento) {
        validarEAnexarUsuario(investimento);
        return investimentoRepository.save(investimento);
    }

    public Investimento update(Long id, Investimento investimentoAtualizado) {
        Investimento investimento = findById(id);
        validarEAnexarUsuario(investimentoAtualizado);

        investimento.setNome(investimentoAtualizado.getNome());
        investimento.setTipoInvestimento(investimentoAtualizado.getTipoInvestimento());
        investimento.setValorAplicado(investimentoAtualizado.getValorAplicado());
        investimento.setDataAplicacao(investimentoAtualizado.getDataAplicacao());
        investimento.setCorretora(investimentoAtualizado.getCorretora());
        investimento.setUsuario(investimentoAtualizado.getUsuario());

        return investimentoRepository.save(investimento);
    }

    public void delete(Long id) {
        Investimento investimento = findById(id);
        investimentoRepository.delete(investimento);
    }

    private void validarEAnexarUsuario(Investimento investimento) {
        if (investimento.getUsuario() == null || investimento.getUsuario().getId() == null) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório para salvar o investimento.");
        }
        Usuario usuario = usuarioRepository.findById(investimento.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + investimento.getUsuario().getId()));
        investimento.setUsuario(usuario);
    }
}