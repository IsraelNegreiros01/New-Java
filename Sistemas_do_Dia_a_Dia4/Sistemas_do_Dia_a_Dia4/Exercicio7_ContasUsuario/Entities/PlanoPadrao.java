package Sistemas_do_Dia_a_Dia4.Exercicio7_ContasUsuario.Entities;

public class PlanoPadrao extends ContaStreaming {

    private String qualidadeVideo = "Full HD";
    private int limiteTelas = 2;

    public PlanoPadrao(String nomeUsuario, String email) {

        super(nomeUsuario, email);
        setPrecoBase(30.0);
    }

    @Override
    public String toString() {

        return super.toString()
                + "\nPlano: PADRAO"
                + "\nQualidade: " + qualidadeVideo
                + "\nLimite de telas: " + limiteTelas;
    }
}