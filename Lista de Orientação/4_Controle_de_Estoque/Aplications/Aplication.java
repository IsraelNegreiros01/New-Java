package Lista_de_Orientação_a_Objetos._4_Controle_de_Estoque.Aplications;

import Lista_de_Orientação_a_Objetos._4_Controle_de_Estoque.Entities.Produto;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {

        Produto produto= new Produto();
        Scanner scanner= new Scanner(System.in);
        System.out.println("Insira o nome do produto");
        produto.Nome= scanner.nextLine();
        System.out.println("Insira o preço do produto");
        produto.Preço= scanner.nextDouble();
        System.out.println("Insira a quantidade do produto no estoque");
        produto.QuantidadeEstoque= scanner.nextDouble();
        produto.resumo();
        scanner.close();
    }


}

