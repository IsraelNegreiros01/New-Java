package Chamado_Emergencia.models;

import Chamado_Emergencia.enums.TipoUnidade;
import jakarta.persistence.*;

@Entity
@Table(name = "unidades")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_loja", nullable = false, unique = true)
    private String codigoLoja;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String uf;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_unidade", nullable = false)
    private TipoUnidade tipoUnidade;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoLoja() { return codigoLoja; }
    public void setCodigoLoja(String codigoLoja) { this.codigoLoja = codigoLoja; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public TipoUnidade getTipoUnidade() { return tipoUnidade; }
    public void setTipoUnidade(TipoUnidade tipoUnidade) { this.tipoUnidade = tipoUnidade; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getNome() {
        return codigoLoja + " - " + cidade;
    }
}