package Lista_de_Orientação_a_Objetos._3_Calculadora_de_Áreas.Aplications;

import Lista_de_Orientação_a_Objetos._3_Calculadora_de_Áreas.Entities.Retangulo;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Retangulo ret = new Retangulo();

        System.out.println("Digite a base e a altura do retângulo:");
        ret.base = sc.nextDouble();
        ret.altura = sc.nextDouble();

        double area = ret.calcularArea();

        System.out.printf("Area: %.2f%n", area);

        sc.close();
    }
}