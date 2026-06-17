package Exercicio3_InventarioLaboratorio.Application;

import Exercicio3_InventarioLaboratorio.Entities.Computador;
import Exercicio3_InventarioLaboratorio.Entities.Equipamento;
import Exercicio3_InventarioLaboratorio.Entities.EquipamentoComum;
import Exercicio3_InventarioLaboratorio.Entities.InventarioException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Equipamento> lista = new ArrayList<>();

        String entrada = "C:\\temp\\entrada_inventario.txt";
        String relatorio = "C:\\temp\\relatorio_valido.txt";
        String erros = "C:\\temp\\log_erros.txt";
        try (
                BufferedReader br = new BufferedReader(
                        new FileReader(entrada));

                BufferedWriter bwRelatorio = new BufferedWriter(
                        new FileWriter(relatorio));

                BufferedWriter bwErro = new BufferedWriter(
                        new FileWriter(erros))
        ) {
            String linha;

            while ((linha = br.readLine()) != null) {

                try {

                    String[] campos = linha.split(",");

                    if (campos[0].equals("E")) {

                        Equipamento equipamento =
                                new EquipamentoComum(
                                        Integer.parseInt(campos[1]),
                                        campos[2]);

                        lista.add(equipamento);
                    }

                    else if (campos[0].equals("C")) {

                        Equipamento computador =
                                new Computador(
                                        Integer.parseInt(campos[1]),
                                        campos[2],
                                        campos[3],
                                        campos[4]);

                        lista.add(computador);
                    }

                    else {

                        throw new InventarioException(
                                "Linha inválida: " + linha);
                    }

                }
                catch (InventarioException e) {

                    bwErro.write(e.getMessage());
                    bwErro.newLine();
                }
            }

            for (Equipamento equipamento : lista) {

                bwRelatorio.write(
                        equipamento.gerarRelatorio());

                bwRelatorio.newLine();
            }

            System.out.println("Relatório gerado com sucesso!");
            System.out.println("Arquivo: relatorio_valido.txt");
            System.out.println("Arquivo: log_erros.txt");

        }

        catch (IOException e) {

            System.out.println(
                    "Erro ao manipular arquivos: "
                            + e.getMessage());
        }
    }
}