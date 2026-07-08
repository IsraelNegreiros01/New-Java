package Exercício13_Sistema_de_Delivery;

import Exercício13_Sistema_de_Delivery.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SistemaDelivery {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("SISTEMA DE DELIVERY ONLINE ");
            System.out.println("1 - Realizar Login e Fazer Pedido (INSERT)");
            System.out.println("2 - Visualizar Todos os Pedidos (SELECT)");
            System.out.println("3 - Atualizar Status do Pedido (UPDATE)");
            System.out.println("4 - Cancelar/Excluir Pedido (DELETE)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    fluxoLoginEPedido(sc);
                    break;
                case 2:
                    ClassStatic.exibirRelatorioPedidos();
                    break;
                case 3:
                    atualizarStatusPedido(sc);
                    break;
                case 4:
                    cancelarPedido(sc);
                    break;
                case 0:
                    System.out.println("Saindo do sistema de delivery...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }

    private static void fluxoLoginEPedido(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.println(" ÁREA DE LOGIN ");
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String sqlLogin = "SELECT id, nome FROM clientes WHERE email = '" + email + "' AND senha = '" + senha + "'";
        ResultSet rsLogin = stmt.executeQuery(sqlLogin);

        if (!rsLogin.next()) {
            System.out.println("Usuário ou senha inválidos.");
            rsLogin.close();
            stmt.close();
            conn.close();
            return;
        }

        int idCliente = rsLogin.getInt("id");
        String nomeCliente = rsLogin.getString("nome");
        System.out.println("Login efetuado com sucesso! Bem-vindo(a), " + nomeCliente);

        System.out.println("=== CARDÁPIO DISPONÍVEL ===");
        String sqlCardapio = "SELECT id, nome_produto, preco FROM cardapio";
        Statement stmtCardapio = conn.createStatement();
        ResultSet rsCardapio = stmtCardapio.executeQuery(sqlCardapio);
        while (rsCardapio.next()) {
            System.out.println("Cód: " + rsCardapio.getInt("id") +
                    " | " + rsCardapio.getString("nome_produto") +
                    " - R$ " + rsCardapio.getDouble("preco"));
        }

        System.out.print("Digite o código do produto que deseja pedir: ");
        int codProduto = Integer.parseInt(sc.nextLine());

        double valorItens = 0.0;
        String sqlBuscaProd = "SELECT preco FROM cardapio WHERE id = " + codProduto;
        ResultSet rsProd = stmt.getResultSet();
        if (rsProd.next()) {
            valorItens = rsProd.getDouble("preco");
        } else {
            System.out.println("Produto inválido! Operação cancelada.");
            return;
        }

        double taxaEntrega = ClassStatic.CalculadoraDelivery.calcularTaxa(valorItens);
        double valorTotal = valorItens + taxaEntrega;

        System.out.printf("Subtotal dos itens: R$ %.2f%n", valorItens);
        System.out.printf("Taxa de entrega: R$ %.2f%n", taxaEntrega);
        System.out.printf("Valor total do pedido: R$ %.2f%n", valorTotal);

        System.out.println("Escolha a forma de pagamento:1 - PI X2 - Cartão3 - Dinheiro");
        System.out.print("Opção: ");
        int optPagto = Integer.parseInt(sc.nextLine());
        String formaPagto = (optPagto == 1) ? "PIX" : (optPagto == 2) ? "Cartão" : "Dinheiro";

        System.out.println("Validando pagamento...1 - Aprovar Operação 2 - Recusar Operação");
        System.out.print("Simular resposta do banco: ");
        int simulaBanco = Integer.parseInt(sc.nextLine());

        if (simulaBanco == 2) {
            System.out.println("Pagamento não autorizado.");
        } else {
            String sqlInsertPedido = "INSERT INTO pedidos (id_cliente, valor_itens, taxa_entrega, valor_total, forma_pagamento) " +
                    "VALUES (" + idCliente + ", " + valorItens + ", " + taxaEntrega + ", " + valorTotal + ", '" + formaPagto + "')";
            stmt.executeUpdate(sqlInsertPedido, Statement.RETURN_GENERATED_KEYS);

            ResultSet rsKeys = stmt.getGeneratedKeys();
            int numPedido = 0;
            if (rsKeys.next()) {
                numPedido = rsKeys.getInt(1);
            }

            System.out.println("");
            System.out.println("NÚMERO DO PEDIDO GERADO: " + numPedido);
            System.out.println("Status: Enviado para a cozinha");
            System.out.println("Pedido realizado com sucesso.");
            System.out.println("");
            rsKeys.close();
        }

        rsLogin.close();
        rsCardapio.close();
        rsProd.close();
        stmtCardapio.close();
        stmt.close();
        conn.close();
    }

    private static void atualizarStatusPedido(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o Número (ID) do pedido que deseja atualizar: ");
        int numPedido = Integer.parseInt(sc.nextLine());

        System.out.println("Escolha o novo status: 1 - Em Preparo 2 - Saiu para Entrega 3 - Entregue");
        System.out.print("Opção: ");
        int optStatus = Integer.parseInt(sc.nextLine());
        String novoStatus = (optStatus == 1) ? "Em Preparo" : (optStatus == 2) ? "Saiu para Entrega" : "Entregue";

        String sqlUpdate = "UPDATE pedidos SET status_pedido = '" + novoStatus + "' WHERE id = " + numPedido;
        int linhasModificadas = stmt.executeUpdate(sqlUpdate);

        if (linhasModificadas > 0) {
            System.out.println("Status do pedido atualizado com sucesso!");
        } else {
            System.out.println("Pedido não localizado.");
        }

        stmt.close();
        conn.close();
    }

    private static void cancelarPedido(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o Número (ID) do pedido que deseja remover do banco: ");
        int numPedido = Integer.parseInt(sc.nextLine());

        String sqlDelete = "DELETE FROM pedidos WHERE id = " + numPedido;
        int linhasModificadas = stmt.executeUpdate(sqlDelete);

        if (linhasModificadas > 0) {
            System.out.println("Pedido excluído e purgado do banco de dados com sucesso!");
        } else {
            System.out.println("Nenhum registro encontrado para o número informado.");
        }

        stmt.close();
        conn.close();
    }
}
