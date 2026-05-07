package Lista_de_Orientação_a_Objetos._10_Termômetro_Inteligente.Aplications;

import Lista_de_Orientação_a_Objetos._10_Termômetro_Inteligente.Entities.Termometro;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Termometro term = new Termometro();

        System.out.print("Digite a temperatura em Celsius: ");
        term.celsius = sc.nextDouble();

        System.out.println("\n--- Conversões ---");
        System.out.printf("Temperatura em Celsius: %.2f°C%n", term.celsius);
        System.out.printf("Temperatura em Fahrenheit: %.2f°F%n", term.paraFahrenheit());
        System.out.printf("Temperatura em Kelvin: %.2fK%n", term.paraKelvin());

        sc.close();
    }
}