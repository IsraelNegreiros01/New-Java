package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio3_AgendamentoMedico.Entities;
public class Paciente {
    private String nome;
    private double cpf;
    private String especialidade;
    private int cadastro;
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getCPF() { return this.cpf; }
    public void setCPF(double cpf) { this.cpf = cpf; }
    public String getEspecialidade() { return this.especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public int getCadastro() { return this.cadastro; }
    public void setCadastro(int cadastro) { this.cadastro = cadastro; }
}