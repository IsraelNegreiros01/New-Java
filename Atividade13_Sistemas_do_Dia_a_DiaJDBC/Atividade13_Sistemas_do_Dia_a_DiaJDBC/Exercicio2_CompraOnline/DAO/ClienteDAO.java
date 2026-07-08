package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DB.DB;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ClienteDAO {
    public void inserirCliente(Compra cliente) {
        String sql = "INSERT INTO clientes "+"(cpf, nome, endereco) "+"VALUES (?, ?, ?) ";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, cliente.getCpf());
            st.setString(2, cliente.getNome());
            st.setString(3, cliente.getEndereco());
            st.executeUpdate();
            st.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarClientes(){
        String sql = "SELECT * FROM clientes";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println("| cpf: "+rs.getString("cpf") + " | nome: " + rs.getString("nome") + " | endereco: " + rs.getString("endereco")+" |");
            }
            rs.close();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void atualizarCliente(Compra cliente) {
        String sql = "UPDATE clientes SET nome = ?, endereco = ? " + "WHERE cpf = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getEndereco());
            st.setString(3, cliente.getCpf());
            int rows = st.executeUpdate();
            System.out.println("Cliente atualizado com sucesso.");
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletarCliente(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, cpf);
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
