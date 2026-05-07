package Lista_de_Orientação_a_Objetos._4_Controle_de_Estoque.Entities;

public class Produto {

    public String Nome;
    public double Preço;

    public Double QuantidadeEstoque;
    public void resumo() {
        Double total = Preço * QuantidadeEstoque;
        System.out.println("Resumo:");
        System.out.println("Nome do produto: " + Nome);
        System.out.println("Preço do produto: R$" + Preço);
        System.out.println("Quantidade do produto no estoque: " + QuantidadeEstoque);
        System.out.println("Valor total do produto: R$" + total);
    }
}

