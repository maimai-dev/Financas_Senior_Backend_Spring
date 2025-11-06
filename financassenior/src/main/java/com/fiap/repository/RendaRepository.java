package br.com.fiap.repository;

import br.com.fiap.model.Renda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RendaRepository extends JpaRepository<Renda, Long> {
    List<Renda> findByUsuarioId(Long usuarioId);
}