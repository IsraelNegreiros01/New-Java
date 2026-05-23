package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio2_CompraOnline.Entities;

public class Carrinho {

    private Produto produto1;
    private Produto produto2;
    private Produto produto3;

    public Carrinho(Produto produto1, Produto produto2, Produto produto3) {

        this.produto1 = produto1;
        this.produto2 = produto2;
        this.produto3 = produto3;
    }

    public double calcularTotal() {

        return produto1.getPreco() +
                produto2.getPreco() +
                produto3.getPreco();
    }
}