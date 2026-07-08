package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DB.DB;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.Pagamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PagamentoDAO {
    public void inserirPagamento(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos " + "(numero_pedido, cartao_aprovado) " + "VALUES (?, ?) ";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, pagamento.getNumeroPedido());
            st.setBoolean(2, pagamento.isCartaoAprovado());
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarPagamentos() {
        String sql = "SELECT * FROM pagamentos";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println("| id: " + rs.getInt("id") + " | numero_pedido: " + rs.getInt("numero_pedido") + " | cartao_aprovado: " + rs.getBoolean("cartao_aprovado") + " |");
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarPagamento(Pagamento pagamento) {
        String sql = "UPDATE pagamentos " + "SET numero_pedido = ?, cartao_aprovado = ? " + "WHERE id = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, pagamento.getNumeroPedido());
            st.setBoolean(2, pagamento.isCartaoAprovado());
            st.setInt(3, pagamento.getId());
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletarPagamento(int numeroPedido) {
        String sql = "DELETE FROM pagamentos WHERE numero_pedido = ?";
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
