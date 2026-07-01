package _2_File_e_Buffered._3_CredenciamentoHackathon.Application;

import _2_File_e_Buffered._3_CredenciamentoHackathon.Entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String entrada =
                "C:\\temp\\inscricoes_brutas.txt";

        String aprovados =
                "C:\\temp\\aprovados_hackathon.txt";

        String pendencias =
                "C:\\temp\\pendencias_inscricao.txt";

        List<Participante> lista =
                new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(entrada));

             BufferedWriter bwAprovados =
                     new BufferedWriter(
                             new FileWriter(aprovados));

             BufferedWriter bwPendencias =
                     new BufferedWriter(
                             new FileWriter(pendencias))) {

            String line = br.readLine();

            while (line != null) {

                try {

                    String[] dados =
                            line.split(";");

                    if (dados.length < 5) {
                        throw new Exception();
                    }

                    Participante p;

                    if (dados[0].equals("D")) {

                        p = new Desenvolvedor(
                                dados[1],
                                Integer.parseInt(dados[2]),
                                dados[3],
                                dados[4]);
                    }

                    else if (dados[0].equals("G")) {

                        p = new Designer(
                                dados[1],
                                Integer.parseInt(dados[2]),
                                dados[3],
                                dados[4]);
                    }

                    else {
                        throw new Exception();
                    }

                    lista.add(p);

                    bwAprovados.write(
                            p.toString());

                    bwAprovados.newLine();

                }
                catch (Exception e) {

                    bwPendencias.write(line);
                    bwPendencias.newLine();
                }

                line = br.readLine();
            }

        }
        catch (IOException e) {
            System.out.println("Erro: "
                    + e.getMessage());
        }

        System.out.println("Processamento concluido.");
    }
}
