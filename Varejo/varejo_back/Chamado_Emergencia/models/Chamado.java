package Chamado_Emergencia.models;

import Chamado_Emergencia.enums.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoChamado tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Impacto impacto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Urgencia urgencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.NOVO;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_suporte", nullable = false)
    private NivelSuporte nivelSuporte = NivelSuporte.N1;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "tecnico_atribuido_id")
    private Usuario tecnicoAtribuido;

    @Column(name = "solucao_registrada", columnDefinition = "TEXT")
    private String solucaoRegistrada;

    @Column(name = "suporte_remoto")
    private Boolean suporteRemoto = false;

    @Column(name = "data_abertura", updatable = false)
    private LocalDateTime dataAbertura = LocalDateTime.now();

    @Column(name = "prazo_limite_sla", nullable = false)
    private LocalDateTime prazoLimiteSLA;

    @Column(name = "data_envolvimento")
    private LocalDateTime dataEnvolvimento;

    public Chamado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public TipoChamado getTipo() { return tipo; }
    public void setTipo(TipoChamado tipo) { this.tipo = tipo; }
    public Impacto getImpacto() { return impacto; }
    public void setImpacto(Impacto impacto) { this.impacto = impacto; }
    public Urgencia getUrgencia() { return urgencia; }
    public void setUrgencia(Urgencia urgencia) { this.urgencia = urgencia; }
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }
    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }
    public NivelSuporte getNivelSuporte() { return nivelSuporte; }
    public void setNivelSuporte(NivelSuporte nivelSuporte) { this.nivelSuporte = nivelSuporte; }
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Usuario getSolicitante() { return solicitante; }
    public void setSolicitante(Usuario solicitante) { this.solicitante = solicitante; }
    public Usuario getTecnicoAtribuido() { return tecnicoAtribuido; }
    public void setTecnicoAtribuido(Usuario tecnicoAtribuido) { this.tecnicoAtribuido = tecnicoAtribuido; }
    public String getSolucaoRegistrada() { return solucaoRegistrada; }
    public void setSolucaoRegistrada(String solucaoRegistrada) { this.solucaoRegistrada = solucaoRegistrada; }
    public Boolean getSuporteRemoto() { return suporteRemoto; }
    public void setSuporteRemoto(Boolean suporteRemoto) { this.suporteRemoto = suporteRemoto; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }
    public LocalDateTime getPrazoLimiteSLA() { return prazoLimiteSLA; }
    public void setPrazoLimiteSLA(LocalDateTime prazoLimiteSLA) { this.prazoLimiteSLA = prazoLimiteSLA; }
    public LocalDateTime getDataEnvolvimento() { return dataEnvolvimento; }
    public void setDataEnvolvimento(LocalDateTime dataEnvolvimento) { this.dataEnvolvimento = dataEnvolvimento; }
}
