package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio1_ControleBiblioteca.Entities;
public class Aluno {
    private int matricula;
    private int livrosEmprestados;
    private String multa;
    public int getMatricula() { return this.matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }
    public int getLivrosEmprestados() { return this.livrosEmprestados; }
    public void setLivrosEmprestados(int livrosEmprestados) { this.livrosEmprestados = livrosEmprestados; }
    public String getMulta() { return this.multa; }
    public void setMulta(String multa) { this.multa = multa; }
    public void DadosAluno() {
        System.out.println("Matrícula do Aluno: " + this.matricula);
        System.out.println("Livros Emprestados: " + this.livrosEmprestados);
        System.out.println("Possui Multa: " + this.multa);
    }
}