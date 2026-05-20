package Atividade2_Sistemas_do_Dia_a_Dia.Exercicio2_CompraOnline.Aplication;

import Atividade2_Sistemas_do_Dia_a_Dia.Exercicio2_CompraOnline.Entities.Carrinho;
import Atividade2_Sistemas_do_Dia_a_Dia.Exercicio2_CompraOnline.Entities.Cliente;
import Atividade2_Sistemas_do_Dia_a_Dia.Exercicio2_CompraOnline.Entities.Produto;

import java.util.Scanner;

public class Main {
    static void main() {

        Scanner sc = new Scanner(System.in);

        Produto produto1 = new Produto("Teclado", 200);

        Produto produto2 = new Produto("Mouse", 150);

        Produto produto3 = new Produto("Monitor", 300);

        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();

        System.out.print("Digite seu CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Digite seu endereço: ");
        String endereco = sc.nextLine();

        Cliente cliente = new Cliente(nome, cpf, endereco);

        Carrinho carrinho =
                new Carrinho(produto1, produto2, produto3);

        double total = carrinho.calcularTotal();

        System.out.println("Total: R$ " + total);

        double frete;

        if (total > 500) {

            frete = 0;

            System.out.println("Frete grátis.");

        } else {

            frete = 25;

            System.out.println("Frete: R$ 25");
        }

        double valorFinal = total + frete;

        boolean cartaoAprovado = true;

        if (cartaoAprovado) {

            int numeroPedido = 1234;

            System.out.println("Pagamento aprovado.");

            System.out.println("Pedido gerado: " + numeroPedido);

            System.out.println("Compra registrada.");

            System.out.println("Confirmação enviada.");

            System.out.println("Compra realizada com sucesso.");

            System.out.println("Cliente: " + cliente.nome);

            System.out.println("CPF: " + cliente.cpf);

            System.out.println("Endereço: " + cliente.endereco);

            System.out.println("Valor final: R$ " + valorFinal);

        } else {

            System.out.println("Pagamento não autorizado.");
        }



        sc.close();
    }
    }
