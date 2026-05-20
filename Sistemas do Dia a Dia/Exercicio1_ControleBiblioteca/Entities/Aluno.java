package Atividade2_Sistemas_do_Dia_a_Dia.Exercicio1_ControleBiblioteca.Entities;


    public class Aluno {
        public int Matricula;
        public String Multa;
        public int LivrosEmprestados;
        public void DadosAluno() {

            System.out.println("Matricula: " + Matricula);
            System.out.println("Multa: " + Multa);
            System.out.println("Livros emprestados: " + LivrosEmprestados);

        }
    }

