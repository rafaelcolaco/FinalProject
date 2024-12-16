package dao;

import dto.VeiculoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private Connection connection;

    public VeiculoDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean salvar(VeiculoDTO veiculo) {
        String sql = "INSERT INTO veiculo (modelo, preco, marca_id, pcd) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getModelo());   
            stmt.setDouble(2, veiculo.getPreco());   
            stmt.setInt(3, veiculo.getMarcaId());   
            stmt.setBoolean(4, veiculo.isPcd());  
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            System.out.println("Erro ao salvar o veículo: " + e.getMessage());
            return false; 
        }
    }

    public List<VeiculoDTO> listar() {
        String sql = "SELECT * FROM veiculo";
        List<VeiculoDTO> veiculos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                VeiculoDTO veiculo = new VeiculoDTO();
                veiculo.setId(rs.getInt("id"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setPreco(rs.getDouble("preco"));
                veiculo.setPcd(rs.getBoolean("pcd"));
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar veículos: " + e.getMessage());
        }
        return veiculos;
    }

    public boolean atualizar(VeiculoDTO veiculo) {
        String sql = "UPDATE veiculo SET modelo = ?, preco = ?, pcd = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getModelo());
            stmt.setDouble(2, veiculo.getPreco());
            stmt.setBoolean(3, veiculo.isPcd());
            stmt.setInt(4, veiculo.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar veículo: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM veiculo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir veículo: " + e.getMessage());
            return false;
        }
    }
    
    
}
