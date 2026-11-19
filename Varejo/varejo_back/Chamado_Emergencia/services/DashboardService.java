package Chamado_Emergencia.services;

import Chamado_Emergencia.dtos.DashboardIndicadoresDTO;
import Chamado_Emergencia.enums.StatusChamado;
import Chamado_Emergencia.models.Chamado;
import Chamado_Emergencia.repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    public DashboardIndicadoresDTO obterIndicadores() {
        List<Chamado> todos = chamadoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();

        DashboardIndicadoresDTO dto = new DashboardIndicadoresDTO();
        dto.setTotalChamados(todos.size());

        dto.setChamadosAbertos(todos.stream().filter(c -> c.getStatus() == StatusChamado.NOVO || c.getStatus() == StatusChamado.EM_ATENDIMENTO).count());
        dto.setChamadosResolvidos(todos.stream().filter(c -> c.getStatus() == StatusChamado.RESOLVIDO || c.getStatus() == StatusChamado.FECHADO).count());
        dto.setChamadosEscalados(todos.stream().filter(c -> c.getStatus() == StatusChamado.ESCALADO).count());

        long foraSla = todos.stream().filter(c -> {
            if (c.getDataEnvolvimento() != null) {
                return c.getDataEnvolvimento().isAfter(c.getPrazoLimiteSLA());
            }
            return agora.isAfter(c.getPrazoLimiteSLA());
        }).count();

        dto.setForaSLA(foraSla);
        dto.setDentroSLA(todos.size() - foraSla);

        dto.setTotalPorLoja(todos.stream()
                .collect(Collectors.groupingBy(c -> c.getUnidade().getNome(), Collectors.counting())));

        dto.setTotalPorCategoria(todos.stream()
                .collect(Collectors.groupingBy(c -> c.getCategoria().getNome(), Collectors.counting())));

        dto.setTotalPorNivelSuporte(todos.stream()
                .collect(Collectors.groupingBy(c -> c.getNivelSuporte().name(), Collectors.counting())));

        return dto;
    }
}
