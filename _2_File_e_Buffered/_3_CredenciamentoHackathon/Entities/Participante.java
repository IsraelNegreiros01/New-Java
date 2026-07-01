package _2_File_e_Buffered._3_CredenciamentoHackathon.Entities;

public abstract class Participante {

    private String nome;
    private int idade;
    private String matricula;

    public Participante(String nome,
                        int idade,
                        String matricula) {

        this.nome = nome;
        setIdade(idade);
        this.matricula = matricula;
    }

    public void setIdade(int idade) {

        if (idade < 14 || idade > 21) {
            throw new IllegalArgumentException(
                    "Idade invalida");
        }

        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public String toString() {

        return "Nome: " + nome
                + ", Idade: " + idade
                + ", Matricula: "
                + matricula;
    }
}
