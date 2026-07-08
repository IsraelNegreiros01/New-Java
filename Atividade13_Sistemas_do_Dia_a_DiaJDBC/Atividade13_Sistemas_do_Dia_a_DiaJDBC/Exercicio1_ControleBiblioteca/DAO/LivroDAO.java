package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DB.DB;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LivroDAO {
    public void inserirLivro(Livro livro) {
        String sql = "INSERT INTO livros (codigo_livro, disponivel) VALUES (?, ?)";
        try{
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, livro.getCodigoLivro());
            st.setBoolean(2, livro.isDisponivel());
            st.executeUpdate();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarLivros() {
        String sql = "SELECT * FROM livros";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println("| codigo_livro: "+rs.getInt("codigo_livro") + " | disponivel: " + rs.getBoolean("disponivel")+" |");
            }
            rs.close();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void atualizarLivro( Livro livro) {
        String sql = "UPDATE livros SET disponivel = ? WHERE codigo_livro = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, livro.isDisponivel());
            st.setInt(2, livro.getCodigoLivro());
            st.executeUpdate();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletarLivro(int codigoLivro) {
        String sql = "DELETE FROM livros WHERE codigo_livro = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, codigoLivro);
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
