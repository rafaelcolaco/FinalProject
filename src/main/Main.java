package main;

import bo.ClienteBO;
import bo.VendedorBO;
import conexao.Conexao;
import bo.VendaBO;
import dto.ClienteDTO;
import dto.VendaDTO;
import dto.VendedorDTO;
import dto.VeiculoDTO;
import dto.MarcaDTO;
import dao.VeiculoDAO;
import dao.MarcaDAO;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = Conexao.conectar();
        VeiculoDAO veiculoDAO = new VeiculoDAO(connection);


        if (connection == null) {
            System.out.println("Erro ao conectar ao banco de dados. Encerrando...");
            return;
        }

        System.out.println("Bem-vindo ao Sistema de Vendas de Veículos");
        System.out.println("Digite 1 para Cliente ou 2 para Vendedor:");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            ClienteBO clienteBO = new ClienteBO();
            System.out.println("Digite seu email:");
            String emailCliente = scanner.nextLine();
            System.out.println("Digite sua senha:");
            String senhaCliente = scanner.nextLine();

            ClienteDTO cliente = clienteBO.login(emailCliente, senhaCliente);
            if (cliente != null) {
                System.out.println("Bem-vindo, " + cliente.getNome() + "!");

                List<VeiculoDTO> veiculos = veiculoDAO.listar();

                if (veiculos.isEmpty()) {
                    System.out.println("Nenhum veículo disponível no momento.");
                } else {
                    System.out.println("Veículos disponíveis:");
                    for (VeiculoDTO veiculo : veiculos) {
                        System.out.println("ID: " + veiculo.getId() + ", Modelo: " + veiculo.getModelo() + ", Preço: " + veiculo.getPreco());
                    }
                }
            } else {
                System.out.println("Credenciais inválidas.");
            }
        } else if (escolha == 2) {
            VendedorBO vendedorBO = new VendedorBO(connection);
            System.out.println("Digite seu email de vendedor:");
            String emailVendedor = scanner.nextLine();
            System.out.println("Digite sua senha de vendedor:");
            String senhaVendedor = scanner.nextLine();
            
            int vendedorId = vendedorBO.buscarIdPeloEmail(emailVendedor, senhaVendedor);

            if (vendedorId != 0) {
                boolean continuar = true;
                
                while (continuar) {
                    System.out.println("Bem-vindo, Vendedor!");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Cadastrar marca");
                    System.out.println("2. Cadastrar veículo");
                    System.out.println("3. Efetivar venda");
                    System.out.println("4. Relatórios de Vendas");
                    System.out.println("5. Sair");
                    int escolhaVendedor = scanner.nextInt();
                    scanner.nextLine();

                    switch (escolhaVendedor) {
                        case 1:
                            System.out.println("Digite o nome da marca:");
                            String nomeMarca = scanner.nextLine();

                            MarcaDTO marcaDTO = new MarcaDTO();
                            marcaDTO.setNome(nomeMarca);

                            MarcaDAO marcaDAO = new MarcaDAO(connection);
                            marcaDAO.salvar(marcaDTO);
                            System.out.println("Marca registrada com sucesso!");
                            break;

                        case 2:
                            System.out.println("Digite o modelo do veículo:");
                            String modelo = scanner.nextLine();
                            System.out.println("Digite o preço do veículo:");
                            double preco = scanner.nextDouble();
                            System.out.println("Digite o ID da marca:");
                            int marcaId = scanner.nextInt();
                            scanner.nextLine();

                            VeiculoDTO veiculoDTO = new VeiculoDTO();
                            veiculoDTO.setModelo(modelo);
                            veiculoDTO.setPreco(preco);
                            veiculoDTO.setMarcaId(marcaId);

                            veiculoDAO.salvar(veiculoDTO);
                            System.out.println("Veículo cadastrado com sucesso!");
                            break;

                        case 3:
                            System.out.println("Lista de vendedores disponíveis:");
                            List<VendedorDTO> vendedores = vendedorBO.listarVendedores();
                            for (VendedorDTO vendedor : vendedores) {
                                System.out.println("ID: " + vendedor.getId() + ", Nome: " + vendedor.getNome());
                            }
                            System.out.println("Digite o ID do vendedor para a venda:");
                            int vendedorIdVenda = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Lista de clientes disponíveis:");
                            ClienteBO clienteBO = new ClienteBO();
                            List<ClienteDTO> clientes = clienteBO.listarClientes(connection);
                            for (ClienteDTO cliente : clientes) {
                                System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome());
                            }
                            System.out.println("Digite o ID do cliente para a venda:");
                            int clienteId = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Lista de veículos disponíveis:");
                            List<VeiculoDTO> veiculos = veiculoDAO.listar();
                            for (VeiculoDTO veiculo : veiculos) {
                                System.out.println("ID: " + veiculo.getId() + ", Modelo: " + veiculo.getModelo());
                            }
                            System.out.println("Digite o ID do veículo para a venda:");
                            int veiculoId = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Digite o preço final da venda:");
                            double precoFinal = scanner.nextDouble();
                            System.out.println("Digite o número de parcelas:");
                            int parcelas = scanner.nextInt();
                            scanner.nextLine();

                            VendaDTO novaVenda = new VendaDTO();
                            novaVenda.setVendedorId(vendedorIdVenda);
                            novaVenda.setClienteId(clienteId);
                            novaVenda.setVeiculoId(veiculoId);
                            novaVenda.setPrecoFinal(precoFinal);
                            novaVenda.setParcelas(parcelas);
                            novaVenda.setDataVenda("2024-12-15");

                            VendaBO vendaBO = new VendaBO(connection);
                            if (vendaBO.criarVenda(novaVenda)) {
                                System.out.println("Venda criada e registrada com sucesso!");
                            } else {
                                System.out.println("Erro ao registrar a venda.");
                            }
                            break;


                        case 4:
                            System.out.println("Escolha uma opção de relatório:");
                            System.out.println("1. Faturamento por ano");
                            System.out.println("2. Faturamento por mês");
                            System.out.println("3. Faturamento por dia");
                            System.out.println("4. Vendas por vendedor");
                            System.out.println("5. Vendas por cliente");
                            System.out.println("6. Carro mais vendido");
                            int opcaoRelatorio = scanner.nextInt();
                            scanner.nextLine();

                            VendaBO faturamentoBO = new VendaBO(connection);

                            switch (opcaoRelatorio) {
                                case 1:
                                    System.out.println("Digite o ano para o faturamento:");
                                    int ano = scanner.nextInt();
                                    scanner.nextLine();
                                    double faturamentoAno = faturamentoBO.faturamentoPorAno(ano);
                                    System.out.println("Faturamento no ano " + ano + ": R$ " + faturamentoAno);
                                    break;

                                case 2:
                                    System.out.println("Digite o mês para o faturamento:");
                                    int mes = scanner.nextInt();
                                    scanner.nextLine();
                                    double faturamentoMes = faturamentoBO.faturamentoPorMes(mes);
                                    System.out.println("Faturamento no mês " + mes + ": R$ " + faturamentoMes);
                                    break;

                                case 3:
                                    System.out.println("Digite o dia para o faturamento:");
                                    int dia = scanner.nextInt();
                                    scanner.nextLine();
                                    double faturamentoDia = faturamentoBO.faturamentoPorDia(dia);
                                    System.out.println("Faturamento no dia " + dia + ": R$ " + faturamentoDia);
                                    break;

                                case 4:
                                    System.out.println("Vendas por vendedor:");
                                    List<String> vendasVendedores = vendedorBO.vendasPorVendedor();
                                    for (String venda : vendasVendedores) {
                                        System.out.println(venda);
                                    }
                                    break;

                                case 5:
                                    System.out.println("Vendas por cliente:");
                                    List<String> vendasClientes = vendedorBO.vendasPorCliente();
                                    for (String venda : vendasClientes) {
                                        System.out.println(venda);
                                    }
                                    break;

                                case 6:
                                    System.out.println("Carro mais vendido:");
                                    List<String> carrosMaisVendidos = vendedorBO.carroMaisVendido();
                                    for (String carro : carrosMaisVendidos) {
                                        System.out.println(carro);
                                    }
                                    break;

                                default:
                                    System.out.println("Opção inválida.");
                                    break;
                            }
                            break;


                        case 5:
                            continuar = false;
                            System.out.println("Saindo...");
                            break;

                        default:
                            System.out.println("Opção inválida.");
                    }
                }
            } else {
                System.out.println("Credenciais de vendedor inválidas.");
            }
        } else {
            System.out.println("Opção inválida.");
        }

        scanner.close();
    }
}
