package dao;

import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import dto.VendaDTO;

import java.sql.*;

public class VendaDAO {

    private Connection connection;

    public VendaDAO() {
        this.connection = Conexao.conectar();
    }

    public boolean inserirVenda(VendaDTO venda) throws SQLException {
        String sql = "INSERT INTO venda (cliente_id, vendedor_id, interesse_id, preco_final, parcelas, data_venda, veiculo_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venda.getClienteId());
            stmt.setInt(2, venda.getVendedorId());
            stmt.setInt(3, venda.getInteresseId());
            stmt.setDouble(4, venda.getPrecoFinal());
            stmt.setInt(5, venda.getParcelas());
            stmt.setString(6, venda.getDataVenda());
            stmt.setInt(7, venda.getVeiculoId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao registrar a venda: " + e.getMessage());
            return false;
        }
    }

    public List<VendaDTO> buscarVendas() throws SQLException {
        List<VendaDTO> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                VendaDTO venda = new VendaDTO();
                venda.setId(rs.getInt("id"));
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setVendedorId(rs.getInt("vendedor_id"));
                venda.setDataVenda(rs.getString("data_venda"));
                venda.setPrecoFinal(rs.getDouble("preco_final"));
                venda.setParcelas(rs.getInt("parcelas"));
                
                vendas.add(venda);
            }
        }
        return vendas;
    }

    public List<VendaDTO> buscarVendasPorCliente(int clienteId) throws SQLException {
        List<VendaDTO> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda WHERE cliente_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendaDTO venda = new VendaDTO();
                venda.setId(rs.getInt("id"));
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setVendedorId(rs.getInt("vendedor_id"));
                venda.setDataVenda(rs.getString("data_venda"));
                venda.setPrecoFinal(rs.getDouble("preco_final"));
                venda.setParcelas(rs.getInt("parcelas"));
                
                vendas.add(venda);
            }
        }
        return vendas;
    }
}
