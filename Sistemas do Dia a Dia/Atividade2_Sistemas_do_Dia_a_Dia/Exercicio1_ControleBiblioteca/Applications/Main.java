package Atividade2_Sistemas_do_Dia_a_Dia.Exercicio1_ControleBiblioteca.Applications;
import   Atividade2_Sistemas_do_Dia_a_Dia.Exercicio1_ControleBiblioteca.Entities.Aluno;
import Atividade2_Sistemas_do_Dia_a_Dia.Exercicio1_ControleBiblioteca.Entities.Livro;

import java.util.Random;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Random random = new Random();
            Aluno aluno = new Aluno();
            Livro livro = new Livro();
            aluno.Matricula = random.nextInt(4  ) + 1;
            aluno.LivrosEmprestados = random.nextInt(4);
            if (random.nextInt(2) == 0) {
                aluno.Multa = "Nao";
            } else {
                aluno.Multa = "Sim";
            }
            livro.Codigo = random.nextInt(9) + 1;
            if (random.nextInt(2) == 0) {
                livro.Disponibilidade = "Disponivel";
            } else {
                livro.Disponibilidade = "Indisponivel";
            }
            System.out.println("Digite a matricula:");
            int matricula = sc.nextInt();
            if (matricula != aluno.Matricula) {
                System.out.println("Aluno nao encontrado.");
            }
            else if (aluno.Multa.equals("Sim")) {
                System.out.println("Emprestimo bloqueado por multa.");
            }
            else if (aluno.LivrosEmprestados >= 3) {
                System.out.println("Limite de emprestimos atingido.");
            }
            else {
                System.out.println("Digite o codigo do livro:");
                int codigo = sc.nextInt();
                if (codigo != livro.Codigo ||
                        livro.Disponibilidade.equals("Indisponivel")) {
                    System.out.println("Livro indisponivel.");
                }
                else {
                    aluno.LivrosEmprestados++;
                    System.out.println("Emprestimo realizado com sucesso.");
                }
            }
            System.out.println("Dados do aluno e do livro:");
            aluno.DadosAluno();
            livro.DadosLivro();
            sc.close();
        }
    }


