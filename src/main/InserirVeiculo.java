package main;

import controller.VeiculoController;

import java.util.Scanner;

public class InserirVeiculo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o modelo do veículo:");
        String modelo = scanner.nextLine();

        System.out.println("Digite o preço do veículo:");
        double preco = scanner.nextDouble();

        System.out.println("Digite o ID da marca:");
        int marcaId = scanner.nextInt();

        VeiculoController veiculoController = new VeiculoController();
        veiculoController.inserirVeiculo(modelo, preco, marcaId);
        scanner.close();
    }
}
