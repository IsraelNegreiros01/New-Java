package Chamado_Emergencia.dtos;

import Chamado_Emergencia.enums.Impacto;
import Chamado_Emergencia.enums.TipoChamado;
import Chamado_Emergencia.enums.Urgencia;

public class ChamadoCriacaoDTO {

    private String titulo;
    private String descricao;
    private TipoChamado tipo;
    private Impacto impacto;
    private Urgencia urgencia;
    private Long unidadeId;
    private Long categoriaId;
    private Long solicitanteId;
    private Boolean suporteRemoto = false;

    public ChamadoCriacaoDTO() {}

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
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public Long getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Long solicitanteId) { this.solicitanteId = solicitanteId; }
    public Boolean getSuporteRemoto() { return suporteRemoto; }
    public void setSuporteRemoto(Boolean suporteRemoto) { this.suporteRemoto = suporteRemoto; }
}
