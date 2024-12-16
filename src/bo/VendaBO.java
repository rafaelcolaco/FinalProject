package bo;

import dao.VendaDAO;
import dto.VendaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendaBO {
    private Connection connection;
    private VendaDAO vendaDAO;

    public VendaBO(Connection connection) {
        this.connection = connection;
        this.vendaDAO = new VendaDAO();
    }

    public boolean efetivarVenda(VendaDTO venda) {
        try {
            if (venda.getClienteId() <= 0 || venda.getVendedorId() <= 0 || venda.getPrecoFinal() <= 0 || venda.getParcelas() <= 0) {
                System.out.println("Dados inválidos para efetivar a venda.");
                return false; 
            }

            vendaDAO.inserirVenda(venda);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao efetivar a venda: " + e.getMessage());
            return false; 
        }
    }

    public List<VendaDTO> listarVendas() {
        try {
            return vendaDAO.buscarVendas();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas: " + e.getMessage());
            return null;
        }
    }

    public List<VendaDTO> listarVendasPorCliente(int clienteId) {
        try {
            return vendaDAO.buscarVendasPorCliente(clienteId);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas por cliente: " + e.getMessage());
            return null;
        }
    }

    public boolean criarVenda(VendaDTO venda) {
        String query = "INSERT INTO venda (cliente_id, veiculo_id, vendedor_id, preco_final, parcelas, data_venda) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, venda.getClienteId());
            stmt.setInt(2, venda.getVeiculoId());
            stmt.setInt(3, venda.getVendedorId());
            stmt.setDouble(4, venda.getPrecoFinal());
            stmt.setInt(5, venda.getParcelas());
            stmt.setString(6, venda.getDataVenda());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public double faturamentoPorAno(int ano) {
        String query = "SELECT SUM(preco_final) FROM venda WHERE YEAR(data_venda) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ano);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double faturamentoPorMes(int mes) {
        String query = "SELECT SUM(preco_final) FROM venda WHERE MONTH(data_venda) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, mes);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double faturamentoPorDia(int dia) {
        String query = "SELECT SUM(preco_final) FROM venda WHERE DAY(data_venda) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, dia);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
}
