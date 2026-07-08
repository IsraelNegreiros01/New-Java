package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.DB.DB;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities.Biblioteca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class BibliotecaDAO {
    public void inserirAluno(Biblioteca aluno){
        String sql = "INSERT INTO alunos " + "(matricula, multa, livros_emprestados) " + "VALUES (?, ?, ?)";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, aluno.getMatricula());
            st.setBoolean(2, aluno.isMulta());
            st.setInt(3, aluno.getLivrosEmprestados());
            st.executeUpdate();
            st.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarAlunos() {
        String sql = "SELECT * FROM alunos";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println("| matricula: "+rs.getInt("matricula") + " | multa: " + rs.getBoolean("multa") + " | livros_emprestados: " + rs.getInt("livros_emprestados")+" |");
            }
            rs.close();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void atualizarAluno(Biblioteca aluno) {
        String sql = "UPDATE alunos " + "SET multa = ?, livros_emprestados = ? " + "WHERE matricula = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, aluno.isMulta());
            st.setInt(2, aluno.getLivrosEmprestados());
            st.setInt(3, aluno.getMatricula());
            st.executeUpdate();
            st.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletarAluno(int matricula) {
        String sql = "DELETE FROM alunos WHERE matricula = ?";
        try {
            Connection conn = DB.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, matricula);
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

