package db_hotel;

import db_hotel.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class SistemaHotel {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== SISTEMA DE RESERVAS DE HOTEL ===");
            System.out.println("1 - Realizar Pesquisa e Nova Reserva (INSERT / SELECT)");
            System.out.println("2 - Listar Reservas Registradas (SELECT)");
            System.out.println("3 - Atualizar Quantidade de Hospedes (UPDATE)");
            System.out.println("4 - Cancelar/Excluir Reserva (DELETE)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    fazerReserva(sc);
                    break;
                case 2:
                    ClassStatic.exibirTodasAsReservas();
                    break;
                case 3:
                    atualizarReserva(sc);
                    break;
                case 4:
                    deletarReserva(sc);
                    break;
                case 0:
                    System.out.println("Finalizando o programa...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
        sc.close();
    }

    private static void fazerReserva(Scanner sc) throws SQLException {
        Connection connection = ClassConnection.getConnection();
        Statement stmt = connection.createStatement();

        System.out.print("Informe a cidade desejada: ");
        String cidade = sc.nextLine();
        System.out.print("Data de check-in (AAAA-MM-DD): ");
        String checkinStr = sc.nextLine();
        System.out.print("Data de check-out (AAAA-MM-DD): ");
        String checkoutStr = sc.nextLine();
        System.out.print("Quantidade de hospedes: ");
        int qtdHospedes = Integer.parseInt(sc.nextLine());

        LocalDate checkin = LocalDate.parse(checkinStr);
        LocalDate checkout = LocalDate.parse(checkoutStr);
        long diarias = ChronoUnit.DAYS.between(checkin, checkout);

        String sqlQuartos = "SELECT * FROM hoteis_quartos WHERE cidade = '" + cidade + "' AND disponivel = 'SIM'";
        ResultSet rsQuartos = stmt.executeQuery(sqlQuartos);

        if (!rsQuartos.isBeforeFirst()) {
            System.out.println("Nenhum quarto disponivel.");
            rsQuartos.close();
            stmt.close();
            connection.close();
            return;
        }

        System.out.println("\nLista de quartos disponiveis encontrados:");
        while (rsQuartos.next()) {
            System.out.println("ID Quarto: " + rsQuartos.getInt("id") +
                    " | N° Quarto: " + rsQuartos.getString("numero_quarto") +
                    " | Valor Diaria: R$ " + rsQuartos.getDouble("preco_diaria"));
        }

        System.out.print("\nEscolha e digite o ID do quarto desejado: ");
        int idQuartoEscolhido = Integer.parseInt(sc.nextLine());

        String sqlPreco = "SELECT preco_diaria FROM hoteis_quartos WHERE id = " + idQuartoEscolhido;
        ResultSet rsPreco = stmt.executeQuery(sqlPreco);
        double precoDiaria = 0;
        if (rsPreco.next()) {
            precoDiaria = rsPreco.getDouble("preco_diaria");
        }

        double valorTotal = ClassStatic.calcularValorTotal(precoDiaria, diarias);
        System.out.printf("Valor total calculado: R$ %.2f (%d diarias)\n", valorTotal, diarias);

        System.out.print("Digite seu nome para registro: ");
        String nomeCliente = sc.nextLine();
        System.out.print("Digite seu e-mail: ");
        String emailCliente = sc.nextLine();

        String sqlCliente = "INSERT INTO clientes (nome, email) VALUES ('" + nomeCliente + "', '" + emailCliente + "')";
        stmt.execute(sqlCliente);

        ResultSet rsIdCli = stmt.executeQuery("SELECT LAST_INSERT_ID()");
        int idCliente = 0;
        if (rsIdCli.next()) {
            idCliente = rsIdCli.getInt(1);
        }

        System.out.print("Escolha a forma de pagamento (Cartao/Pix/Dinheiro): ");
        String formaPagamento = sc.nextLine();
        System.out.print("Simular validacao do pagamento (A - Aprovado / R - Recusado): ");
        String simulaValidacao = sc.nextLine();

        if (simulaValidacao.equalsIgnoreCase("R")) {
            System.out.println("Pagamento recusado.");
            rsQuartos.close();
            rsPreco.close();
            rsIdCli.close();
            stmt.close();
            connection.close();
            return;
        }

        String sqlReserva = "INSERT INTO reservas (id_cliente, id_quarto, data_checkin, data_checkout, qtd_hospedes, valor_total, forma_pagamento, status_pagamento) " +
                "VALUES (" + idCliente + ", " + idQuartoEscolhido + ", '" + checkinStr + "', '" + checkoutStr + "', " + qtdHospedes + ", " + valorTotal + ", '" + formaPagamento + "', 'Aprovado')";
        stmt.execute(sqlReserva);

        stmt.execute("UPDATE hoteis_quartos SET disponivel = 'NAO' WHERE id = " + idQuartoEscolhido);

        System.out.println("Reserva confirmada!");
        System.out.println("Confirmacao enviada para: " + emailCliente);

        rsQuartos.close();
        rsPreco.close();
        rsIdCli.close();
        stmt.close();
        connection.close();
    }

    private static void atualizarReserva(Scanner sc) throws SQLException {
        Connection connection = ClassConnection.getConnection();
        Statement stmt = connection.createStatement();

        System.out.print("Informe o ID da reserva que deseja atualizar: ");
        int idReserva = Integer.parseInt(sc.nextLine());
        System.out.print("Informe a nova quantidade de hospedes: ");
        int novosHospedes = Integer.parseInt(sc.nextLine());

        String sql = "UPDATE reservas SET qtd_hospedes = " + novosHospedes + " WHERE id = " + idReserva;
        int linhasAfetadas = stmt.executeUpdate(sql);

        if (linhasAfetadas > 0) {
            System.out.println("Quantidade de hospedes atualizada com sucesso!");
        } else {
            System.out.println("Reserva nao localizada.");
        }

        stmt.close();
        connection.close();
    }

    private static void deletarReserva(Scanner sc) throws SQLException {
        Connection connection = ClassConnection.getConnection();
        Statement stmt = connection.createStatement();

        System.out.print("Informe o ID da reserva que deseja cancelar/excluir: ");
        int idReserva = Integer.parseInt(sc.nextLine());

        String sqlBuscaQuarto = "SELECT id_quarto FROM reservas WHERE id = " + idReserva;
        ResultSet rs = stmt.executeQuery(sqlBuscaQuarto);
        if (rs.next()) {
            int idQuarto = rs.getInt(1);
            stmt.execute("UPDATE hoteis_quartos SET disponivel = 'SIM' WHERE id = " + idQuarto);
        }
        rs.close();

        String sqlDelete = "DELETE FROM reservas WHERE id = " + idReserva;
        int linhasAfetadas = stmt.executeUpdate(sqlDelete);

        if (linhasAfetadas > 0) {
            System.out.println("Reserva excluida com sucesso!");
        } else {
            System.out.println("Reserva nao localizada.");
        }

        stmt.close();
        connection.close();
    }
}
