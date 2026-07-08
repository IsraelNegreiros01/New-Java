package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio1_ControleBiblioteca.Entities;

public class Emprestimo {
    private Integer id;
    private Integer matricula;
    private Integer codigoLivro;
    private StatusEmprestimo status;
    public Emprestimo() {
    }
public Emprestimo(Integer id, Integer matricula, Integer codigoLivro, StatusEmprestimo status) {
        this.codigoLivro = codigoLivro;
        this.id = id;
        this.matricula = matricula;
        this.status = status;
}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMatricula() {
        return matricula;
    }
    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }
    public Integer getCodigoLivro() {
        return codigoLivro;
    }
    public void setCodigoLivro(Integer codigoLivro) {
        this.codigoLivro = codigoLivro;
    }
    public StatusEmprestimo getStatus() {
        return status;
    }
    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }
}
