package main;

import java.util.Scanner;
import conexao.Conexao;
import bo.VendedorBO;
import dto.VendedorDTO;
import java.sql.*;

public class MainVendedor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection = Conexao.conectar();
        if (connection == null) {
            System.out.println("Erro ao conectar ao banco de dados. Encerrando...");
        }

        VendedorBO vendedorBO = new VendedorBO(connection);

        while (true) {
            System.out.println("===== Menu Vendedor =====");
            System.out.println("1. Cadastrar Vendedor");
            System.out.println("2. Mostrar Vendedores");
            System.out.println("3. Atualizar Vendedor");
            System.out.println("4. Deletar Vendedor");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    VendedorDTO vendedor = new VendedorDTO();
                    System.out.print("Nome: ");
                    vendedor.setNome(scanner.nextLine());
                    System.out.print("Email: ");
                    vendedor.setEmail(scanner.nextLine());
                    System.out.print("Senha: ");
                    vendedor.setSenha(scanner.nextLine());
                    System.out.print("Ativo (true/false): ");
                    vendedor.setAtivo(scanner.nextBoolean());
                    scanner.nextLine(); 
                    vendedorBO.cadastrarVendedor(vendedor);
                    break;

                case 2:
                    System.out.println("Vendedores cadastrados:");
                    for (VendedorDTO v : vendedorBO.listarVendedores()) {
                        System.out.println("ID: " + v.getId() + ", Nome: " + v.getNome() + ", Email: " + v.getEmail() + ", Ativo: " + v.isAtivo());
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID do Vendedor que deseja atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    VendedorDTO vendedorAtualizado = new VendedorDTO();
                    vendedorAtualizado.setId(idAtualizar);
                    System.out.print("Novo Nome: ");
                    vendedorAtualizado.setNome(scanner.nextLine());
                    System.out.print("Novo Email: ");
                    vendedorAtualizado.setEmail(scanner.nextLine());
                    System.out.print("Nova Senha: ");
                    vendedorAtualizado.setSenha(scanner.nextLine());
                    System.out.print("Ativo (true/false): ");
                    vendedorAtualizado.setAtivo(scanner.nextBoolean());
                    scanner.nextLine();
                    vendedorBO.atualizarVendedor(vendedorAtualizado);
                    break;

                case 4:
                    System.out.print("Digite o ID do Vendedor que deseja deletar: ");
                    int idDeletar = scanner.nextInt();
                    vendedorBO.deletarVendedor(idDeletar);
                    break;

                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}
