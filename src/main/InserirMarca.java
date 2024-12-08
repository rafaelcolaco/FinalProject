package main;

import controller.MarcaController;

import java.util.Scanner;

public class InserirMarca {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome da marca:");
        String nome = scanner.nextLine();

        MarcaController marcaController = new MarcaController();
        marcaController.inserirMarca(nome);
        scanner.close();
    }
}
