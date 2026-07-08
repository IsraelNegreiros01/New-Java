package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercício3_AgendamentoMedico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClassConnection {
    public static Connection getConnection() throws SQLException {
        try {

            String url = "JDBC:mysql://localhost:3306/AgendamentoMedico";





            String user = "root";
            String password = "";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}

