package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DB.DB;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PedidoDAO {
    public void inserirPedido(Compra pedido) {
        String sql = "INSERT INTO pedidos "+" (cpf, valor_compra, frete, status) "+"VALUES (?, ?, ?, ?) ";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, pedido.getCpf());
            st.setDouble(2, pedido.getValorCompra());
            st.setDouble(3, pedido.getFrete());
            st.setString(4, pedido.getStatus().name());
            st.executeUpdate();
            st.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarPedidos(){
        String sql = "SELECT * FROM pedidos";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println( "| numero_pedido: "+ rs.getInt("numero_pedido")+" | cpf: " + rs.getString("cpf") + " | valor_compra: " + rs.getDouble("valor_compra")+" | frete: "+ rs.getDouble( "frete") + " status: " + rs.getString("status") + " |");
            }
            rs.close();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void atualizarPedido(Compra pedido) {
        String sql = "UPDATE pedidos " + "SET valor_compra = ?, frete = ?, status = ? "+"WHERE cpf = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setDouble(1, pedido.getValorCompra());
            st.setDouble(2, pedido.getFrete());
            st.setString(3, pedido.getStatus().name());
            st.setString(4, pedido.getCpf());
            st.executeUpdate();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletarPedido(Integer numeroPedido) {
        String sql = "DELETE FROM pedidos WHERE numero_pedido = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, numeroPedido);
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
