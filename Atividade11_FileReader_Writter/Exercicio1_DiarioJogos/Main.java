package Atividade11_FileReader_Writter.Exercicio1_DiarioJogos;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            File pasta = new File("C:\\Diario_Jogos");

            if (!pasta.exists()) {
                pasta.mkdir();
            }

            File arquivo = new File("C:\\Diario_Jogos\\SeusJogos.txt");

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Bem vindo ao seu diário de jogos");
        System.out.println("Digite seus jogos favoritos para serem adicionados ao seu diário");
        System.out.println("Digite Fim para quando você quiser parar");
        String path = "C:\\Diario_Jogos\\SeusJogos.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {

            String SalvarJogos;

            while (true) {
                SalvarJogos = sc.nextLine();

                if (SalvarJogos.equalsIgnoreCase("fim")) {
                    break;
                }

                bw.write(SalvarJogos);
                bw.newLine();
            }
            System.out.println("Jogos salvos com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Digite sim se quiser ver sua lista de jogos no seu diário");
        String VerJogos = sc.nextLine();
        if (VerJogos.equalsIgnoreCase("sim")) {
            File file = new File("C:\\Diario_Jogos\\SeusJogos.txt");
            try {
                sc = new Scanner(file);

                while (sc.hasNextLine()) {
                    System.out.println(sc.nextLine());
                }

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());

            } finally {
                if (sc != null) {
                    sc.close();
                }
            }
        }
    }
}