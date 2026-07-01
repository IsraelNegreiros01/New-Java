package _2_File_e_Buffered._2_JogoLuta.Application;

import _2_File_e_Buffered._2_JogoLuta.Entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String path = "C:\\temp\\personagens_db.csv";

        List<Personagem> lista = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(path))) {

            String line = br.readLine();

            while (line != null) {

                String[] dados = line.split(";");

                if (dados[0].equals("L")) {

                    lista.add(
                            new Corpo_a_Corpo(
                                    dados[1],
                                    Integer.parseInt(dados[2]),
                                    dados[3]));
                }

                else if (dados[0].equals("A")) {

                    lista.add(
                            new Atirador(
                                    dados[1],
                                    Integer.parseInt(dados[2]),
                                    dados[3]));
                }

                line = br.readLine();
            }

        }
        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("PERSONAGENS:");

        for (Personagem p : lista) {
            System.out.println(p);
        }
    }
}