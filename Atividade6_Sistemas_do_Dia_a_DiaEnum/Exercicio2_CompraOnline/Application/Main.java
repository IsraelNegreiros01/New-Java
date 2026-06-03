package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio2_CompraOnline.Application;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio2_CompraOnline.Entities.Compra;
import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio2_CompraOnline.Entities.StatusPedido;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Compra pedido = new Compra();

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        String nome;

        do {
            System.out.print("Nome: ");
            nome = sc.nextLine();

            if (nome.trim().isEmpty()) {
                System.out.println("O nome é obrigatório.");
            }

        } while (nome.trim().isEmpty());

        pedido.setNome(nome);

        String cpf;

        do {
            System.out.print("CPF: ");
            cpf = sc.nextLine();

            if (cpf.trim().isEmpty()) {
                System.out.println("O CPF é obrigatório.");
            }

        } while (cpf.trim().isEmpty());

        pedido.setCpf(cpf);

        String endereco;

        do {
            System.out.print("Endereco: ");
            endereco = sc.nextLine();

            if (endereco.trim().isEmpty()) {
                System.out.println("O endereco nao pode ficar vazio.");
            }

        } while (endereco.trim().isEmpty());

        pedido.setEndereco(endereco);

        double valorCompra;

        do {
            System.out.print("Valor da compra: ");
            valorCompra = sc.nextDouble();

            if (valorCompra <= 0) {
                System.out.println("Informe um valor maior que zero.");
            }

        } while (valorCompra <= 0);

        pedido.setValorCompra(valorCompra);

        pedido.setFrete(pedido.calcularFrete());

        String resposta;

        do {

            System.out.print("Cartao aprovado? (sim/nao): ");
            resposta = sc.next();

            if (!resposta.equalsIgnoreCase("sim")
                    && !resposta.equalsIgnoreCase("nao")) {

                System.out.println("Digite apenas sim ou nao.");
            }

        } while (!resposta.equalsIgnoreCase("sim")
                && !resposta.equalsIgnoreCase("nao"));

        pedido.setCartaoAprovado(
                resposta.equalsIgnoreCase("sim"));

        StatusPedido status = pedido.finalizarCompra();

        switch (status) {

            case PAGAMENTO_RECUSADO:

                pedido.setStatus(StatusPedido.PAGAMENTO_RECUSADO);

                System.out.println("Pagamento nao autorizado. Preparesse para morrer.");
                break;

            case COMPRA_REALIZADA:

                pedido.setStatus(StatusPedido.COMPRA_REALIZADA);

                pedido.setNumeroPedido(1001);

                double total = pedido.getValorCompra()
                        + pedido.getFrete();

                System.out.println("Numero do pedido: "
                        + pedido.getNumeroPedido());

                System.out.printf("Valor total: %.2f%n",
                        total);

                System.out.println("Compra realizada com sucesso.");
                break;

            default:
                break;
        }

        sc.close();
    }
}