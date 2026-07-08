package db_streaming;

import db_streaming.ClassConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassStatic {

    public static class ContaStreaming {
        private String nomeUsuario;
        private String email;
        private double precoBase;

        public ContaStreaming(String nomeUsuario, String email) {
            this.nomeUsuario = nomeUsuario;
            this.email = email;
        }

        public String getNomeUsuario() { return nomeUsuario; }
        public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public double getPrecoBase() { return precoBase; }
        public void setPrecoBase(double precoBase) { this.precoBase = precoBase; }

        @Override
        public String toString() {
            return "Assinante: " + nomeUsuario + " | Email: " + email;
        }
    }

    public static class PlanoPadrao extends ContaStreaming {
        private String qualidadeVideo = "Full HD";
        private int limiteTelas = 2;

        public PlanoPadrao(String nome, String email) {
            super(nome, email);
            super.setPrecoBase(30.00);
        }

        public String getQualidadeVideo() { return qualidadeVideo; }
        public int getLimiteTelas() { return limiteTelas; }

        @Override
        public String toString() {
            return super.toString() + "\nPlano: Padrao | Valor: R$ " + getPrecoBase() +
                    " | Qualidade Maxima: " + qualidadeVideo + " | Limite de Telas: " + limiteTelas;
        }
    }

    public static class PlanoPremium extends ContaStreaming {
        private String qualidadeVideo = "4K Ultra HD";
        private int limiteTelas = 4;
        private boolean permiteDownload = true;

        public PlanoPremium(String nome, String email) {
            super(nome, email);
            super.setPrecoBase(50.00);
        }

        public String getQualidadeVideo() { return qualidadeVideo; }
        public int getLimiteTelas() { return limiteTelas; }
        public boolean isPermiteDownload() { return permiteDownload; }

        @Override
        public String toString() {
            return super.toString() + "\nPlano: Premium (VIP) | Valor: R$ " + getPrecoBase() +
                    " | Qualidade Maxima: " + qualidadeVideo + " | Limite de Telas: " + limiteTelas +
                    " | Download Offline: Ativo (" + permiteDownload + ")";
        }
    }

    public static void exibirRelatorioAssinaturas() throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "SELECT a.id, u.nome_usuario, u.email, a.tipo_plano, p.preco_base, p.qualidade_video " +
                "FROM assinaturas_ativas a " +
                "JOIN usuarios u ON a.id_usuario = u.id " +
                "JOIN planos_detalhes p ON a.tipo_plano = p.tipo_plano";

        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n=== ASSINATURAS ATIVAS NO BANCO DE DADOS (SELECT) ===");
        boolean possuiRegistros = false;

        while (rs.next()) {
            possuiRegistros = true;
            System.out.println("ID Assinatura: " + rs.getInt(1) +
                    " | Cliente: " + rs.getString(2) +
                    " | Email: " + rs.getString(3) +
                    " | Plano: " + rs.getString(4) +
                    " | Valor: R$ " + rs.getDouble(5) +
                    " | Qualidade: " + rs.getString(6));
        }

        if (!possuiRegistros) {
            System.out.println("Nenhum usuario assinando planos no momento.");
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
