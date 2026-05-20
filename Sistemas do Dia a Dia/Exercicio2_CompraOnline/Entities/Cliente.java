package Atividade2_Sistemas_do_Dia_a_Dia.Exercicio2_CompraOnline.Entities;

public class Cliente {

    public String nome;
    public String cpf;
    public String endereco;

    public Cliente(String nome, String cpf, String endereco) {

        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }
}