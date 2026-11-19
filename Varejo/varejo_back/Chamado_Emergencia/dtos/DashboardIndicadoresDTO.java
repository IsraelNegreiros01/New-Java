package Chamado_Emergencia.dtos;

import java.util.Map;

public class DashboardIndicadoresDTO {

    private long totalChamados;
    private long chamadosAbertos;
    private long chamadosResolvidos;
    private long chamadosEscalados;
    private long dentroSLA;
    private long foraSLA;
    private Map<String, Long> totalPorLoja;
    private Map<String, Long> totalPorCategoria;
    private Map<String, Long> totalPorNivelSuporte;

    public DashboardIndicadoresDTO() {}

    public long getTotalChamados() { return totalChamados; }
    public void setTotalChamados(long totalChamados) { this.totalChamados = totalChamados; }
    public long getChamadosAbertos() { return chamadosAbertos; }
    public void setChamadosAbertos(long chamadosAbertos) { this.chamadosAbertos = chamadosAbertos; }
    public long getChamadosResolvidos() { return chamadosResolvidos; }
    public void setChamadosResolvidos(long chamadosResolvidos) { this.chamadosResolvidos = chamadosResolvidos; }
    public long getChamadosEscalados() { return chamadosEscalados; }
    public void setChamadosEscalados(long chamadosEscalados) { this.chamadosEscalados = chamadosEscalados; }
    public long getDentroSLA() { return dentroSLA; }
    public void setDentroSLA(long dentroSLA) { this.dentroSLA = dentroSLA; }
    public long getForaSLA() { return foraSLA; }
    public void setForaSLA(long foraSLA) { this.foraSLA = foraSLA; }
    public Map<String, Long> getTotalPorLoja() { return totalPorLoja; }
    public void setTotalPorLoja(Map<String, Long> totalPorLoja) { this.totalPorLoja = totalPorLoja; }
    public Map<String, Long> getTotalPorCategoria() { return totalPorCategoria; }
    public void setTotalPorCategoria(Map<String, Long> totalPorCategoria) { this.totalPorCategoria = totalPorCategoria; }
    public Map<String, Long> getTotalPorNivelSuporte() { return totalPorNivelSuporte; }
    public void setTotalPorNivelSuporte(Map<String, Long> totalPorNivelSuporte) { this.totalPorNivelSuporte = totalPorNivelSuporte; }
}
