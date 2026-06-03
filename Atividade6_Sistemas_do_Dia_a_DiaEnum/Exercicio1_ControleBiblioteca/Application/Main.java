package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio1_ControleBiblioteca.Application;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio1_ControleBiblioteca.Entities.Biblioteca;
import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio1_ControleBiblioteca.Entities.StatusEmprestimo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Biblioteca aluno = new Biblioteca();

        System.out.print("Informe a matrícula: ");
        aluno.setMatricula(sc.nextInt());

        if (aluno.getMatricula() == 2141 ) {

            String respostaMulta;

            do {

                System.out.print("Possui multa pendente? (sim/nao): ");
                respostaMulta = sc.next();

                if (!respostaMulta.equalsIgnoreCase("sim")
                        && !respostaMulta.equalsIgnoreCase("nao")) {

                    System.out.println("Digite apenas sim ou nao.");
                }

            } while (!respostaMulta.equalsIgnoreCase("sim")
                    && !respostaMulta.equalsIgnoreCase("nao"));

            aluno.multa = respostaMulta.equalsIgnoreCase("sim");

            if (!aluno.isMulta()) {

                System.out.print("Quantidade de livros emprestados: ");
                aluno.setLivrosEmprestados(sc.nextInt());

                if (aluno.getLivrosEmprestados() < 5) {

                    System.out.print("Código do livro: ");
                    aluno.setCodigoLivro(sc.nextInt());

                    String respostaLivro;

                    do {

                        System.out.print("Livro disponivel? (sim/nao): ");
                        respostaLivro = sc.next();

                        if (!respostaLivro.equalsIgnoreCase("sim")
                                && !respostaLivro.equalsIgnoreCase("nao")) {

                        }

                    } while (!respostaLivro.equalsIgnoreCase("sim")
                            && !respostaLivro.equalsIgnoreCase("nao"));

                    aluno.disponivel = respostaLivro.equalsIgnoreCase("sim");
                }
            }
        }

        StatusEmprestimo status = aluno.verificarEmprestimo();

        switch (status) {

            case ALUNO_NAO_ENCONTRADO:
                System.out.println("Aluno não encontrado.");
                break;

            case BLOQUEADO_POR_MULTA:
                System.out.println("Empréstimo bloqueado por multa.");
                break;

            case LIMITE_ATINGIDO:
                System.out.println("Limite de empréstimos atingido.");
                break;

            case LIVRO_INDISPONIVEL:
                System.out.println("Livro indisponível.");
                break;

            case EMPRESTIMO_REALIZADO:
                System.out.println("Empréstimo realizado com sucesso.");
                break;
        }

        sc.close();
    }
}