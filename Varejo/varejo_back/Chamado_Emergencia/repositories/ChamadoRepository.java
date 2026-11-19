package Chamado_Emergencia.repositories;

import Chamado_Emergencia.enums.NivelSuporte;
import Chamado_Emergencia.enums.StatusChamado;
import Chamado_Emergencia.models.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByUnidadeId(Long unidadeId);
    List<Chamado> findByNivelSuporte(NivelSuporte nivelSuporte);
    List<Chamado> findByStatus(StatusChamado status);
}
