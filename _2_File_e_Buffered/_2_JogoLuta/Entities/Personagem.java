package _2_File_e_Buffered._2_JogoLuta.Entities;

public abstract class Personagem {

    private String nome;
    private int forcaBase;

    public Personagem(String nome, int forcaBase) {
        this.nome = nome;
        this.forcaBase = forcaBase;
    }

    public String getNome() {
        return nome;
    }

    public int getForcaBase() {
        return forcaBase;
    }

    @Override
    public String toString() {
        return "Nome: " + nome
                + ", Forca: " + forcaBase;
    }
}