package Exercicio3_InventarioLaboratorio.Entities;

public abstract class Equipamento {

    private Integer id;
    private String marca;

    public Equipamento(Integer id, String marca) {
        this.id = id;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public abstract String gerarRelatorio();
}