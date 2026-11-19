package Chamado_Emergencia.dtos;

import Chamado_Emergencia.enums.Perfil;

public class UsuarioCriacaoDTO {

    private String nome;
    private String email;
    private String senha;
    private Perfil perfil;
    private Long unidadeId;

    public UsuarioCriacaoDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
}
