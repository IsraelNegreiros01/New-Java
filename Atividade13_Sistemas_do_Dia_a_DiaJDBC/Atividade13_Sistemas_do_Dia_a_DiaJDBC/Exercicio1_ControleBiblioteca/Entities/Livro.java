package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities;

public class Livro {
    private int codigoLivro;
    private boolean disponivel;
    public Livro() {
    }
    public Livro(int codigoLivro, boolean disponivel) {
        this.codigoLivro = codigoLivro;
        this.disponivel = disponivel;
    }

    public int getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(int codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

}
