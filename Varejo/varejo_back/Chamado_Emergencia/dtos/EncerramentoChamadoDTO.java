package Chamado_Emergencia.dtos;

public class EncerramentoChamadoDTO {

    private String solucao;
    private Long usuarioId;

    public EncerramentoChamadoDTO() {}

    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
