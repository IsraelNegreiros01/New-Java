package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DB.DB;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities.Emprestimo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class EmprestimoDAO {
    public void inserirEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (matricula, codigo_livro, status) VALUES (?, ?, ?)";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, emprestimo.getMatricula());
            st.setInt(2, emprestimo.getCodigoLivro());
            st.setString(3, emprestimo.getStatus().name());
            st.executeUpdate();
            st.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarEmprestimos() {
        String sql = "SELECT * FROM emprestimos";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println("| ID: " + rs.getInt("id") + " | Matrícula: " + rs.getInt("matricula") + " | Código Livro: " + rs.getInt("codigo_livro") + " | Status: " + rs.getString("status")+" | ");
            }
            rs.close();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void atualizarEmprestimo(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET matricula = ?, codigo_livro = ?, status = ? WHERE id = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, emprestimo.getMatricula());
            st.setInt(2, emprestimo.getCodigoLivro());
            st.setString(3, emprestimo.getStatus().name());
            st.setInt(4, emprestimo.getId());
            st.executeUpdate();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletarEmprestimo(int id) {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
