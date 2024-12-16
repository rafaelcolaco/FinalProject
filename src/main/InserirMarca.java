package main;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import bo.MarcaBO;
import conexao.Conexao;
import dto.MarcaDTO;
import dao.MarcaDAO;

public class InserirMarca {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = Conexao.conectar()) {

            MarcaDAO marcaDAO = new MarcaDAO(connection);
            MarcaBO marcaBO = new MarcaBO(marcaDAO);

            boolean continuar = true;
            while (continuar) {
                System.out.println("Sistema de Gestão de Marcas");
                System.out.println("Escolha uma opção:");
                System.out.println("1. Inserir Nova Marca");
                System.out.println("2. Listar Marcas");
                System.out.println("3. Atualizar Marca");
                System.out.println("4. Excluir Marca");
                System.out.println("5. Sair");
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.print("Nome da Marca: ");
                        String nomeMarca = scanner.nextLine();

                        MarcaDTO marca = new MarcaDTO();
                        marca.setNome(nomeMarca);

                        if (marcaBO.salvarMarca(marca)) {
                            System.out.println("Marca inserida com sucesso!");
                        } else {
                            System.out.println("Erro ao inserir a marca.");
                        }
                        break;

                    case 2:
                        List<MarcaDTO> marcas = marcaBO.listarMarcas();
                        if (marcas.isEmpty()) {
                            System.out.println("Nenhuma marca cadastrada.");
                        } else {
                            System.out.println("Lista de Marcas:");
                            for (MarcaDTO m : marcas) {
                                System.out.println("ID: " + m.getId() + ", Nome: " + m.getNome());
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Digite o ID da marca que deseja atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Novo nome da Marca: ");
                        String novoNome = scanner.nextLine();

                        MarcaDTO marcaAtualizada = new MarcaDTO();
                        marcaAtualizada.setId(idAtualizar);
                        marcaAtualizada.setNome(novoNome);

                        if (marcaBO.atualizarMarca(marcaAtualizada)) {
                            System.out.println("Marca atualizada com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar a marca.");
                        }
                        break;

                    case 4:
                        System.out.print("Digite o ID da marca que deseja excluir: ");
                        int idExcluir = scanner.nextInt();
                        scanner.nextLine();

                        if (marcaBO.excluirMarca(idExcluir)) {
                            System.out.println("Marca excluída com sucesso!");
                        } else {
                            System.out.println("Erro ao excluir a marca.");
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
