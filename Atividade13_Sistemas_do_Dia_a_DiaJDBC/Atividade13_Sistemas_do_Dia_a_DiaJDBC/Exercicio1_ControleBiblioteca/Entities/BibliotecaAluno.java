package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities;

public class BibliotecaAluno extends Biblioteca {
    @Override
    public StatusEmprestimo verificarEmprestimo() {

        if (getMatricula() <= 100) {
            return StatusEmprestimo.ALUNO_NAO_ENCONTRADO;
        }

        if (isMulta()) {
            return StatusEmprestimo.BLOQUEADO_POR_MULTA;
        }

        if (getLivrosEmprestados() >= 3) {
            return StatusEmprestimo.LIMITE_ATINGIDO;
        }

        if (!isDisponivel()) {
            return StatusEmprestimo.LIVRO_INDISPONIVEL;
        }

        return StatusEmprestimo.EMPRESTIMO_REALIZADO;
    }
}
