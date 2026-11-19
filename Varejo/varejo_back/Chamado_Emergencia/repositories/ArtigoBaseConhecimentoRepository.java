package Chamado_Emergencia.repositories;

import Chamado_Emergencia.models.ArtigoBaseConhecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtigoBaseConhecimentoRepository extends JpaRepository<ArtigoBaseConhecimento, Long> {
    List<ArtigoBaseConhecimento> findByCategoriaId(Long categoriaId);
    List<ArtigoBaseConhecimento> findByTituloContainingIgnoreCaseOrConteudoContainingIgnoreCase(String termoTitulo, String termoConteudo);
}