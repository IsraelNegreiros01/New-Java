package Chamado_Emergencia.dtos;

import Chamado_Emergencia.enums.Perfil;

public class UsuarioRespostaDTO {

    private final Long id;
    private final String nome;
    private final String email;
    private final Perfil perfil;
    private final String unidade;

    public UsuarioRespostaDTO(Long id, String nome, String email, Perfil perfil, String unidade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.unidade = unidade;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Perfil getPerfil() { return perfil; }
    public String getUnidade() { return unidade; }
}
