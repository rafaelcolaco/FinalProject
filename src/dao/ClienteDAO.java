package dao;

import dto.ClienteDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarCliente(ClienteDTO cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, senha, ativo, pcd, dataCadastro) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setBoolean(4, cliente.isAtivo());
            stmt.setBoolean(5, cliente.isPcd());
            stmt.setDate(6, new java.sql.Date(cliente.getDataCadastro().getTime()));
            stmt.executeUpdate();
        }
    }

    public ClienteDTO buscarClientePorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        ClienteDTO cliente = null;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                cliente = new ClienteDTO();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setAtivo(rs.getBoolean("ativo"));
                cliente.setPcd(rs.getBoolean("pcd"));
                cliente.setDataCadastro(rs.getDate("dataCadastro"));
            }
        }
        return cliente;
    }

    public List<ClienteDTO> listarClientesAtivos() throws SQLException {
        String sql = "SELECT * FROM cliente WHERE ativo = true";
        List<ClienteDTO> clientes = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setAtivo(rs.getBoolean("ativo"));
                cliente.setPcd(rs.getBoolean("pcd"));
                cliente.setDataCadastro(rs.getDate("dataCadastro"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public void desativarCliente(int id) throws SQLException {
        String sql = "UPDATE cliente SET ativo = false WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizarCliente(ClienteDTO cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, email = ?, senha = ?, ativo = ?, pcd = ?, dataCadastro = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setBoolean(4, cliente.isAtivo());
            stmt.setBoolean(5, cliente.isPcd());
            stmt.setDate(6, new java.sql.Date(cliente.getDataCadastro().getTime()));
            stmt.setInt(7, cliente.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirCliente(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public ClienteDTO buscarPorEmail(String email) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ClienteDTO cliente = new ClienteDTO();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setAtivo(rs.getBoolean("ativo"));
                    cliente.setPcd(rs.getBoolean("pcd"));
                    cliente.setDataCadastro(rs.getTimestamp("dataCadastro"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente por email: " + e.getMessage());
        }
        return null;
    }
    
    public List<ClienteDTO> listar() {
        String sql = "SELECT * FROM cliente";
        List<ClienteDTO> clientes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public ClienteDTO buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ClienteDTO cliente = new ClienteDTO();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }
    
    public boolean cadastrar(ClienteDTO cliente) {
        String sql = "INSERT INTO cliente (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            return false;
        }
    }
    
    
    public List<ClienteDTO> listarnova() {
        String sql = "SELECT id, nome, email FROM clientes";
        List<ClienteDTO> clientes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
