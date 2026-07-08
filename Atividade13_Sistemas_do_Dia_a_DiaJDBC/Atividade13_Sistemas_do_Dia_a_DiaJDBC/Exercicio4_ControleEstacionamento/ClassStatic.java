package Exercício13_Controle_de_estacionamento;

import Exercício13_Controle_de_estacionamento.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassStatic {

    public static class TarifaEstacionamento {
        public static double calcularValor(int horas) {
            if (horas <= 1) {
                return 10.00;
            } else {
                return 10.00 + ((horas - 1) * 5.00);
            }
        }
    }

    public static void exibirVeiculosEstacionados() throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "SELECT id, placa, tipo_veiculo, horario_entrada, status_veiculo FROM veiculos_estacionados";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println(" VEÍCULOS NO BANCO DE DADOS (SELECT) ");
        boolean possuiRegistros = false;

        while (rs.next()) {
            possuiRegistros = true;
            System.out.println("ID Registro: " + rs.getInt("id") +
                    " | Placa: " + rs.getString("placa") +
                    " | Tipo: " + rs.getString("tipo_veiculo") +
                    " | Entrada: " + rs.getTimestamp("horario_entrada") +
                    " | Status: " + rs.getString("status_veiculo"));
        }

        if (!possuiRegistros) {
            System.out.println("Nenhum veiculo estacionado ou registrado no momento.");
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
