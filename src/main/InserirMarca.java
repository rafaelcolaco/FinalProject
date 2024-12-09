package main;

import bo.MarcaBO;
import dao.MarcaDAO;
import conexao.Conexao;

import java.util.Scanner;

public class InserirMarca {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MarcaBO marcaBO = new MarcaBO(new MarcaDAO(Conexao.conectar()));

        System.out.print("Digite o nome da marca: ");
        String nomeMarca = scanner.nextLine();

        if (marcaBO.salvarMarca(nomeMarca)) {
            System.out.println("Marca inserida com sucesso!");
        } else {
            System.out.println("Erro ao inserir a marca.");
        }
        scanner.close();
    }
}
