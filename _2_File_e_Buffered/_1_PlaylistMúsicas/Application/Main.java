package _2_File_e_Buffered._1_PlaylistMúsicas.Application;

import _2_File_e_Buffered._1_PlaylistMúsicas.Entities.Musica;
import _2_File_e_Buffered._1_PlaylistMúsicas.Entities.Genero;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        String path = "C:\\temp\\minha_playlist.txt";

        Musica m1 = new Musica(
                "Numb",
                "Linkin Park",
                2003,
                Genero.ROCK);

        Musica m2 = new Musica(
                "Shape of You",
                "Ed Sheeran",
                2017,
                Genero.POP);

        Musica m3 = new Musica(
                "Lose Yourself",
                "Eminem",
                2002,
                Genero.RAP);

        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(path))) {

            bw.write(m1.toString());
            bw.newLine();

            bw.write(m2.toString());
            bw.newLine();

            bw.write(m3.toString());

        }
        catch (IOException e) {
            System.out.println("Erro ao gravar arquivo.");
        }

        try (BufferedReader br =
                     new BufferedReader(new FileReader(path))) {

            String line = br.readLine();

            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao ler arquivo.");
        }
    }
}