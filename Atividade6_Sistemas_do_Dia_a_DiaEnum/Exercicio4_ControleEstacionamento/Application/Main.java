package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio4_ControleEstacionamento.Application;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio4_ControleEstacionamento.Entities.Veiculo;
import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio4_ControleEstacionamento.Entities.StatusEstacionamento;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Veiculo v = new Veiculo();

        int vagas;

        System.out.print("Quantidade de vagas disponiveis: ");
        vagas = sc.nextInt();

        v.setVagasDisponiveis(vagas > 0);

        if (vagas > 0) {

            sc.nextLine();

            System.out.print("Placa: ");
            v.setPlaca(sc.nextLine());

            System.out.print("Tipo (carro/moto): ");
            v.setTipo(sc.nextLine());

            System.out.print("Hora de entrada: ");
            v.setHoraEntrada(sc.nextInt());

            System.out.println("=== SAIDA ===");

            sc.nextLine();

            System.out.print("Informe a placa: ");
            String placaSaida = sc.nextLine();

            if (placaSaida.equalsIgnoreCase(v.getPlaca())) {

                System.out.print("Hora de saida: ");
                int horaSaida = sc.nextInt();

                int tempo = horaSaida - v.getHoraEntrada();

                double valor = v.calcularPagamento(horaSaida);

                System.out.println("Tempo estacionado: "
                        + tempo + " hora(s)");

                System.out.printf("Valor total: R$ %.2f%n", valor);

                String pagamento;

                do {

                    System.out.print("Pagamento aprovado? (sim/nao): ");
                    pagamento = sc.next();

                } while (!pagamento.equalsIgnoreCase("sim")
                        && !pagamento.equalsIgnoreCase("nao"));

                v.setPagamentoAprovado(
                        pagamento.equalsIgnoreCase("sim"));
            }
        }

        StatusEstacionamento status = v.verificarSaida();

        switch (status) {

            case LOTADO:
                System.out.println("Estacionamento lotado.");
                break;

            case PAGAMENTO_RECUSADO:
                System.out.println("Pagamento nao autorizado.");
                break;

            case SAIDA_LIBERADA:
                System.out.println("Saida liberada.");
                break;
        }

        sc.close();
    }
}