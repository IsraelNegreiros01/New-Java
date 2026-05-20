package
Atividade2_Sistemas_do_Dia_a_Dia.Exercicio2_CompraOnline.Entities;

public class Carrinho {

    Produto produto1, produto2, produto3;

    public Carrinho(Produto produto1, Produto produto2, Produto produto3) {

        this.produto1 = produto1;
        this.produto2 = produto2;
        this.produto3 = produto3;
    }

    public double calcularTotal() {

        return produto1.preco +
                produto2.preco +
                produto3.preco;
    }
}