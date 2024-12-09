package main;

import bo.VeiculoBO;
import dao.VeiculoDAO;
import conexao.Conexao;

import java.util.Scanner;
import java.sql.Connection;

public class InserirVeiculo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = Conexao.conectar()) {
            VeiculoDAO veiculoDAO = new VeiculoDAO(connection);
            VeiculoBO veiculoBO = new VeiculoBO(veiculoDAO);

            System.out.print("Digite o modelo do veículo: ");
            String modeloVeiculo = scanner.nextLine();

            System.out.print("Digite o preço do veículo: ");
            double precoVeiculo = scanner.nextDouble();

            System.out.print("Digite o ID da marca do veículo: ");
            int marcaId = scanner.nextInt();

            if (veiculoBO.salvarVeiculo(modeloVeiculo, precoVeiculo, marcaId)) {
                System.out.println("Veículo inserido com sucesso!");
            } else {
                System.out.println("Erro ao inserir o veículo.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
