package dao;

import conexao.Conexao;
import dto.MarcaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarcaDAO {
    public void inserir(MarcaDTO marca) throws SQLException {
        String sql = "INSERT INTO marca (nome) VALUES (?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            stmt.executeUpdate();
        }
    }
}
