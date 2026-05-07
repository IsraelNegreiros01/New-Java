package Lista_de_Orientação_a_Objetos._9_Agenda_de_Contatos.Entities;

public class Agenda {
    public Contato[] contatos = new Contato[5];

    public void listarContatos() {
        System.out.println("\n--- Lista de Contatos ---");
        for (int i = 0; i < contatos.length; i++) {
            if (contatos[i] != null) {
                System.out.println("Nome: " + contatos[i].nome + " | Tel: " + contatos[i].telefone);
            } else {
                System.out.println("Posição " + (i + 1) + ": [Vazio]");
            }
        }
    }
}