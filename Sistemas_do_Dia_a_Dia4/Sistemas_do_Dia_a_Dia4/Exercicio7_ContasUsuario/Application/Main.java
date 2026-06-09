package Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Application;

import Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Entities.ContaStreaming;
import Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Entities.PlanoPadrao;
import Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Entities.PlanoPremium;
import Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Entities.PlanoTipo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome do usuario: ");
            nome = sc.nextLine();
        }

        String email = "";
        while (email.trim().isEmpty()) {
            System.out.print("Email: ");
            email = sc.nextLine();
        }

        PlanoTipo tipo = null;

        while (tipo == null) {

            System.out.print("Plano (PADRAO/PREMIUM): ");
            String entrada = sc.nextLine().trim();

            if (entrada.isEmpty()) {
                continue;
            }

            try {
                tipo = PlanoTipo.valueOf(entrada.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                // Não faz nada, apenas repete a pergunta
            }
        }

        ContaStreaming conta;

        if (tipo == PlanoTipo.PADRAO) {
            conta = new PlanoPadrao(nome, email);
        }
        else {
            conta = new PlanoPremium(nome, email);
        }

        System.out.println();
        System.out.println("===== RECIBO DA ASSINATURA =====");
        System.out.println(conta);

        sc.close();
    }
}