package Lista_de_Orientação_a_Objetos._7_Carro_no_Mundo_Real.Entities;

public class Carro {

    public String modelo;
    public String marca;
    public double velocidadeAtual;

    public void acelerar() {
        velocidadeAtual += 10;
        System.out.println("Acelerando... Velocidade atual: " + velocidadeAtual + " km/h");
    }

    public void frear() {
        if (velocidadeAtual >= 10) {
            velocidadeAtual -= 10;
        } else {
            velocidadeAtual = 0;
        }
        System.out.println("Freando... Velocidade atual: " + velocidadeAtual + " km/h");
    }
}