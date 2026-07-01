package _2_File_e_Buffered._2_JogoLuta.Entities;

public class Atirador extends Personagem {

    private String armaPrincipal;

    public Atirador(String nome,
                    int forcaBase,
                    String armaPrincipal) {

        super(nome, forcaBase);
        this.armaPrincipal = armaPrincipal;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Arma: "
                + armaPrincipal;
    }
}