package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio4_ControleEstacionamento.Entities;

public class Veículo {
    private String placa;
    private String tipo;
    private int horaEntrada;

    public Veículo(String placa, String tipo, int horaEntrada) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaEntrada = horaEntrada;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getHoraEntrada() {
        return this.horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int calcularTempo(int horaSaida) {
        return horaSaida - this.horaEntrada;
    }

    public double calcularPagamento(int horaSaida) {
        int tempo = calcularTempo(horaSaida);
        double taxaHora = this.tipo.equalsIgnoreCase("moto") ? 5.0 : 10.0;
        return tempo * taxaHora;
    }
}