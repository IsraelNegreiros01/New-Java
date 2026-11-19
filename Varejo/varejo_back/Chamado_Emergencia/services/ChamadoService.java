package Chamado_Emergencia.services;

import Chamado_Emergencia.dtos.ChamadoCriacaoDTO;
import Chamado_Emergencia.dtos.ChamadoEscalonamentoDTO;
import Chamado_Emergencia.dtos.EncerramentoChamadoDTO;
import Chamado_Emergencia.enums.*;
import Chamado_Emergencia.models.*;
import Chamado_Emergencia.repositories.*;
import Chamado_Emergencia.enums.*;
import Chamado_Emergencia.models.*;
import Chamado_Emergencia.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired private ChamadoRepository chamadoRepository;
    @Autowired private UnidadeRepository unidadeRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private HistoricoChamadoRepository historicoRepository;

    public List<Chamado> listarTodos() {
        return chamadoRepository.findAll();
    }

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com o ID: " + id));
    }

    @Transactional
    public Chamado criarChamado(ChamadoCriacaoDTO dto) {
        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Usuario solicitante = usuarioRepository.findById(dto.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));

        Chamado chamado = new Chamado();
        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        chamado.setTipo(dto.getTipo());
        chamado.setImpacto(dto.getImpacto());
        chamado.setUrgencia(dto.getUrgencia());
        chamado.setUnidade(unidade);
        chamado.setCategoria(categoria);
        chamado.setSolicitante(solicitante);
        chamado.setSuporteRemoto(dto.getSuporteRemoto());
        chamado.setStatus(StatusChamado.NOVO);
        chamado.setNivelSuporte(NivelSuporte.N1);

        Prioridade prioridade = calcularPrioridade(dto.getImpacto(), dto.getUrgencia(), categoria, unidade);
        chamado.setPrioridade(prioridade);
        chamado.setPrazoLimiteSLA(calcularPrazoSLA(prioridade));

        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        registrarHistorico(chamadoSalvo, solicitante, "CRIACAO", "Chamado aberto no sistema", null, null);

        return chamadoSalvo;
    }

    @Transactional
    public Chamado escalarChamado(Long id, ChamadoEscalonamentoDTO dto) {
        Chamado chamado = buscarPorId(id);
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        NivelSuporte nivelOrigem = chamado.getNivelSuporte();
        chamado.setNivelSuporte(dto.getNovoNivel());
        chamado.setStatus(StatusChamado.ESCALADO);
        chamado.setTecnicoAtribuido(null);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        registrarHistorico(chamadoSalvo, usuario, "ESCALONAMENTO", dto.getMotivo(), nivelOrigem, dto.getNovoNivel());

        return chamadoSalvo;
    }

    @Transactional
    public Chamado encerrarChamado(Long id, EncerramentoChamadoDTO dto) {
        Chamado chamado = buscarPorId(id);
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.getSolucao() == null || dto.getSolucao().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: É obrigatório registrar a solução antes de encerrar o chamado.");
        }

        chamado.setSolucaoRegistrada(dto.getSolucao());
        chamado.setStatus(StatusChamado.FECHADO);
        chamado.setDataEnvolvimento(LocalDateTime.now());

        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        registrarHistorico(chamadoSalvo, usuario, "ENCERRAMENTO", "Solução: " + dto.getSolucao(), null, null);

        return chamadoSalvo;
    }

    private Prioridade calcularPrioridade(Impacto impacto, Urgencia urgencia, Categoria categoria, Unidade unidade) {
        LocalTime agora = LocalTime.now();
        boolean horarioComercial = agora.isAfter(LocalTime.of(8, 0)) && agora.isBefore(LocalTime.of(19, 0));

        if (unidade.getTipoUnidade() == TipoUnidade.LOJA && horarioComercial) {
            String nomeCat = categoria.getNome().toUpperCase();
            if (nomeCat.contains("PDV") || nomeCat.contains("INTERNET")) {
                return Prioridade.CRITICA;
            }
        }

        if (impacto == Impacto.ALTO && urgencia == Urgencia.ALTA) return Prioridade.CRITICA;
        if (impacto == Impacto.ALTO || urgencia == Urgencia.ALTA) return Prioridade.ALTA;
        if (impacto == Impacto.MEDIO && urgencia == Urgencia.MEDIA) return Prioridade.MEDIA;

        return Prioridade.BAIXA;
    }

    private LocalDateTime calcularPrazoSLA(Prioridade prioridade) {
        LocalDateTime agora = LocalDateTime.now();
        return switch (prioridade) {
            case CRITICA -> agora.plusHours(2);
            case ALTA    -> agora.plusHours(8);
            case MEDIA   -> agora.plusHours(24);
            case BAIXA   -> agora.plusHours(48);
        };
    }

    private void registrarHistorico(Chamado chamado, Usuario usuario, String acao, String observacao, NivelSuporte origem, NivelSuporte destino) {
        HistoricoChamado hist = new HistoricoChamado();
        hist.setChamado(chamado);
        hist.setUsuario(usuario);
        hist.setAcao(acao);
        hist.setObservacao(observacao);
        hist.setNivelOrigem(origem);
        hist.setNivelDestino(destino);
        historicoRepository.save(hist);
    }
}
