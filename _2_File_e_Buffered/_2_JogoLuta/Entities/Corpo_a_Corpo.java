package _2_File_e_Buffered._2_JogoLuta.Entities;

public class Corpo_a_Corpo extends Personagem {

    private String arteMarcial;

    public Corpo_a_Corpo(String nome,
                         int forcaBase,
                         String arteMarcial) {

        super(nome, forcaBase);
        this.arteMarcial = arteMarcial;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Arte Marcial: "
                + arteMarcial;
    }
}