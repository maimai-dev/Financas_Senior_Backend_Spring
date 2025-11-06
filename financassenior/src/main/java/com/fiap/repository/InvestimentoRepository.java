package br.com.fiap.repository;

import br.com.fiap.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {
    List<Investimento> findByUsuarioId(Long usuarioId);
}