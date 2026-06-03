package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio3_AgendamentoMedico.application;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio3_AgendamentoMedico.entities.Consulta;
import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio3_AgendamentoMedico.entities.StatusConsulta;

import java.util.Scanner;

public class application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Consulta consulta = new Consulta();

        System.out.print("CPF: ");
        consulta.setCpf(sc.nextLine());

        System.out.print("Nome: ");
        consulta.setNome(sc.nextLine());

        System.out.print("Especialidade desejada: ");
        consulta.setEspecialidade(sc.nextLine());

        String resposta;

        do {
            System.out.print("Paciente cadastrado? (sim/nao): ");
            resposta = sc.nextLine();
        } while (!resposta.equalsIgnoreCase("sim")
                && !resposta.equalsIgnoreCase("nao"));

        consulta.setCadastrado(resposta.equalsIgnoreCase("sim"));

        if (consulta.isCadastrado()) {

            do {
                System.out.print("Existem horarios disponiveis? (sim/nao): ");
                resposta = sc.nextLine();
            } while (!resposta.equalsIgnoreCase("sim")
                    && !resposta.equalsIgnoreCase("nao"));

            consulta.setHorariosDisponiveis(
                    resposta.equalsIgnoreCase("sim"));

            if (consulta.isHorariosDisponiveis()) {

                System.out.println("Horarios livres: 08:00, 10:00, 14:00");

                System.out.print("Escolha um horario: ");
                sc.nextLine();

                do {
                    System.out.print("Horario ainda disponivel? (sim/nao): ");
                    resposta = sc.nextLine();
                } while (!resposta.equalsIgnoreCase("sim")
                        && !resposta.equalsIgnoreCase("nao"));

                consulta.setHorarioLivre(
                        resposta.equalsIgnoreCase("sim"));
            }
        }

        StatusConsulta status = consulta.verificarConsulta();

        switch (status) {

            case PACIENTE_NAO_CADASTRADO:
                System.out.println("Paciente nao cadastrado.");
                break;

            case SEM_HORARIOS:
                System.out.println("Nao ha horarios disponiveis.");
                break;

            case HORARIO_INDISPONIVEL:
                System.out.println("Horario indisponivel.");
                break;

            case CONSULTA_AGENDADA:
                System.out.println("Consulta agendada com sucesso.");
                break;
        }

        sc.close();
    }
}