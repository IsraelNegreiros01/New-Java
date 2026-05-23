package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio2_CompraOnline.Entities;

public class Produto {

    private String nome;
    private double preco;

    public Produto(String nome, double preco) {

        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}