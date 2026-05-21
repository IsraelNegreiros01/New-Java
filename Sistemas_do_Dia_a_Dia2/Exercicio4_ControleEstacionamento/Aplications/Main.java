package Sistemas_do_Dia_a_Dia2.Exercicio4_ControleEstacionamento.Aplications;

import Sistemas_do_Dia_a_Dia2.Exercicio4_ControleEstacionamento.Entities.Veículo;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Veículo v = new Veículo();

        int vagasDisponiveis;

        System.out.println("Digite a quantidade de vagas disponiveis:");
        vagasDisponiveis = sc.nextInt();

        if (vagasDisponiveis <= 0) {

            System.out.println("Estacionamento lotado.");

        } else {

            sc.nextLine();

            System.out.println("Digite a placa do veiculo:");
            v.placa = sc.nextLine();

            System.out.println("Digite o tipo do veiculo (carro ou moto):");
            v.tipo = sc.nextLine();

            System.out.println("Digite a hora de entrada:");
            v.horaEntrada = sc.nextInt();

            System.out.println("=== SAIDA DO VEICULO ===");

            sc.nextLine();

            System.out.println("Informe a placa do veiculo:");
            String placaSaida = sc.nextLine();

            if (placaSaida.equals(v.placa)) {

                System.out.println("Digite a hora de saida:");
                int horaSaida = sc.nextInt();

                int tempo = horaSaida - v.horaEntrada;

                double valor = v.calcularPagamento(horaSaida);

                System.out.println("Tempo estacionado: " + tempo + " hora(s)");

                System.out.printf("Valor total: R$ %.2f%n", valor);

                sc.nextLine();

                System.out.println("Pagamento aprovado? (sim/nao)");
                String pagamento = sc.nextLine();

                if (pagamento.equalsIgnoreCase("sim")) {

                    System.out.println("Saida liberada.");

                } else {

                    System.out.println("Pagamento nao autorizado.");
                }

            } else {

                System.out.println("Veiculo nao encontrado.");
            }
        }

        sc.close();
    }
}