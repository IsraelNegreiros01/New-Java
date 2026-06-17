package Exercicio2_ExportaçãodeUsuarios.Aplication;
import Exercicio2_ExportaçãodeUsuarios.Entities.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        List<Usuario> usuarios = new ArrayList<>();


        usuarios.add(new Usuario("Ana", "ana@email.com", 20));
        usuarios.add(new Usuario("Bruno", "bruno@email.com", 25));
        usuarios.add(new Usuario("Carlos", "carlos@email.com", 30));
        usuarios.add(new Usuario("Daniela", "daniela@email.com", 22));


        try {
            FileWriter fw = new FileWriter("arquivos/usuarios.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Usuario u : usuarios) {
                bw.write(u.getNome() + ";" + u.getEmail() + ";" + u.getIdade());
                bw.newLine();
            }

            bw.close();

            System.out.println("Arquivo usuarios.csv criado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }


        List<Usuario> usuariosImportados = new ArrayList<>();


        try {
            FileReader fr = new FileReader("usuarios.csv");
            BufferedReader br = new BufferedReader(fr);

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(";");

                String nome = dados[0];
                String email = dados[1];
                int idade = Integer.parseInt(dados[2]);

                Usuario usuario = new Usuario(nome, email, idade);

                usuariosImportados.add(usuario);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }


        System.out.println("Usuários importados:");

        for (Usuario u : usuariosImportados) {
            u.exibirInfo();
        }
    }
}