package Lista_de_Orientação_a_Objetos._5_Conta_Bancária.Entities;

public class ContaBancaria {

    public String titular;
    public double saldo;
    public void depositar(double valor) {
        saldo = saldo + valor;
    }
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo = saldo - valor;
            System.out.println("Valor sacado, saldo atual: " + saldo);
        } else {
            System.out.println("Saldo insuficiente, saldo atual: " + saldo);
        }
    }


}

