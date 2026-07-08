package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Application;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DAO.BibliotecaDAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DAO.EmprestimoDAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DAO.LivroDAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities.*;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BibliotecaDAO alunoDAO = new BibliotecaDAO();
        LivroDAO livroDAO = new LivroDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        Biblioteca aluno = new BibliotecaAluno();
        try {
            System.out.println("===== CONTROLE DA BIBLIOTECA =====");
            System.out.println("Escolha a tabela:");
            System.out.println("1 - Alunos");
            System.out.println("2 - Livros");
            System.out.println("3 - Empréstimos");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            int tabela = sc.nextInt();
            if (tabela == 4) {
                System.out.println("Programa encerrado.");
                return;
            }
            System.out.println();
            System.out.println("Escolha a operação:");
            System.out.println("1 - Inserir");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("5 - Sair");
            System.out.print("Opção: ");
            int operacao = sc.nextInt();
            if (tabela == 5) {
                System.out.println("Programa encerrado.");
                return;
            }
            System.out.println();
            switch (tabela) {
                case 1:
                    switch (operacao) {
                        case 1:
                            System.out.print("Matrícula: ");
                            aluno.setMatricula(sc.nextInt());
                            System.out.print("Possui multa? (true/false): ");
                            aluno.setMulta(sc.nextBoolean());
                            System.out.print("Quantidade de livros emprestados: ");
                            aluno.setLivrosEmprestados(sc.nextInt());
                            alunoDAO.inserirAluno(aluno);
                            System.out.println("Aluno cadastrado com sucesso.");
                            break;
                        case 2:
                            alunoDAO.listarAlunos();
                            break;
                        case 3:
                            System.out.print("Matrícula: ");
                            aluno.setMatricula(sc.nextInt());
                            System.out.print("Possui multa? (true/false): ");
                            aluno.setMulta(sc.nextBoolean());
                            System.out.print("Quantidade de livros emprestados: ");
                            aluno.setLivrosEmprestados(sc.nextInt());
                            alunoDAO.atualizarAluno(aluno);
                            System.out.println("Aluno atualizado.");
                            break;
                        case 4:
                            System.out.print("Informe a matrícula: ");
                            int matricula = sc.nextInt();
                            alunoDAO.deletarAluno(matricula);
                            System.out.println("Aluno removido.");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
                case 2:
                    switch (operacao) {
                        case 1:
                            Livro livro = new Livro();
                            System.out.print("Código do livro: ");
                            livro.setCodigoLivro(sc.nextInt());
                            System.out.print("Livro disponível? (true/false): ");
                            livro.setDisponivel(sc.nextBoolean());
                            livroDAO.inserirLivro(livro);
                            System.out.println("Livro cadastrado.");
                            break;
                        case 2:
                            livroDAO.listarLivros();
                            break;
                        case 3:
                            Livro livroAtualizar = new Livro();
                            System.out.print("Código do livro: ");
                            livroAtualizar.setCodigoLivro(sc.nextInt());
                            System.out.print("Livro disponível? (true/false): ");
                            livroAtualizar.setDisponivel(sc.nextBoolean());
                            livroDAO.atualizarLivro(livroAtualizar);
                            System.out.println("Livro atualizado.");
                            break;
                        case 4:
                            System.out.print("Código do livro: ");
                            int codigoLivro = sc.nextInt();
                            livroDAO.deletarLivro(codigoLivro);
                            System.out.println("Livro removido.");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
                case 3:
                    switch (operacao) {
                        case 1:
                            Emprestimo emprestimo = new Emprestimo();
                            System.out.print("Matrícula: ");
                            emprestimo.setMatricula(sc.nextInt());
                            System.out.print("Código do livro: ");
                            emprestimo.setCodigoLivro(sc.nextInt());
                            System.out.println("1 - EMPRESTIMO_REALIZADO");
                            System.out.println("2 - LIVRO_INDISPONIVEL");
                            System.out.println("3 - LIMITE_ATINGIDO");
                            System.out.println("4 - BLOQUEADO_POR_MULTA");
                            System.out.println("5 - ALUNO_NAO_ENCONTRADO");
                            int status = sc.nextInt();
                            switch (status) {
                                case 1:
                                    emprestimo.setStatus(StatusEmprestimo.EMPRESTIMO_REALIZADO);
                                    break;
                                case 2:
                                    emprestimo.setStatus(StatusEmprestimo.LIVRO_INDISPONIVEL);
                                    break;
                                case 3:
                                    emprestimo.setStatus(StatusEmprestimo.LIMITE_ATINGIDO);
                                    break;
                                case 4:
                                    emprestimo.setStatus(StatusEmprestimo.BLOQUEADO_POR_MULTA);
                                    break;
                                case 5:
                                    emprestimo.setStatus(StatusEmprestimo.ALUNO_NAO_ENCONTRADO);
                                    break;
                            }
                            emprestimoDAO.inserirEmprestimo(emprestimo);
                            System.out.println("Empréstimo cadastrado.");
                            break;
                        case 2:
                            emprestimoDAO.listarEmprestimos();
                            break;
                        case 3:
                            Emprestimo atualizar = new Emprestimo();
                            System.out.print("ID: ");
                            atualizar.setId(sc.nextInt());
                            System.out.print("Matrícula: ");
                            atualizar.setMatricula(sc.nextInt());
                            System.out.print("Código do livro: ");
                            atualizar.setCodigoLivro(sc.nextInt());
                            System.out.println("1 - EMPRESTIMO_REALIZADO");
                            System.out.println("2 - LIVRO_INDISPONIVEL");
                            System.out.println("3 - LIMITE_ATINGIDO");
                            System.out.println("4 - BLOQUEADO_POR_MULTA");
                            System.out.println("5 - ALUNO_NAO_ENCONTRADO");
                            status = sc.nextInt();
                            switch (status) {
                                case 1:
                                    atualizar.setStatus(StatusEmprestimo.EMPRESTIMO_REALIZADO);
                                    break;
                                case 2:
                                    atualizar.setStatus(StatusEmprestimo.LIVRO_INDISPONIVEL);
                                    break;
                                case 3:
                                    atualizar.setStatus(StatusEmprestimo.LIMITE_ATINGIDO);
                                    break;
                                case 4:
                                    atualizar.setStatus(StatusEmprestimo.BLOQUEADO_POR_MULTA);
                                    break;
                                case 5:
                                    atualizar.setStatus(StatusEmprestimo.ALUNO_NAO_ENCONTRADO);
                                    break;
                            }
                            emprestimoDAO.atualizarEmprestimo(atualizar);
                            System.out.println("Empréstimo atualizado.");
                            break;
                        case 4:
                            System.out.print("ID do empréstimo: ");
                            int id = sc.nextInt();
                            emprestimoDAO.deletarEmprestimo(id);
                            System.out.println("Empréstimo removido.");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
                default:
                    System.out.println("Tabela inválida.");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}