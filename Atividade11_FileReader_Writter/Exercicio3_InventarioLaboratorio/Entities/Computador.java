package Exercicio3_InventarioLaboratorio.Entities;

public final class Computador extends Equipamento {

    private String processador;
    private String quantidadeMemoria;

    public Computador(Integer id,
                      String marca,
                      String processador,
                      String quantidadeMemoria) {

        super(id, marca);
        this.processador = processador;
        this.quantidadeMemoria = quantidadeMemoria;
    }

    @Override
    public String gerarRelatorio() {

        return "COMPUTADOR | ID: "
                + getId()
                + " | Marca: "
                + getMarca()
                + " | CPU: "
                + processador
                + " | Memoria: "
                + quantidadeMemoria;
    }
}