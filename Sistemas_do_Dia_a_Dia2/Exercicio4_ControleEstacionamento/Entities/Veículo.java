package Sistemas_do_Dia_a_Dia2.Exercicio4_ControleEstacionamento.Entities;

public class Veículo {

    public String placa;
    public String tipo;
    public int horaEntrada;

    public double calcularPagamento(int horaSaida) {

        int tempo = horaSaida - horaEntrada;

        if (tempo <= 1) {
            return 10.0;
        } else {
            return 10.0 + (tempo - 1) * 5.0;
        }
    }
}