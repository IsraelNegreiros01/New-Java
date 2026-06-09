package Sistemas_do_Dia_a_Dia4.Exercicio8_PassagensAéreas.Entities;


    public class ClasseExecutiva extends Passagem {

        private boolean servicoBordoVip = true;
        private boolean acessoSalaVip = true;

        public ClasseExecutiva(String nomePassageiro,
                               String numeroVoo,
                               double precoOriginal) {

            super(nomePassageiro, numeroVoo, precoOriginal);
        }

        public double calcularPrecoFinal() {
            return getPrecoOriginal() * 1.5;
        }

        @Override
        public String toString() {
            return "===== BILHETE DE EMBARQUE =====" +
                    "Passageiro: " + getNomePassageiro() +
                    "Voo: " + getNumeroVoo() +
                    "Classe: Executiva" +
                    "Bagagem: 2 malas de até 23kg inclusas" +
                    "Serviço de Bordo VIP: " + (servicoBordoVip ? "Sim" : "Não") +
                    "Acesso à Sala VIP: " + (acessoSalaVip ? "Sim" : "Não") +
                    "Valor Final: R$ " + String.format("%.2f", calcularPrecoFinal());
        }
    }

