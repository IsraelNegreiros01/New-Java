package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio1_ControleBiblioteca.Entities;
public class Livro {
    private int codigo;
    private String disponibilidade;
    public int getCodigo() { return this.codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getDisponibilidade() { return this.disponibilidade; }
    public void setDisponibilidade(String disponibilidade) { this.disponibilidade = disponibilidade; }
    public void DadosLivro() {
        System.out.println("Código do Livro: " + this.codigo);
        System.out.println("Disponibilidade: " + this.disponibilidade);
    }
}