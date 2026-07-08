package db_streaming;

import db_streaming.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SistemaStreaming {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== GERENCIADOR PLATAFORMA STREAMING ===");
            System.out.println("1 - Criar Conta e Assinar Plano (INSERT)");
            System.out.println("2 - Listar Assinantes Ativos (SELECT)");
            System.out.println("3 - Atualizar E-mail do Assinante (UPDATE)");
            System.out.println("4 - Cancelar/Excluir Conta (DELETE)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    realizarNovaAssinatura(sc);
                    break;
                case 2:
                    ClassStatic.exibirRelatorioAssinaturas();
                    break;
                case 3:
                    atualizarEmailAssinante(sc);
                    break;
                case 4:
                    cancelarContaStream(sc);
                    break;
                case 0:
                    System.out.println("Encerrando o modulo de streaming. Ate logo!");
                    break;
                default:
                    System.out.println("Opcao incorreta!");
            }
        }
        sc.close();
    }

    private static void realizarNovaAssinatura(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Nome do usuario: ");
        String nome = sc.nextLine();
        System.out.print("E-mail do usuario: ");
        String email = sc.nextLine();
        System.out.println("Selecione o plano desejado:\n1 - Plano Padrao (R$ 30,00)\n2 - Plano Premium (R$ 50,00)");
        System.out.print("Opcao: ");
        int tipoPlano = Integer.parseInt(sc.nextLine());

        String nomePlanoStr = "";

        System.out.println("\n=== RECIBO DA ASSINATURA GERADO PELO MODELO ===");
        if (tipoPlano == 1) {
            ClassStatic.PlanoPadrao padrao = new ClassStatic.PlanoPadrao(nome, email);
            System.out.println(padrao.toString());
            nomePlanoStr = "Padrao";
        } else if (tipoPlano == 2) {
            ClassStatic.PlanoPremium premium = new ClassStatic.PlanoPremium(nome, email);
            System.out.println(premium.toString());
            nomePlanoStr = "Premium";
        } else {
            System.out.println("Plano invalido! Operacao abortada.");
            stmt.close();
            conn.close();
            return;
        }

        String sqlUser = "INSERT INTO usuarios (nome_usuario, email) VALUES ('" + nome + "', '" + email + "')";
        stmt.execute(sqlUser, Statement.RETURN_GENERATED_KEYS);

        ResultSet rsKeys = stmt.getGeneratedKeys();
        int idUsuarioGerado = 0;
        if (rsKeys.next()) {
            idUsuarioGerado = rsKeys.getInt(1);
        }

        String sqlAssinatura = "INSERT INTO assinaturas_ativas (id_usuario, tipo_plano) VALUES (" + idUsuarioGerado + ", '" + nomePlanoStr + "')";
        stmt.execute(sqlAssinatura);

        System.out.println("\nAssinatura vinculada e salva nas tabelas do Banco de Dados com sucesso!");

        rsKeys.close();
        stmt.close();
        conn.close();
    }

    private static void atualizarEmailAssinante(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o ID da Assinatura que deseja alterar o e-mail do usuario: ");
        int idAssinatura = Integer.parseInt(sc.nextLine());

        String queryBusca = "SELECT id_usuario FROM assinaturas_ativas WHERE id = " + idAssinatura;
        ResultSet rs = stmt.executeQuery(queryBusca);

        if (rs.next()) {
            int idUser = rs.getInt(1);
            System.out.print("Digite o NOVO e-mail para atualizacao: ");
            String novoEmail = sc.nextLine();

            String sqlUpdate = "UPDATE usuarios SET email = '" + novoEmail + "' WHERE id = " + idUser;
            stmt.executeUpdate(sqlUpdate);
            System.out.println("E-mail atualizado com sucesso na tabela de usuarios!");
        } else {
            System.out.println("Assinatura nao foi localizada.");
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    private static void cancelarContaStream(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o ID da Assinatura que deseja remover/cancelar: ");
        int idAssinatura = Integer.parseInt(sc.nextLine());

        String sqlDelete = "DELETE FROM assinaturas_ativas WHERE id = " + idAssinatura;
        int linhasModificadas = stmt.executeUpdate(sqlDelete);

        if (linhasModificadas > 0) {
            System.out.println("Assinatura excluida e cancelada com sucesso do Banco de Dados!");
        } else {
            System.out.println("Registro de assinatura nao encontrado.");
        }

        stmt.close();
        conn.close();
    }
}
