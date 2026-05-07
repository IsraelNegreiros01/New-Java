package Lista_de_Orientação_a_Objetos._7_Carro_no_Mundo_Real.Aplications;

import Lista_de_Orientação_a_Objetos._7_Carro_no_Mundo_Real.Entities.Carro;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Carro meuCarro = new Carro();

        System.out.print("Digite a marca do carro: ");
        meuCarro.marca = sc.nextLine();
        System.out.print("Digite o modelo do carro: ");
        meuCarro.modelo = sc.nextLine();

        System.out.println("\nCarro: " + meuCarro.marca + " " + meuCarro.modelo);

        meuCarro.acelerar();
        meuCarro.acelerar();
        meuCarro.frear();
        meuCarro.frear();
        meuCarro.frear();

        sc.close();
    }
}