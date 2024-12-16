package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexao.Conexao;
import dto.VendedorDTO;

public class VendedorDAO {

    private Connection connection;

    public VendedorDAO() {
        this.connection = Conexao.conectar();
    }

    public void inserirVendedor(VendedorDTO vendedor) throws SQLException {
        String sql = "INSERT INTO vendedor (nome, email, senha, ativo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getEmail());
            stmt.setString(3, vendedor.getSenha());
            stmt.setBoolean(4, vendedor.isAtivo());
            stmt.executeUpdate();
        }
    }

    public List<VendedorDTO> buscarVendedores() throws SQLException {
        List<VendedorDTO> vendedores = new ArrayList<>();
        String sql = "SELECT * FROM vendedor";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                VendedorDTO vendedor = new VendedorDTO();
                vendedor.setId(rs.getInt("id"));
                vendedor.setNome(rs.getString("nome"));
                vendedor.setEmail(rs.getString("email"));
                vendedor.setSenha(rs.getString("senha"));
                vendedor.setAtivo(rs.getBoolean("ativo"));
                vendedores.add(vendedor);
            }
        }
        return vendedores;
    }

    public void atualizarVendedor(VendedorDTO vendedor) throws SQLException {
        String sql = "UPDATE vendedor SET nome = ?, email = ?, senha = ?, ativo = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getEmail());
            stmt.setString(3, vendedor.getSenha());
            stmt.setBoolean(4, vendedor.isAtivo());
            stmt.setInt(5, vendedor.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarVendedor(int id) throws SQLException {
        String sql = "DELETE FROM vendedor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public VendedorDTO buscarPorEmail(String email) {
        String sql = "SELECT * FROM vendedor WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    VendedorDTO vendedor = new VendedorDTO();
                    vendedor.setId(rs.getInt("id"));
                    vendedor.setNome(rs.getString("nome"));
                    vendedor.setEmail(rs.getString("email"));
                    vendedor.setSenha(rs.getString("senha"));
                    vendedor.setAtivo(rs.getBoolean("ativo"));
                    return vendedor;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendedor por email: " + e.getMessage());
        }
        return null;
    }
    
    
    
    public List<VendedorDTO> listar() {
        String sql = "SELECT id, nome, email, ativo FROM vendedores";
        List<VendedorDTO> vendedores = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                VendedorDTO vendedor = new VendedorDTO();
                vendedor.setId(rs.getInt("id"));
                vendedor.setNome(rs.getString("nome"));
                vendedor.setEmail(rs.getString("email"));
                vendedor.setAtivo(rs.getBoolean("ativo"));
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendedores;
    }
    
}
