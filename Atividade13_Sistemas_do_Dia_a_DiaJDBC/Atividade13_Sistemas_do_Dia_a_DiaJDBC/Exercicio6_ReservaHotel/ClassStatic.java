package db_hotel;

import db_hotel.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassStatic {

    public static double calcularValorTotal(double precoDiaria, long diarias) {
        double total = precoDiaria * diarias;
        if (diarias > 5) {
            total = total * 0.90;
            System.out.println("Desconto de 10% aplicado automaticamente (Mais que 5 diarias)!");
        }
        return total;
    }

    public static void exibirTodasAsReservas() throws SQLException {
        Connection connection = ClassConnection.getConnection();
        Statement stmt = connection.createStatement();

        String sql = "SELECT r.id, c.nome, r.valor_total FROM reservas r JOIN clientes c ON r.id_cliente = c.id";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n=== RESERVAS REGISTRADAS NO BANCO ===");
        boolean temDados = false;

        while (rs.next()) {
            temDados = true;
            int idReserva = rs.getInt(1);
            String nomeCliente = rs.getString(2);
            double valorTotal = rs.getDouble(3);

            System.out.println("ID Reserva: " + idReserva + " => Hospede: " + nomeCliente + " | Total: R$ " + valorTotal);
        }

        if (!temDados) {
            System.out.println("Nenhuma reserva cadastrada no momento.");
        }

        rs.close();
        stmt.close();
        connection.close();
    }
}
