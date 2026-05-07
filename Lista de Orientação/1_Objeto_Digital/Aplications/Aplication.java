package Lista_de_Orientação_a_Objetos._1_Objeto_Digital.Aplications;

import Lista_de_Orientação_a_Objetos._1_Objeto_Digital.Entities.Lampada;

import java.util.Scanner;
public class Aplication {
    public static void main(String[] args) {
        Lampada lampada = new Lampada();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 1 para ligar ou 2 para desligar:");
        int opcao = scanner.nextInt();
        if (opcao == 1) {
            lampada.ligar();
        } else if (opcao == 2) {
            lampada.desligar();
        }
        if (lampada.ligada) {
            System.out.println("A lâmpada está acesa.");
        } else {
            System.out.println("A lâmpada está apagada.");
        }
        scanner.close();
    }
}
