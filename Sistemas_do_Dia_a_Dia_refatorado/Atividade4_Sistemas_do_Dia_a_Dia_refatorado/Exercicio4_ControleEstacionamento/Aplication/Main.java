package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio4_ControleEstacionamento.Aplication;

import Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio4_ControleEstacionamento.Entities.Veículo;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite a quantidade de vagas disponiveis:");
            int vagasDisponiveis = sc.nextInt();
            if (vagasDisponiveis <= 0) {
                System.out.println("Estacionamento lotado.");
                sc.close();
                return;
            }
            sc.nextLine();
            String placa;
            do {
                System.out.println("Digite a placa do veiculo:");
                placa = sc.nextLine();

                if (placa.isEmpty()) {
                    System.out.println("A placa nao pode ficar vazia.");
                }

            } while (placa.isEmpty());
            String tipo;
            do {
                System.out.println("Digite o tipo do veiculo (carro ou moto):");
                tipo = sc.nextLine();

                if (tipo.isEmpty()) {
                    System.out.println("O tipo do veiculo nao pode ficar vazio.");
                }

            } while (tipo.isEmpty());

            System.out.println("Digite a hora de entrada:");
            int horaEntrada = sc.nextInt();

            Veículo veiculo = new Veículo(placa, tipo, horaEntrada);

            System.out.println("=== SAIDA DO VEICULO ===");

            sc.nextLine();

            System.out.println("Informe a placa do veiculo:");
            String placaSaida = sc.nextLine();

            if (!placaSaida.equalsIgnoreCase(veiculo.getPlaca())) {
                System.out.println("Veiculo nao encontrado.");
                sc.close();
                return;
            }

            System.out.println("Digite a hora de saida:");
            int horaSaida = sc.nextInt();

            int tempo = veiculo.calcularTempo(horaSaida);
            double valor = veiculo.calcularPagamento(horaSaida);

            System.out.println("Tempo estacionado: " + tempo + " hora(s)");
            System.out.printf("Valor total: R$ %.2f%n", valor);

            sc.nextLine();

            String pagamento;

            do {

                System.out.println("Pagamento aprovado? (sim/nao)");
                pagamento = sc.nextLine();

                if (pagamento.isEmpty()) {
                    System.out.println("Digite sim ou nao.");
                }

            } while (pagamento.isEmpty());

            if (pagamento.equalsIgnoreCase("sim")) {

                System.out.println("Saida liberada.");

            } else {

                System.out.println("Pagamento nao autorizado.");
            }

            sc.close();
        }
}