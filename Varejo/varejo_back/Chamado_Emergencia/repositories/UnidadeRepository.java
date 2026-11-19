package Chamado_Emergencia.repositories;

import Chamado_Emergencia.models.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    Optional<Unidade> findByCodigoLoja(String codigoLoja);
}
