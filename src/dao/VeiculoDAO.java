package dao;

import conexao.Conexao;
import dto.VeiculoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VeiculoDAO {
    public void inserir(VeiculoDTO veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo (modelo, preco, marca_id) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getModelo());
            stmt.setDouble(2, veiculo.getPreco());
            stmt.setInt(3, veiculo.getId());
            stmt.executeUpdate();
        }
    }
}
