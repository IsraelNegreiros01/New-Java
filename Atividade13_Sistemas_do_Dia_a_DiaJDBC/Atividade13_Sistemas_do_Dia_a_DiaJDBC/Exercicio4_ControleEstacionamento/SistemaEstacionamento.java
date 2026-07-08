package Exercício13_Controle_de_estacionamento;

import Exercício13_Controle_de_estacionamento.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SistemaEstacionamento {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== AUTOMAÇÃO DE ESTACIONAMENTO ===");
            System.out.println("1 - Registrar Entrada de Veiculo (INSERT)");
            System.out.println("2 - Registrar Saida e Pagamento (SELECT / INSERT)");
            System.out.println("3 - Alterar Placa do Veiculo (UPDATE)");
            System.out.println("4 - Excluir Registro do Banco (DELETE)");
            System.out.println("5 - Listar Movimentacao Geral (SELECT)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    registrarEntrada(sc);
                    break;
                case 2:
                    registrarSaida(sc);
                    break;
                case 3:
                    alterarPlaca(sc);
                    break;
                case 4:
                    excluirRegistro(sc);
                    break;
                case 5:
                    ClassStatic.exibirVeiculosEstacionados();
                    break;
                case 0:
                    System.out.println("Encerrando o modulo do estacionamento.");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
        sc.close();
    }

    private static void registrarEntrada(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlVagas = "SELECT total_vagas, vagas_ocupadas FROM controle_vagas WHERE id = 1";
        ResultSet rs = stmt.executeQuery(sqlVagas);
        if (rs.next()) {
            int total = rs.getInt("total_vagas");
            int ocupadas = rs.getInt("vagas_ocupadas");

            if (ocupadas >= total) {
                System.out.println("Estacionamento lotado.");
                rs.close();
                stmt.close();
                conn.close();
                return;
            }
        }

        System.out.print("Informe a placa do veiculo: ");
        String placa = sc.nextLine().toUpperCase();
        System.out.println("Selecione o tipo do veiculo:\n1 - Carro\n2 - Moto");
        System.out.print("Opcao: ");
        int tipoOpcao = Integer.parseInt(sc.nextLine());
        String tipoVeiculo = (tipoOpcao == 1) ? "Carro" : "Moto";

        String sqlInsert = "INSERT INTO veiculos_estacionados (placa, tipo_veiculo) VALUES ('" + placa + "', '" + tipoVeiculo + "')";
        stmt.executeUpdate(sqlInsert);

        String sqlUpdateVagas = "UPDATE controle_vagas SET vagas_ocupadas = vagas_ocupadas + 1 WHERE id = 1";
        stmt.executeUpdate(sqlUpdateVagas);

        System.out.println("Entrada registrada com sucesso! Horario de entrada salvo.");

        rs.close();
        stmt.close();
        conn.close();
    }

    private static void registrarSaida(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe a placa para a baixa da saida: ");
        String placa = sc.nextLine().toUpperCase();

        String sqlBusca = "SELECT id, tipo_veiculo FROM veiculos_estacionados WHERE placa = '" + placa + "' AND status_veiculo = 'Estacionado'";
        ResultSet rs = stmt.executeQuery(sqlBusca);

        if (rs.next()) {
            int idVeiculo = rs.getInt("id");

            System.out.print("Informe o tempo que o veiculo permaneceu estacionado (em horas cheias): ");
            int horas = Integer.parseInt(sc.nextLine());

            double valorTotal = ClassStatic.TarifaEstacionamento.calcularValor(horas);
            System.out.printf("Valor total a pagar: R$ %.2f%n", valorTotal);

            System.out.print("Informe a forma de pagamento (Dinheiro/Cartao/Pix): ");
            String formaPagto = sc.nextLine();

            System.out.println("Processando pagamento...\n1 - Aprovar Pagamento\n2 - Recusar Pagamento");
            System.out.print("Simular retorno da operadora: ");
            int simulacao = Integer.parseInt(sc.nextLine());

            if (simulacao == 2) {
                System.out.println("Pagamento não autorizado.");
            } else {
                String sqlPagamento = "INSERT INTO pagamentos_estacionamento (id_veiculo, tempo_horas, valor_pago, forma_pagamento) " +
                        "VALUES (" + idVeiculo + ", " + horas + ", " + valorTotal + ", '" + formaPagto + "')";
                stmt.executeUpdate(sqlPagamento);

                String sqlAtualizaStatus = "UPDATE veiculos_estacionados SET status_veiculo = 'Liberado' WHERE id = " + idVeiculo;
                stmt.executeUpdate(sqlAtualizaStatus);

                String sqlLiberaVaga = "UPDATE controle_vagas SET vagas_ocupadas = vagas_ocupadas - 1 WHERE id = 1";
                stmt.executeUpdate(sqlLiberaVaga);

                System.out.println("Saída liberada.");
            }
        } else {
            System.out.println("Veiculo nao localizado ou ja liberado no sistema.");
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    private static void alterarPlaca(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o ID do registro que deseja alterar a placa: ");
        int idRegistro = Integer.parseInt(sc.nextLine());

        System.out.print("Digite a NOVA placa corrigida: ");
        String novaPlaca = sc.nextLine().toUpperCase();

        String sqlUpdate = "UPDATE veiculos_estacionados SET placa = '" + novaPlaca + "' WHERE id = " + idRegistro;
        int linhasModificadas = stmt.executeUpdate(sqlUpdate);

        if (linhasModificadas > 0) {
            System.out.println("Placa do veiculo atualizada com sucesso!");
        } else {
            System.out.println("Registro nao encontrado.");
        }

        stmt.close();
        conn.close();
    }

    private static void excluirRegistro(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o ID do veiculo que deseja remover definitivamente do banco: ");
        int idRegistro = Integer.parseInt(sc.nextLine());

        // Deletando o veículo (As chaves estrangeiras com CASCADE limpam os registros vinculados de pagamento)
        String sqlDelete = "DELETE FROM veiculos_estacionados WHERE id = " + idRegistro;
        int linhasModificadas = stmt.executeUpdate(sqlDelete);

        if (linhasModificadas > 0) {
            System.out.println("Registro deletado e purgado do histórico com sucesso!");
        } else {
            System.out.println("Nenhum registro encontrado para o ID especificado.");
        }

        stmt.close();
        conn.close();
    }
}
