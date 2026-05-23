package Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio1_ControleBiblioteca.Applications;
import java.util.Scanner;
import java.util.Random;
import Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio1_ControleBiblioteca.Entities.Aluno;
import Atividade4_Sistemas_do_Dia_a_Dia_refatorado.Exercicio1_ControleBiblioteca.Entities.Livro;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        Aluno aluno = new Aluno();
        Livro livro = new Livro();
        aluno.setMatricula(random.nextInt(2) + 1);
        aluno.setLivrosEmprestados(random.nextInt(4));
        aluno.setMulta(random.nextInt(2) == 0 ? "Nao" : "Sim");
        livro.setCodigo(random.nextInt(3) + 1);
        livro.setDisponibilidade(random.nextInt(2) == 0 ? "Disponivel" : "Indisponivel");
        System.out.println("Digite a matricula:");
        int matricula = sc.nextInt();
        if (matricula != aluno.getMatricula()) {
            System.out.println("Aluno nao encontrado.");
        } else if (aluno.getMulta().equals("Sim")) {
            System.out.println("Emprestimo bloqueado por multa.");
        } else if (aluno.getLivrosEmprestados() >= 3) {
            System.out.println("Limite de emprestimos atingido.");
        } else {
            System.out.println("Digite o codigo do livro:");
            int codigo = sc.nextInt();

            if (codigo != livro.getCodigo() || livro.getDisponibilidade().equals("Indisponivel")) {
                System.out.println("Livro indisponivel.");
            } else {
                aluno.setLivrosEmprestados(aluno.getLivrosEmprestados() + 1);
                System.out.println("Emprestimo realizado com sucesso.");
            }
        }
        aluno.DadosAluno();
        livro.DadosLivro();
        sc.close();
    }
}



