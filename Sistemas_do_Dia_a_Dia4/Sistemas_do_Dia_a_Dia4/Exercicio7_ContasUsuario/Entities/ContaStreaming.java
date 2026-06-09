package Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Entities;

public class ContaStreaming {

    private String nomeUsuario;
    private String email;
    private double precoBase;

    public ContaStreaming(String nomeUsuario, String email) {

        this.nomeUsuario = nomeUsuario;
        this.email = email;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    @Override
    public String toString() {

        return "Nome: "
                + nomeUsuario
                + "\nEmail: "
                + email
                + "\nPreco: R$ "
                + String.format("%.2f", precoBase);
    }
}