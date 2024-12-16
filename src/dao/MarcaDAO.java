package dao;

import dto.MarcaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {
    private Connection connection;

    public MarcaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean salvar(MarcaDTO marca) {
        String sql = "INSERT INTO marca (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar marca: " + e.getMessage());
            return false;
        }
    }

    public List<MarcaDTO> listarMarcas() {
        List<MarcaDTO> marcas = new ArrayList<>();
        String sql = "SELECT * FROM marca";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MarcaDTO marca = new MarcaDTO();
                marca.setId(rs.getInt("id"));
                marca.setNome(rs.getString("nome"));
                marcas.add(marca);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar marcas: " + e.getMessage());
        }
        return marcas;
    }

    public boolean atualizar(MarcaDTO marca) {
        String sql = "UPDATE marca SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar marca: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM marca WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir marca: " + e.getMessage());
            return false;
        }
    }
}
