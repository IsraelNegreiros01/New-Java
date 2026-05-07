package Lista_de_Orientação_a_Objetos._10_Termômetro_Inteligente.Entities;

public class Termometro {

    public double celsius;

    public double paraFahrenheit() {
        return celsius * 1.8 + 32;
    }

    public double paraKelvin() {
        return celsius + 273.15;
    }
}