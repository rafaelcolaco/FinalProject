package dao;

import dto.VeiculoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private Connection connection;

    public VeiculoDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean salvar(VeiculoDTO veiculoDTO) {
        String sql = "INSERT INTO veiculo (modelo, preco, marca_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculoDTO.getModelo());
            stmt.setDouble(2, veiculoDTO.getPreco());
            stmt.setInt(3, veiculoDTO.getMarcaId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar veículo: " + e.getMessage());
            return false;
        }
    }

    public List<VeiculoDTO> listar() {
        List<VeiculoDTO> veiculos = new ArrayList<>();
        String sql = "SELECT v.id, v.modelo, v.preco, m.nome AS marca_nome FROM veiculo v JOIN marca m ON v.marca_id = m.id";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                VeiculoDTO veiculoDTO = new VeiculoDTO();
                veiculoDTO.setId(rs.getInt("id"));
                veiculoDTO.setModelo(rs.getString("modelo"));
                veiculoDTO.setPreco(rs.getDouble("preco"));
                veiculoDTO.setMarcaNome(rs.getString("marca_nome"));
                veiculos.add(veiculoDTO);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar veículos: " + e.getMessage());
        }

        return veiculos;
    }
}
