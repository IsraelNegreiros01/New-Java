package db_passagens;

import db_passagens.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SistemaPassagens {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== COMPANHIA AÉREA - GERENCIADOR DE PASSAGENS ===");
            System.out.println("1 - Comprar/Emitir Passagem (INSERT)");
            System.out.println("2 - Listar Passagens Emitidas (SELECT)");
            System.out.println("3 - Alterar Voo da Passagem (UPDATE)");
            System.out.println("4 - Cancelar Passagem/Bilhete (DELETE)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    venderPassagem(sc);
                    break;
                case 2:
                    ClassStatic.listarPassagensVendidas();
                    break;
                case 3:
                    alterarVooPassagem(sc);
                    break;
                case 4:
                    cancelarPassagem(sc);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema de passagens...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
        sc.close();
    }

    private static void venderPassagem(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Nome do Passageiro: ");
        String nome = sc.nextLine();

        System.out.println("\nVoos disponíveis regulamentados:");
        ResultSet rsVoos = stmt.executeQuery("SELECT * FROM voos");
        while (rsVoos.next()) {
            System.out.println("Código Voo: " + rsVoos.getString("numero_voo") +
                    " | Rota: " + rsVoos.getString("origem") + " -> " + rsVoos.getString("destino") +
                    " | Tarifa Regular: R$ " + rsVoos.getDouble("preco_base"));
        }

        System.out.print("\nDigite o Código do Voo desejado: ");
        String numeroVoo = sc.nextLine().toUpperCase();

        String sqlBuscaVoo = "SELECT preco_base FROM voos WHERE numero_voo = '" + numeroVoo + "'";
        ResultSet rsPreco = stmt.executeQuery(sqlBuscaVoo);

        if (!rsPreco.next()) {
            System.out.println("Voo inválido ou não cadastrado! Operação cancelada.");
            rsVoos.close();
            rsPreco.close();
            stmt.close();
            conn.close();
            return;
        }
        double precoOriginal = rsPreco.getDouble("preco_base");

        System.out.println("Escolha a Classe do Assento:\n1 - Classe Economica\n2 - Classe Executiva");
        System.out.print("Opcao: ");
        int classeOpcao = Integer.parseInt(sc.nextLine());

        String tipoClasse = "";
        double precoFinal = 0;
        String bagagem = "";
        String servicos = "";

        if (classeOpcao == 1) {
            System.out.print("Deseja despachar mala de até 23kg? (S/N): ");
            boolean despacha = sc.nextLine().equalsIgnoreCase("S");

            ClassStatic.ClasseEconomica eco = new ClassStatic.ClasseEconomica(nome, numeroVoo, precoOriginal, despacha);
            precoFinal = eco.calcularPrecoFinal();
            tipoClasse = "Economica";
            bagagem = despacha ? "1 Mochila de mao (10kg) + 1 Mala Despachada" : "Apenas 1 Mochila de mao (10kg)";
            servicos = "Servico de bordo simples regulamentado";

            System.out.println("\n=== BILHETE DE EMBARQUE IMPRESSO ===");
            System.out.println(eco.toString());

        } else if (classeOpcao == 2) {
            ClassStatic.ClasseExecutiva exec = new ClassStatic.ClasseExecutiva(nome, numeroVoo, precoOriginal);
            precoFinal = exec.calcularPrecoFinal();
            tipoClasse = "Executiva";
            bagagem = "2 Malas de ate 23kg inclusas gratuitamente";
            servicos = "Servico de Bordo VIP + Acesso a Sala VIP do Aeroporto";

            System.out.println("\n=== BILHETE DE EMBARQUE IMPRESSO ===");
            System.out.println(exec.toString());
        } else {
            System.out.println("Classe inválida.");
            return;
        }

        stmt.execute("INSERT INTO passageiros (nome) VALUES ('" + nome + "')", Statement.RETURN_GENERATED_KEYS);
        ResultSet rsKeys = stmt.getGeneratedKeys();
        int idPassageiro = 0;
        if (rsKeys.next()) {
            idPassageiro = rsKeys.getInt(1);
        }

        String sqlInsertPassagem = "INSERT INTO passagens_vendidas (id_passageiro, numero_voo, tipo_classe, preco_final, detalhes_bagagem, servicos_inclusos) " +
                "VALUES (" + idPassageiro + ", '" + numeroVoo + "', '" + tipoClasse + "', " + precoFinal + ", '" + bagagem + "', '" + servicos + "')";
        stmt.execute(sqlInsertPassagem);

        System.out.println("\nPassagem registrada com sucesso no banco de dados!");

        rsVoos.close();
        rsPreco.close();
        rsKeys.close();
        stmt.close();
        conn.close();
    }

    private static void alterarVooPassagem(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o ID do Bilhete que deseja alterar (UPDATE): ");
        int idBilhete = Integer.parseInt(sc.nextLine());
        System.out.print("Informe o novo Código do Voo desejado: ");
        String novoVoo = sc.nextLine().toUpperCase();

        ResultSet rsVoo = stmt.executeQuery("SELECT preco_base FROM voos WHERE numero_voo = '" + novoVoo + "'");
        if (!rsVoo.next()) {
            System.out.println("Voo de destino não existe.");
            return;
        }
        double novoPrecoBase = rsVoo.getDouble("preco_base");

        String sqlUpdate = "UPDATE passagens_vendidas SET numero_voo = '" + novoVoo + "', preco_final = preco_final + " + (novoPrecoBase * 0.1) + " WHERE id = " + idBilhete;
        int linhas = stmt.executeUpdate(sqlUpdate);

        if (linhas > 0) {
            System.out.println("Voo do bilhete alterado com sucesso! (Aplicado taxa de reemissão de 10%)");
        } else {
            System.out.println("Bilhete nao localizado.");
        }

        rsVoo.close();
        stmt.close();
        conn.close();
    }

    private static void cancelarPassagem(Scanner sc) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        System.out.print("Informe o ID do Bilhete que deseja cancelar (DELETE): ");
        int idBilhete = Integer.parseInt(sc.nextLine());

        String sqlDelete = "DELETE FROM passagens_vendidas WHERE id = " + idBilhete;
        int linhas = stmt.executeUpdate(sqlDelete);

        if (linhas > 0) {
            System.out.println("Bilhete cancelado e estornado com sucesso no banco de dados!");
        } else {
            System.out.println("Bilhete nao localizado.");
        }

        stmt.close();
        conn.close();
    }
}
