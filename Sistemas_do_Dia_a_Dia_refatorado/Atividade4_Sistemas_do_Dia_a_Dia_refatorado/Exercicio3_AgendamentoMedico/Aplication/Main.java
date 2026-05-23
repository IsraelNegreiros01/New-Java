package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio3_AgendamentoMedico.Aplication;
import Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio3_AgendamentoMedico.Entities.Consulta;
import Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio3_AgendamentoMedico.Entities.Paciente;
import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Paciente paciente = new Paciente();
        Consulta consulta = new Consulta();
        Random random = new Random();
        System.out.println("Insira seu nome, CPF e a especialidade necessária");
        paciente.setNome(sc.nextLine());
        paciente.setCPF(sc.nextDouble());
        sc.nextLine();
        paciente.setEspecialidade(sc.nextLine());
        paciente.setCadastro(random.nextInt(2) + 1);
        int disponibilidade = random.nextInt(1) + 1;
        if (paciente.getCadastro() == 1) {
            System.out.println("Paciente não cadastrado");
            sc.close();
            return;
        } else if (paciente.getCadastro() == 2) {
            System.out.println("Paciente encontrado");
        }
        if (disponibilidade == 1) {
            System.out.println("Não há horários disponíveis");
        } else {
            String horario1 = "17:00";
            String horario2 = "18:30";
            String horario3 = "19:00";
            String horario4 = "20:30";
            System.out.println("Horários disponíveis:");
            System.out.println(horario1);
            System.out.println(horario2);
            System.out.println(horario3);
            System.out.println(horario4);
            System.out.println("Escolha um horário:");
            consulta.setHorario(sc.nextLine());
            int horariolivre = random.nextInt(2) + 1;
            if (horariolivre == 1) {
                System.out.println("Consulta agendada com sucesso");
            } else {
                System.out.println("Horário indisponível");
            }
        }
        sc.close();
    }
}