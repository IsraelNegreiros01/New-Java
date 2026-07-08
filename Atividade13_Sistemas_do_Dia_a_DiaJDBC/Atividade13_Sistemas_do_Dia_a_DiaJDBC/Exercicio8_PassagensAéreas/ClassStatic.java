package db_passagens;

import db_passagens.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassStatic {

    public static class Passagem {
        private String nomePassageiro;
        private String numeroVoo;
        private double precoOriginal;

        public Passagem(String nomePassageiro, String numeroVoo, double precoOriginal) {
            this.nomePassageiro = nomePassageiro;
            this.numeroVoo = numeroVoo;
            this.precoOriginal = precoOriginal;
        }

        public String getNomePassageiro() { return nomePassageiro; }
        public void setNomePassageiro(String nomePassageiro) { this.nomePassageiro = nomePassageiro; }
        public String getNumeroVoo() { return numeroVoo; }
        public void setNumeroVoo(String numeroVoo) { this.numeroVoo = numeroVoo; }
        public double getPrecoOriginal() { return precoOriginal; }
        public void setPrecoOriginal(double precoOriginal) { this.precoOriginal = precoOriginal; }

        @Override
        public String toString() {
            return "Passageiro: " + nomePassageiro + " | Voo: " + numeroVoo + " | Preco Regular: R$ " + precoOriginal;
        }
    }

    public static class ClasseEconomica extends Passagem {
        private boolean despacharMala;
        private double taxaBagagem = 120.00;

        public ClasseEconomica(String nome, String voo, double preco, boolean despacharMala) {
            super(nome, voo, preco);
            this.despacharMala = despacharMala;
        }

        public boolean isDespacharMala() { return despacharMala; }

        public double calcularPrecoFinal() {
            return despacharMala ? (getPrecoOriginal() + taxaBagagem) : getPrecoOriginal();
        }

        @Override
        public String toString() {
            String malaInfo = despacharMala ? "Sim (Taxa de R$ 120,00 inclusa)" : "Nao (Apenas mala de mao)";
            return super.toString() + "\nClasse: Economica | Despacha Mala: " + malaInfo + " | TOTAL FINAL: R$ " + calcularPrecoFinal();
        }
    }

    public static class ClasseExecutiva extends Passagem {
        private boolean servicoBordoVip = true;
        private boolean acessoSalaVip = true;

        public ClasseExecutiva(String nome, String voo, double preco) {
            super(nome, voo, preco);
        }

        public double calcularPrecoFinal() {
            return getPrecoOriginal() * 1.5;
        }

        @Override
        public String toString() {
            return super.toString() + "\nClasse: Executiva (Acrescimo 50%) | Servico de Bordo VIP: Sim | Acesso Sala VIP: Sim | TOTAL FINAL: R$ " + calcularPrecoFinal();
        }
    }

    public static void listarPassagensVendidas() throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "SELECT p.id, pas.nome, p.numero_voo, p.tipo_classe, p.preco_final " +
                "FROM passagens_vendidas p " +
                "JOIN passageiros pas ON p.id_passageiro = pas.id";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n=== BILHETES E PASSAGENS EMITIDAS (SELECT) ===");
        boolean temDados = false;
        while (rs.next()) {
            temDados = true;
            System.out.println("ID Bilhete: " + rs.getInt(1) +
                    " | Passageiro: " + rs.getString(2) +
                    " | Voo: " + rs.getString(3) +
                    " | Classe: " + rs.getString(4) +
                    " | Total Pago: R$ " + rs.getDouble(5));
        }

        if (!temDados) {
            System.out.println("Nenhum bilhete emitido até o momento.");
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
