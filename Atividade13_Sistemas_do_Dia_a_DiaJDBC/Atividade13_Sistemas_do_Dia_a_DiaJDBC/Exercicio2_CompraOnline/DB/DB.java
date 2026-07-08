package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String url = "jdbc:mysql://localhost:3306/compra_online";
    private static final String user = "root";
    private static final String password = "";
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
