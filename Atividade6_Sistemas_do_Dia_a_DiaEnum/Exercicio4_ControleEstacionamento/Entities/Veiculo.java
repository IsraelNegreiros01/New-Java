package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio4_ControleEstacionamento.Entities;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio4_ControleEstacionamento.Entities.StatusEstacionamento;

public class Veiculo {

    private String placa;
    private String tipo;
    private int horaEntrada;

    private boolean vagasDisponiveis;
    private boolean pagamentoAprovado;

    public double calcularPagamento(int horaSaida) {

        int tempo = horaSaida - horaEntrada;

        if (tempo <= 1) {
            return 10.0;
        }

        return 10.0 + (tempo - 1) * 5.0;
    }

    public StatusEstacionamento verificarSaida() {

        if (!vagasDisponiveis) {
            return StatusEstacionamento.LOTADO;
        }

        if (!pagamentoAprovado) {
            return StatusEstacionamento.PAGAMENTO_RECUSADO;
        }

        return StatusEstacionamento.SAIDA_LIBERADA;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public boolean isVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(boolean vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public boolean isPagamentoAprovado() {
        return pagamentoAprovado;
    }

    public void setPagamentoAprovado(boolean pagamentoAprovado) {
        this.pagamentoAprovado = pagamentoAprovado;
    }
}