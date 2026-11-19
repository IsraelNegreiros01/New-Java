package Chamado_Emergencia.models;

import Chamado_Emergencia.enums.NivelSuporte;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_chamados")
public class HistoricoChamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String acao;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_origem")
    private NivelSuporte nivelOrigem;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_destino")
    private NivelSuporte nivelDestino;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro = LocalDateTime.now();

    public HistoricoChamado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Chamado getChamado() { return chamado; }
    public void setChamado(Chamado chamado) { this.chamado = chamado; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public NivelSuporte getNivelOrigem() { return nivelOrigem; }
    public void setNivelOrigem(NivelSuporte nivelOrigem) { this.nivelOrigem = nivelOrigem; }
    public NivelSuporte getNivelDestino() { return nivelDestino; }
    public void setNivelDestino(NivelSuporte nivelDestino) { this.nivelDestino = nivelDestino; }
    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime dataRegistro) { this.dataRegistro = dataRegistro; }
}
