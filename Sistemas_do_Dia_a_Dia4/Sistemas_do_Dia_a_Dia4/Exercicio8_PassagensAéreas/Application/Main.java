package Sistemas_do_Dia_a_Dia4.Exercicio8_PassagensAéreas.Application;


    import Sistemas_do_Dia_a_Dia4.Exercicio8_PassagensAéreas.Entities.ClasseEconomica;
    import Sistemas_do_Dia_a_Dia4.Exercicio8_PassagensAéreas.Entities.ClasseExecutiva;

    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);

            System.out.println("=== SISTEMA DE PASSAGENS AÉREAS ===");

            System.out.print("Nome do passageiro: ");
            String nome = sc.nextLine();

            System.out.print("Número do voo: ");
            String voo = sc.nextLine();

            System.out.print("Preço original da passagem: R$ ");
            double preco = sc.nextDouble();

            System.out.println("\nEscolha a classe:");
            System.out.println("1 - Econômica");
            System.out.println("2 - Executiva");
            System.out.print("Opção: ");
            int opcao = sc.nextInt();

            if (opcao == 1) {

                System.out.print("Deseja despachar mala? (true/false): ");
                boolean despacharMala = sc.nextBoolean();

                ClasseEconomica passagem =
                        new ClasseEconomica(nome, voo, preco, despacharMala);

                System.out.println(passagem);

            } else if (opcao == 2) {

                ClasseExecutiva passagem =
                        new ClasseExecutiva(nome, voo, preco);

                System.out.println(passagem);

            } else {
                System.out.println("Opção inválida!");
            }

            sc.close();
        }
    }

