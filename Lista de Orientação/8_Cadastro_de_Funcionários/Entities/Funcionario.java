package Lista_de_Orientação_a_Objetos._8_Cadastro_de_Funcionários.Entities;

public class Funcionario {

    public String nome;
    public String sobrenome;
    public double salarioMensal;

    public void darAumento(double porcentagem) {
        // Aumenta o salário: salario = salario + (salario * porcentagem / 100)
        salarioMensal += salarioMensal * (porcentagem / 100.0);
    }

    public double salarioAnual() {
        return salarioMensal * 12;
    }
}