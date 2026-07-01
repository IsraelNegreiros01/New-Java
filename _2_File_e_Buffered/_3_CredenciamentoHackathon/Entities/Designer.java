package _2_File_e_Buffered._3_CredenciamentoHackathon.Entities;

public class Designer
        extends Participante {

    private String ferramentaVisual;

    public Designer(String nome,
                    int idade,
                    String matricula,
                    String ferramentaVisual) {

        super(nome, idade, matricula);
        this.ferramentaVisual =
                ferramentaVisual;
    }

    @Override
    public String toString() {

        return super.toString()
                + ", Ferramenta: "
                + ferramentaVisual;
    }
}