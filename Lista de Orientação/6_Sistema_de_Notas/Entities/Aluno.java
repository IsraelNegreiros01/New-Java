package Lista_de_Orientação_a_Objetos._6_Sistema_de_Notas.Entities;

public class Aluno {

    public String nome;
    public String matricula;
    public double[] notas = new double[3];

    public double calcularMedia() {
        double soma = 0.0;
        for (int i = 0; i < notas.length; i++) {
            soma += notas[i];
        }
        return soma / notas.length;
    }

    public String verificarSituacao() {
        if (calcularMedia() >= 7.0) {
            return "Aprovado";
        } else {
            return "Reprovado";
        }
    }
}