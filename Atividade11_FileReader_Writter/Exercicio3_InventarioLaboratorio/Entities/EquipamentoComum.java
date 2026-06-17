package Exercicio3_InventarioLaboratorio.Entities;

public class EquipamentoComum extends Equipamento {

    public EquipamentoComum(Integer id, String marca) {
        super(id, marca);
    }

    @Override
    public String gerarRelatorio() {

        return "EQUIPAMENTO | ID: "
                + getId()
                + " | Marca: "
                + getMarca();
    }
}