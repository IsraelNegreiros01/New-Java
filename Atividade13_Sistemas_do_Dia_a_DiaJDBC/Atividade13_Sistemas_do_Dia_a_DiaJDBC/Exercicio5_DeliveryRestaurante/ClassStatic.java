package Exercício13_Sistema_de_Delivery;

import Exercício13_Sistema_de_Delivery.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassStatic {

    public static class CalculadoraDelivery {
        public static double calcularTaxa(double valorItens) {
            if (valorItens < 50.00) {
                return 8.00;
            } else {
                return 0.00;
            }
        }
    }

    public static void exibirRelatorioPedidos() throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "SELECT p.id, c.nome, p.valor_total, p.forma_pagamento, p.status_pedido " +
                "FROM pedidos p JOIN clientes c ON p.id_cliente = c.id";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println(" LISTA DE PEDIDOS NO BANCO (SELECT) ");
        boolean possuiRegistros = false;

        while (rs.next()) {
            possuiRegistros = true;
            System.out.println("Nº Pedido: " + rs.getInt("id") +
                    " | Cliente: " + rs.getString("nome") +
                    " | Total: R$ " + rs.getDouble("valor_total") +
                    " | Pagamento: " + rs.getString("forma_pagamento") +
                    " | Status: " + rs.getString("status_pedido"));
        }

        if (!possuiRegistros) {
            System.out.println("Nenhum pedido localizado no sistema.");
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
