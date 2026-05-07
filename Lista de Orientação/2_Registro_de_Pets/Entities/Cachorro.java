package Lista_de_Orientação_a_Objetos._2_Registro_de_Pets.Entities;

public class Cachorro {

    public String nome;
    public String raca;
    public int idade;

    public void latir() {
        System.out.println("O " + nome + " de raça " + raca + " está latindo: Au Au!");
    }
}
