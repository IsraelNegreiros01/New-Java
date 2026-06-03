package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio3_AgendamentoMedico.entities;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio3_AgendamentoMedico.entities.StatusConsulta;
public class Consulta {

    private String cpf;
    private String nome;
    private String especialidade;

    private boolean cadastrado;
    private boolean horariosDisponiveis;
    private boolean horarioLivre;

    public StatusConsulta verificarConsulta() {

        if (!cadastrado) {
            return StatusConsulta.PACIENTE_NAO_CADASTRADO;
        }

        if (!horariosDisponiveis) {
            return StatusConsulta.SEM_HORARIOS;
        }

        if (!horarioLivre) {
            return StatusConsulta.HORARIO_INDISPONIVEL;

        }

        return StatusConsulta.CONSULTA_AGENDADA;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public boolean isCadastrado() {
        return cadastrado;
    }

    public void setCadastrado(boolean cadastrado) {
        this.cadastrado = cadastrado;
    }

    public boolean isHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    public void setHorariosDisponiveis(boolean horariosDisponiveis) {
        this.horariosDisponiveis = horariosDisponiveis;
    }

    public boolean isHorarioLivre() {
        return horarioLivre;
    }

    public void setHorarioLivre(boolean horarioLivre) {
        this.horarioLivre = horarioLivre;
    }
}