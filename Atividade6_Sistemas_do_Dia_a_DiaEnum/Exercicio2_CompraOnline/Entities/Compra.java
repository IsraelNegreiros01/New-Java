package Atividade6_Sistemas_do_Dia_a_DiaEnum.Exercicio2_CompraOnline.Entities;

public class Compra {

    private String nome;
    private String cpf;
    private String endereco;

    private double valorCompra;
    private double frete;

    private boolean cartaoAprovado;

    private int numeroPedido;

    private StatusPedido status;

    public Compra() {
    }

    public double calcularFrete() {

        if (valorCompra > 500.0) {
            return 0.0;
        }

        return 25.0;
    }

    public StatusPedido finalizarCompra() {

        if (!cartaoAprovado) {
            return StatusPedido.PAGAMENTO_RECUSADO;
        }

        return StatusPedido.COMPRA_REALIZADA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public boolean isCartaoAprovado() {
        return cartaoAprovado;
    }

    public void setCartaoAprovado(boolean cartaoAprovado) {
        this.cartaoAprovado = cartaoAprovado;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}