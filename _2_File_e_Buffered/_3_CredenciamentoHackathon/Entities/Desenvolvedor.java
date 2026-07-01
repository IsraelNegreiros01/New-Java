package _2_File_e_Buffered._3_CredenciamentoHackathon.Entities;

public class Desenvolvedor
        extends Participante {

    private String linguagemFavorita;

    public Desenvolvedor(String nome,
                         int idade,
                         String matricula,
                         String linguagemFavorita) {

        super(nome, idade, matricula);
        this.linguagemFavorita =
                linguagemFavorita;
    }

    @Override
    public String toString() {

        return super.toString()
                + ", Linguagem: "
                + linguagemFavorita;
    }
}
