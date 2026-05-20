package Atividade2_Sistemas_do_Dia_a_Dia.Exercicio1_ControleBiblioteca.Entities;

public class Livro {
    public int Codigo;
    public String Disponibilidade;
    public void DadosLivro() {

        System.out.println("Codigo do livro: " + Codigo);
        System.out.println("Disponibilidade: " + Disponibilidade);

    }
}
