package Lista_de_Orientação_a_Objetos._6_Sistema_de_Notas.Aplications;

import Lista_de_Orientação_a_Objetos._6_Sistema_de_Notas.Entities.Aluno;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Aluno aluno = new Aluno();

        System.out.print("Nome do aluno: ");
        aluno.nome = sc.nextLine();
        System.out.print("Matrícula: ");
        aluno.matricula = sc.nextLine();

        System.out.println("Digite as 3 notas:");
        for (int i = 0; i < 3; i++) {
            System.out.printf("Nota %d: ", i + 1);
            aluno.notas[i] = sc.nextDouble();
        }

        System.out.println();
        System.out.println("RELATÓRIO:");
        System.out.println("Aluno: " + aluno.nome);
        System.out.printf("Média Final: %.2f%n", aluno.calcularMedia());
        System.out.println("Situação: " + aluno.verificarSituacao());

        sc.close();
    }
}