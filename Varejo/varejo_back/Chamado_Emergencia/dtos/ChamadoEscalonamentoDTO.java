package Chamado_Emergencia.dtos;

import Chamado_Emergencia.enums.NivelSuporte;

public class ChamadoEscalonamentoDTO {

    private NivelSuporte novoNivel;
    private String motivo;
    private Long usuarioId;

    public ChamadoEscalonamentoDTO() {}

    public NivelSuporte getNovoNivel() { return novoNivel; }
    public void setNovoNivel(NivelSuporte novoNivel) { this.novoNivel = novoNivel; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
