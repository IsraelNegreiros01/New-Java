package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities;

public class Pagamento {
    private int id;
    private int numeroPedido;
    private boolean cartaoAprovado;

    public Pagamento() {
    }

    public Pagamento(int id, int numeroPedido, boolean cartaoAprovado) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.cartaoAprovado = cartaoAprovado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public boolean isCartaoAprovado() {
        return cartaoAprovado;
    }

    public void setCartaoAprovado(boolean cartaoAprovado) {
        this.cartaoAprovado = cartaoAprovado;
    }
}
