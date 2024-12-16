package main;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import bo.VeiculoBO;
import conexao.Conexao;
import dto.VeiculoDTO;
import dao.VeiculoDAO;
import dao.MarcaDAO;
import dto.MarcaDTO;

public class InserirVeiculo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = Conexao.conectar()) {
            
            VeiculoDAO veiculoDAO = new VeiculoDAO(connection);
            VeiculoBO veiculoBO = new VeiculoBO(veiculoDAO);
            MarcaDAO marcaDAO = new MarcaDAO(connection);

            boolean continuar = true;
            while (continuar) {
                System.out.println("Sistema de Gestão de Veículos");
                System.out.println("Escolha uma opção:");
                System.out.println("1. Inserir Novo Veículo");
                System.out.println("2. Listar Veículos");
                System.out.println("3. Atualizar Veículo");
                System.out.println("4. Excluir Veículo");
                System.out.println("5. Sair");
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        List<MarcaDTO> marcas = marcaDAO.listarMarcas();
                        System.out.println("Escolha uma marca:");
                        for (int i = 0; i < marcas.size(); i++) {
                            System.out.println((i + 1) + ". " + marcas.get(i).getNome());
                        }

                        System.out.print("Digite o número da marca: ");
                        int marcaEscolhida = scanner.nextInt();
                        scanner.nextLine();

                        MarcaDTO marcaSelecionada = marcas.get(marcaEscolhida - 1);

                        VeiculoDTO veiculo = new VeiculoDTO();
                        System.out.print("Modelo do Veículo: ");
                        veiculo.setModelo(scanner.nextLine());
                        System.out.print("Preço do Veículo: ");
                        veiculo.setPreco(scanner.nextDouble());
                        System.out.print("PCD (true/false): ");
                        veiculo.setPcd(scanner.nextBoolean());

                        veiculo.setMarcaId(marcaSelecionada.getId());

                        veiculoBO.salvarVeiculo(veiculo); 
                        System.out.println("Veículo inserido com sucesso!");
                        break;

                    case 2:
                        List<VeiculoDTO> veiculos = veiculoBO.listarVeiculos();
                        if (veiculos.isEmpty()) {
                            System.out.println("Nenhum veículo cadastrado.");
                        } else {
                            System.out.println("Lista de Veículos:");
                            for (VeiculoDTO v : veiculos) {
                                System.out.println("ID: " + v.getId() + ", Modelo: " + v.getModelo() + ", Preço: " + v.getPreco());
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Digite o ID do veículo que deseja atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Novo modelo do Veículo: ");
                        String novoModelo = scanner.nextLine();
                        System.out.print("Novo preço do Veículo: ");
                        double novoPreco = scanner.nextDouble();
                        System.out.print("PCD (true/false): ");
                        boolean novoPcd = scanner.nextBoolean();

                        VeiculoDTO veiculoAtualizado = new VeiculoDTO();
                        veiculoAtualizado.setId(idAtualizar);
                        veiculoAtualizado.setModelo(novoModelo);
                        veiculoAtualizado.setPreco(novoPreco);
                        veiculoAtualizado.setPcd(novoPcd);

                        veiculoBO.atualizarVeiculo(veiculoAtualizado);
                        System.out.println("Veículo atualizado com sucesso!");
                        break;

                    case 4:
                        System.out.print("Digite o ID do veículo que deseja excluir: ");
                        int idExcluir = scanner.nextInt();
                        scanner.nextLine();

                        veiculoBO.excluirVeiculo(idExcluir);
                        System.out.println("Veículo excluído com sucesso!");
                        break;

                    case 5:
                        continuar = false;
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
