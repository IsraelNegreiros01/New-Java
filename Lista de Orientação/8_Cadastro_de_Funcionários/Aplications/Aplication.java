package Lista_de_Orientação_a_Objetos._8_Cadastro_de_Funcionários.Aplications;

import Lista_de_Orientação_a_Objetos._8_Cadastro_de_Funcionários.Entities.Funcionario;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Funcionario func = new Funcionario();

        System.out.print("Nome: ");
        func.nome = sc.next();
        System.out.print("Sobrenome: ");
        func.sobrenome = sc.next();

        func.salarioMensal = 2000.00;

        System.out.println("Funcionário: " + func.nome + " " + func.sobrenome);
        System.out.printf("Salário Mensal Inicial: %.2f%n", func.salarioMensal);

        func.darAumento(10.0);
        System.out.println("--- Aumento de 10% aplicado ---");

        System.out.printf("Novo Salário Mensal: %.2f%n", func.salarioMensal);
        System.out.printf("Novo Salário Anual: %.2f%n", func.salarioAnual());

        sc.close();
    }
}