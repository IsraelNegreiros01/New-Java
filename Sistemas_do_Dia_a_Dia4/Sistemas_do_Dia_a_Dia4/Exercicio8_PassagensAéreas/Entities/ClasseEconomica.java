package Sistemas_do_Dia_a_Dia4.Exercicio8_PassagensAéreas.Entities;


    public class ClasseEconomica extends Passagem {

        private boolean despacharMala;
        private double taxaBagagem = 120.00;

        public ClasseEconomica(String nomePassageiro,
                               String numeroVoo,
                               double precoOriginal,
                               boolean despacharMala) {

            super(nomePassageiro, numeroVoo, precoOriginal);
            this.despacharMala = despacharMala;
        }

        public double calcularPrecoFinal() {
            if (despacharMala) {
                return getPrecoOriginal() + taxaBagagem;
            }
            return getPrecoOriginal();
        }

        @Override
        public String toString() {
            return "===== BILHETE DE EMBARQUE =====" +
                    "Passageiro: " + getNomePassageiro() +
                    "Voo: " + getNumeroVoo() +
                    "Classe: Econômica" +
                    "Bagagem de mão: Inclusa (até 10kg)" +
                    "Mala despachada: " + (despacharMala ? "Sim" : "Não") +
                    "Valor Final: R$ " + String.format("%.2f", calcularPrecoFinal());
        }
    }

