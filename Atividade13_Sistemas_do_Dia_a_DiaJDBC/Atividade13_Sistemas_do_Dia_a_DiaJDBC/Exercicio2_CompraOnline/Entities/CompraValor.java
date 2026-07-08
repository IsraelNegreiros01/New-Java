package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities;

public class CompraValor extends Compra {
    @Override
    public StatusPedido finalizarCompra() {

        if (!isCartaoAprovado()) {
            return StatusPedido.PAGAMENTO_RECUSADO;
        }

        return StatusPedido.COMPRA_REALIZADA;
    }
}
