package main;

import conexao.Conexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class VisualizarDados {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 1 para visualizar marcas ou 2 para visualizar veículos:");
        int escolha = scanner.nextInt();

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            if (escolha == 1) {
                System.out.println("Marcas cadastradas:");
                String sql = "SELECT * FROM marca";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome"));
                }
            } else if (escolha == 2) {
                System.out.println("Veículos cadastrados:");
                String sql = "SELECT veiculo.id, veiculo.modelo, veiculo.preco, marca.nome AS marca_nome " +
                             "FROM veiculo " +
                             "JOIN marca ON veiculo.marca_id = marca.id";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                                       ", Modelo: " + rs.getString("modelo") +
                                       ", Preço: R$" + rs.getDouble("preco") +
                                       ", Marca: " + rs.getString("marca_nome"));
                }
            } else {
                System.out.println("Opção inválida.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao visualizar dados: " + e.getMessage());
        }
        scanner.close();
    }
}
