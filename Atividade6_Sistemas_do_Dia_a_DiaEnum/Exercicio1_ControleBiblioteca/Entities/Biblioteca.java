package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio1_ControleBiblioteca.Entities;

public class Biblioteca {

    private int matricula;
    public boolean multa;
    private int livrosEmprestados;
    private int codigoLivro;
    public boolean disponivel;
    private StatusEmprestimo status;

    public Biblioteca() {
    }

    public StatusEmprestimo verificarEmprestimo() {

        if (matricula != 2141) {
            return StatusEmprestimo.ALUNO_NAO_ENCONTRADO;
        }

        if (multa) {
            return StatusEmprestimo.BLOQUEADO_POR_MULTA;
        }

        if (livrosEmprestados >= 3) {
            return StatusEmprestimo.LIMITE_ATINGIDO;
        }

        if (!disponivel) {
            return StatusEmprestimo.LIVRO_INDISPONIVEL;
        }

        return StatusEmprestimo.EMPRESTIMO_REALIZADO;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public boolean isMulta() {
        return multa;
    }

    public void setMulta(boolean multa) {
        this.multa = multa;
    }

    public int getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(int livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    public int getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(int codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}