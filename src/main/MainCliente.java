package main;

import bo.ClienteBO;
import conexao.Conexao;
import dto.ClienteDTO;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class MainCliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = Conexao.conectar()) {
            ClienteBO clienteBO = new ClienteBO();
            int opcao;

            do {
                System.out.println("### MENU CLIENTE ###");
                System.out.println("1 - Cadastrar Cliente");
                System.out.println("2 - Mostrar Clientes");
                System.out.println("3 - Atualizar Cliente");
                System.out.println("4 - Deletar Cliente");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarCliente(clienteBO, scanner);
                        break;
                    case 2:
                        mostrarClientes(clienteBO);
                        break;
                    case 3:
                        atualizarCliente(clienteBO, scanner);
                        break;
                    case 4:
                        deletarCliente(clienteBO, scanner);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarCliente(ClienteBO clienteBO, Scanner scanner) {
        ClienteDTO cliente = new ClienteDTO();

        System.out.print("Nome: ");
        cliente.setNome(scanner.nextLine());

        System.out.print("Email: ");
        cliente.setEmail(scanner.nextLine());

        System.out.print("Senha: ");
        cliente.setSenha(scanner.nextLine());

        System.out.print("Ativo (true/false): ");
        cliente.setAtivo(scanner.nextBoolean());

        System.out.print("PCD (true/false): ");
        cliente.setPcd(scanner.nextBoolean());

        cliente.setDataCadastro(new java.util.Date());

        try {
            clienteBO.cadastrarCliente(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void mostrarClientes(ClienteBO clienteBO) {
        try {
            List<ClienteDTO> clientes = clienteBO.listarClientesAtivos();
            if (clientes.isEmpty()) {
                System.out.println("Não há clientes cadastrados.");
            } else {
                for (ClienteDTO cliente : clientes) {
                    System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome() + ", Email: " + cliente.getEmail() + ", Ativo: " + cliente.isAtivo());
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private static void atualizarCliente(ClienteBO clienteBO, Scanner scanner) {
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            ClienteDTO cliente = clienteBO.buscarClientePorId(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            System.out.print("Novo nome (deixe em branco para não alterar): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                cliente.setNome(nome);
            }

            System.out.print("Novo email (deixe em branco para não alterar): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                cliente.setEmail(email);
            }

            System.out.print("Nova senha (deixe em branco para não alterar): ");
            String senha = scanner.nextLine();
            if (!senha.isEmpty()) {
                cliente.setSenha(senha);
            }

            System.out.print("Novo status ativo (true/false): ");
            cliente.setAtivo(scanner.nextBoolean());

            clienteBO.atualizarCliente(cliente);
            System.out.println("Cliente atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    private static void deletarCliente(ClienteBO clienteBO, Scanner scanner) {
        System.out.print("Digite o ID do cliente a ser deletado: ");
        int id = scanner.nextInt();

        try {
            ClienteDTO cliente = clienteBO.buscarClientePorId(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            clienteBO.desativarCliente(id);
            System.out.println("Cliente deletado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }
}

