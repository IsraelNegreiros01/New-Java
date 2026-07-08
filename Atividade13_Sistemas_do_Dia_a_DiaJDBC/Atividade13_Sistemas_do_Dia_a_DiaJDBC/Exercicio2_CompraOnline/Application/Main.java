package Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Application;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DAO.ClienteDAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DAO.PagamentoDAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.DAO.PedidoDAO;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.Compra;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.CompraValor;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.Pagamento;
import Atividade13_Sistemas_do_Dia_a_DiaJDBC.Exercicio2_CompraOnline.Entities.StatusPedido;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.US);
            Scanner sc = new Scanner(System.in);
            ClienteDAO clienteDAO = new ClienteDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            Compra pedido = new CompraValor();
            System.out.println("===== COMPRA ONLINE =====");
            System.out.println("Escolha a tabela:");
            System.out.println("1 - Clientes");
            System.out.println("2 - Pedidos");
            System.out.println("3 - Pagamentos");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            int tabela = sc.nextInt();
            if (tabela == 4) {
                System.out.println("Programa encerrado.");
                return;
            }
            System.out.println();
            System.out.println("Escolha a operação:");
            System.out.println("1 - Inserir");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("5 - Sair");
            System.out.print("Opção: ");
            int operacao = sc.nextInt();
            if (operacao == 5) {
                System.out.println("Programa encerrado.");
                return;
            }
            sc.nextLine();
            switch (tabela) {
                case 1:
                    switch (operacao) {
                        case 1:
                            System.out.print("Nome: ");
                            pedido.setNome(sc.nextLine());
                            System.out.print("CPF: ");
                            pedido.setCpf(sc.nextLine());
                            System.out.print("Endereço: ");
                            pedido.setEndereco(sc.nextLine());
                            clienteDAO.inserirCliente(pedido);
                            System.out.println("Cliente inserido.");
                            break;
                        case 2:
                            clienteDAO.listarClientes();
                            break;
                        case 3:
                            System.out.print("Nome: ");
                            pedido.setNome(sc.nextLine());
                            System.out.print("CPF: ");
                            pedido.setCpf(sc.nextLine());
                            System.out.print("Endereço: ");
                            pedido.setEndereco(sc.nextLine());
                            clienteDAO.atualizarCliente(pedido);
                            System.out.println("Cliente atualizado.");
                            break;
                        case 4:
                            System.out.print("CPF: ");
                            String cpf = sc.nextLine();
                            clienteDAO.deletarCliente(cpf);
                            System.out.println("Cliente removido.");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
                case 2:
                    switch (operacao) {
                        case 1:
                            System.out.print("CPF: ");
                            pedido.setCpf(sc.nextLine());
                            System.out.print("Valor da compra: ");
                            pedido.setValorCompra(sc.nextDouble());
                            pedido.setFrete(pedido.calcularFrete());
                            System.out.print("Cartão aprovado (true/false): ");
                            pedido.setCartaoAprovado(sc.nextBoolean());
                            StatusPedido status = pedido.finalizarCompra();
                            pedido.setStatus(status);
                            pedidoDAO.inserirPedido(pedido);
                            System.out.println("Pedido inserido.");
                            break;
                        case 2:
                            pedidoDAO.listarPedidos();
                            break;
                        case 3:
                            System.out.print("CPF: ");
                            pedido.setCpf(sc.nextLine());
                            System.out.print("Valor da compra: ");
                            pedido.setValorCompra(sc.nextDouble());
                            pedido.setFrete(pedido.calcularFrete());
                            System.out.print("Status (COMPRA_REALIZADA / PAGAMENTO_RECUSADO): ");
                            pedido.setStatus(StatusPedido.valueOf(sc.next()));
                            pedidoDAO.atualizarPedido(pedido);
                            System.out.println("Pedido atualizado.");
                            break;
                        case 4:
                            System.out.print("Número do pedido: ");
                            int id = sc.nextInt();
                            pedidoDAO.deletarPedido(id);
                            System.out.println("Pedido removido.");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
                case 3:
                    switch (operacao) {
                        case 1:
                            Pagamento pagamento = new Pagamento();
                            System.out.print("Número do pedido: ");
                            pagamento.setNumeroPedido(sc.nextInt());
                            System.out.print("Cartão aprovado (true/false): ");
                            pagamento.setCartaoAprovado(sc.nextBoolean());
                            pagamentoDAO.inserirPagamento(pagamento);
                            System.out.println("Pagamento inserido.");
                            break;
                        case 2:
                            pagamentoDAO.listarPagamentos();
                            break;
                        case 3:
                            Pagamento pag = new Pagamento();
                            System.out.print("ID: ");
                            pag.setId(sc.nextInt());
                            System.out.print("Número do pedido: ");
                            pag.setNumeroPedido(sc.nextInt());
                            System.out.print("Cartão aprovado (true/false): ");
                            pag.setCartaoAprovado(sc.nextBoolean());
                            pagamentoDAO.atualizarPagamento(pag);
                            System.out.println("Pagamento atualizado.");
                            break;
                        case 4:
                            System.out.print("Número do pedido: ");
                            int num = sc.nextInt();
                            pagamentoDAO.deletarPagamento(num);
                            System.out.println("Pagamento removido.");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }
    }



