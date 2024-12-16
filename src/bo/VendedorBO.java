package bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import dao.MarcaDAO;
import dao.VeiculoDAO;
import dao.VendedorDAO;
import dto.ClienteDTO;
import dto.MarcaDTO;
import dto.VeiculoDTO;
import dto.VendedorDTO;

public class VendedorBO {

    private VendedorDAO vendedorDAO;
    private Connection connection;

    public VendedorBO(Connection connection) {
        this.connection = connection;
        this.vendedorDAO = new VendedorDAO();
    }

    public void cadastrarVendedor(VendedorDTO vendedor) {
        try {
            vendedorDAO.inserirVendedor(vendedor);
            System.out.println("Vendedor cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar vendedor: " + e.getMessage());
        }
    }
    
    public List<VendedorDTO> listarVendedores() {
        try {
            return vendedorDAO.buscarVendedores();
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendedores: " + e.getMessage());
            return null;
        }
    }

    public void atualizarVendedor(VendedorDTO vendedor) {
        try {
            vendedorDAO.atualizarVendedor(vendedor);
            System.out.println("Vendedor atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar vendedor: " + e.getMessage());
        }
    }

    public void deletarVendedor(int id) {
        try {
            vendedorDAO.deletarVendedor(id);
            System.out.println("Vendedor deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar vendedor: " + e.getMessage());
        }
    }
    
    public boolean login(String email, String senha) {
        VendedorDTO vendedor = vendedorDAO.buscarPorEmail(email);
        if (vendedor != null && vendedor.getSenha().equals(senha)) {
            return true; 
        }
        return false; 
    }
    
    public boolean cadastrarMarca(String nomeMarca) {
        MarcaDAO marcaDAO = new MarcaDAO(connection);
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setNome(nomeMarca);
        
        return marcaDAO.salvar(marcaDTO);
    }
    
    public boolean cadastrarVeiculo(VeiculoDTO veiculoDTO) {
        VeiculoDAO veiculoDAO = new VeiculoDAO(connection); 
        return veiculoDAO.salvar(veiculoDTO); 
    }
    
    public boolean cadastrarCliente(ClienteDTO cliente) {
        ClienteDAO clienteDAO = new ClienteDAO(connection);
        return clienteDAO.cadastrar(cliente);
    }
    
    
    public int buscarIdPeloEmail(String email, String senha) {
        String sql = "SELECT id FROM vendedor WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<String> vendasPorVendedor() {
        List<String> vendas = new ArrayList<>();
        String query = "SELECT v.nome AS vendedor, c.nome AS cliente, ve.modelo AS veiculo, vl.preco_final, vl.data_venda " +
                       "FROM venda vl " +
                       "JOIN vendedor v ON vl.vendedor_id = v.id " +
                       "JOIN cliente c ON vl.cliente_id = c.id " +
                       "JOIN veiculo ve ON vl.veiculo_id = ve.id " +
                       "ORDER BY v.nome";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String venda = String.format("Vendedor: %s | Cliente: %s | Veículo: %s | Preço Final: R$ %.2f | Data: %s",
                        rs.getString("vendedor"), rs.getString("cliente"), rs.getString("veiculo"),
                        rs.getDouble("preco_final"), rs.getString("data_venda"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendas;
    }
    
    public List<String> vendasPorCliente() {
        List<String> vendas = new ArrayList<>();
        String query = "SELECT c.nome AS cliente, v.nome AS vendedor, ve.modelo AS veiculo, vl.preco_final, vl.data_venda " +
                       "FROM venda vl " +
                       "JOIN cliente c ON vl.cliente_id = c.id " +
                       "JOIN vendedor v ON vl.vendedor_id = v.id " +
                       "JOIN veiculo ve ON vl.veiculo_id = ve.id " +
                       "ORDER BY c.nome";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String venda = String.format("Cliente: %s | Vendedor: %s | Veículo: %s | Preço Final: R$ %.2f | Data: %s",
                        rs.getString("cliente"), rs.getString("vendedor"), rs.getString("veiculo"),
                        rs.getDouble("preco_final"), rs.getString("data_venda"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendas;
    }

    
    public List<String> carroMaisVendido() {
        List<String> carrosMaisVendidos = new ArrayList<>();
        String query = "SELECT ve.modelo AS veiculo, COUNT(vl.veiculo_id) AS numero_vendas " +
                       "FROM venda vl " +
                       "JOIN veiculo ve ON vl.veiculo_id = ve.id " +
                       "GROUP BY vl.veiculo_id " +
                       "ORDER BY numero_vendas DESC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String carro = String.format("Veículo: %s | Número de Vendas: %d",
                        rs.getString("veiculo"), rs.getInt("numero_vendas"));
                carrosMaisVendidos.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carrosMaisVendidos;
    }

}
