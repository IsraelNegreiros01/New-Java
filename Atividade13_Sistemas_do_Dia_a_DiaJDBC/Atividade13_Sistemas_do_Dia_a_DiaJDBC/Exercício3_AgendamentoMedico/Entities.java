package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercício3_AgendamentoMedico;

import Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio3_AgendamentoMedico.application.application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Entities {




    static class ClassPessoa {
        private int codigo;
        private String nome;

        public ClassPessoa(int codigo, String nome) {
            this.codigo = codigo;
            this.nome = nome;
        }

        public int getCodigo() { return codigo; }
        public String getNome() { return nome; }
    }

    static class ClassContato {
        private int codigo;
        private String tipo;
        private String valor;
        private int pessoaCodigo;

        public ClassContato(int codigo, String tipo, String valor, int pessoaCodigo) {
            this.codigo = codigo;
            this.tipo = tipo;
            this.valor = valor;
            this.pessoaCodigo = pessoaCodigo;
        }

        public String getTipo() { return tipo; }
        public String getValor() { return valor; }
    }

    static class ClassEndereco {
        private int codigo;
        private String logradouro;
        private String bairro;
        private String cidade;
        private String estado;
        private int pessoaCodigo;

        public ClassEndereco(int codigo, String logradouro, String bairro, String cidade, String estado, int pessoaCodigo) {
            this.codigo = codigo;
            this.logradouro = logradouro;
            this.bairro = bairro;
            this.cidade = cidade;
            this.estado = estado;
            this.pessoaCodigo = pessoaCodigo;
        }

        public String getResumo() {
            return logradouro + ", " + bairro + " - " + cidade + "/" + estado;
        }
    }



    class ClassConnection {
        public static Connection getConnection() throws SQLException {
            try {
                String url = "jdbc:mysql://localhost:3306/java_tarde_jdbc";
                String user = "root";
                String password = "";
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
            }
        }
    }



    public class Program {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);


            System.out.println("=== CADASTRO COMPLETO (PESSOA, CONTATO E ENDEREÇO) ===");
            System.out.print("Nome da Pessoa: ");
            String nome = sc.nextLine();

            System.out.print("Tipo de Contato (ex: Telefone/Email): ");
            String tipoContato = sc.nextLine();
            System.out.print("Valor do Contato: ");
            String valorContato = sc.nextLine();

            System.out.print("Rua/Logradouro: ");
            String logradouro = sc.nextLine();
            System.out.print("Bairro: ");
            String bairro = sc.nextLine();
            System.out.print("Cidade: ");
            String cidade = sc.nextLine();
            System.out.print("Estado (UF - 2 letras): ");
            String estado = sc.nextLine();

            Connection conn = null;
            PreparedStatement stmtPessoa = null;
            PreparedStatement stmtContato = null;
            PreparedStatement stmtEndereco = null;
            PreparedStatement stmtSelect = null;
            ResultSet rsKeys = null;
            ResultSet rsConsulta = null;

            try {
                conn = ClassConnection.getConnection();


                String sqlPessoa = "INSERT INTO pessoas (nome) VALUES (?)";
                stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
                stmtPessoa.setString(1, nome);
                stmtPessoa.executeUpdate();

                int idPessoaGerado = 0;
                rsKeys = stmtPessoa.getGeneratedKeys();
                if (rsKeys.next()) {
                    idPessoaGerado = rsKeys.getInt(1);
                }

                System.out.println("[Sucesso] Pessoa cadastrada com ID: " + idPessoaGerado);


                String sqlContato = "INSERT INTO contatos (tipo, valor, pessoa_codigo) VALUES (?, ?, ?)";
                stmtContato = conn.prepareStatement(sqlContato);
                stmtContato.setString(1, tipoContato);
                stmtContato.setString(2, valorContato);
                stmtContato.setInt(3, idPessoaGerado);
                stmtContato.executeUpdate();
                System.out.println("[Sucesso] Contato associado de forma segura.");


                String sqlEndereco = "INSERT INTO enderecos (logradouro, bairro, cidade, estado, pessoa_codigo) VALUES (?, ?, ?, ?, ?)";
                stmtEndereco = conn.prepareStatement(sqlEndereco);
                stmtEndereco.setString(1, logradouro);
                stmtEndereco.setString(2, bairro);
                stmtEndereco.setString(3, cidade);
                stmtEndereco.setString(4, estado);
                stmtEndereco.setInt(5, idPessoaGerado);
                stmtEndereco.executeUpdate();
                System.out.println("[Sucesso] Endereço associado de forma segura.");



                System.out.println("LISTA ATUALIZADA DE REGISTROS NO BANCO DE DADOS:");


                String sqlSelectCompleto =
                        "SELECT p.codigo AS p_cod, p.nome AS p_nome, " +
                                "       c.codigo AS c_cod, c.tipo AS c_tipo, c.valor AS c_valor, " +
                                "       e.codigo AS e_cod, e.logradouro, e.bairro, e.cidade, e.estado " +
                                "FROM pessoas p " +
                                "LEFT JOIN contatos c ON p.codigo = c.pessoa_codigo " +
                                "LEFT JOIN enderecos e ON p.codigo = e.pessoa_codigo";

                stmtSelect = conn.prepareStatement(sqlSelectCompleto);
                rsConsulta = stmtSelect.executeQuery();

                while (rsConsulta.next()) {

                    ClassPessoa p = new ClassPessoa(rsConsulta.getInt("p_cod"), rsConsulta.getString("p_nome"));
                    ClassContato cont = new ClassContato(rsConsulta.getInt("c_cod"), rsConsulta.getString("c_tipo"), rsConsulta.getString("c_valor"), p.getCodigo());
                    ClassEndereco end = new ClassEndereco(rsConsulta.getInt("e_cod"), rsConsulta.getString("logradouro"), rsConsulta.getString("bairro"), rsConsulta.getString("cidade"), rsConsulta.getString("estado"), p.getCodigo());


                    System.out.println("ID: " + p.getCodigo() + " | Nome: " + p.getNome());
                    System.out.println("   - Contato [" + cont.getTipo() + "]: " + cont.getValor());
                    System.out.println("   - Endereço: " + end.getResumo());

                }

            } catch (SQLException e) {
                System.out.println("Ocorreu um erro operacional com o Banco de Dados: " + e.getMessage());
            } finally {

                try {
                    if (rsConsulta != null) rsConsulta.close();
                    if (rsKeys != null) rsKeys.close();
                    if (stmtSelect != null) stmtSelect.close();
                    if (stmtPessoa != null) stmtPessoa.close();
                    if (stmtContato != null) stmtContato.close();
                    if (stmtEndereco != null) stmtEndereco.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sc.close();
            }
        }
    }
}
