package Lista_de_Orientação_a_Objetos._9_Agenda_de_Contatos.Aplications;

import Lista_de_Orientação_a_Objetos._9_Agenda_de_Contatos.Entities.Agenda;
import Lista_de_Orientação_a_Objetos._9_Agenda_de_Contatos.Entities.Contato;

import java.util.Locale;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Agenda minhaAgenda = new Agenda();

        System.out.println("Cadastro de Agenda (5 contatos):");

        for (int i = 0; i < minhaAgenda.contatos.length; i++) {
            minhaAgenda.contatos[i] = new Contato();

            System.out.print("Nome do contato " + (i + 1) + ": ");
            minhaAgenda.contatos[i].nome = sc.nextLine();

            System.out.print("Telefone do contato " + (i + 1) + ": ");
            minhaAgenda.contatos[i].telefone = sc.nextLine();
        }

        minhaAgenda.listarContatos();

        sc.close();
    }
}