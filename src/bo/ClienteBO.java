package bo;

import dao.ClienteDAO;
import dto.ClienteDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import conexao.Conexao;

public class ClienteBO {

	private ClienteDAO clienteDAO;

	public ClienteBO() {
		Connection connection = Conexao.conectar(); 
		this.clienteDAO = new ClienteDAO(connection);
	}

	public void cadastrarCliente(ClienteDTO cliente) throws SQLException {
		if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
			throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
		}
		if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Email do cliente não pode ser vazio.");
		}
		if (cliente.isPcd()) {
		}

		clienteDAO.cadastrarCliente(cliente);
	}

	public void atualizarCliente(ClienteDTO cliente) throws SQLException {
		if (cliente.getId() <= 0) {
			throw new IllegalArgumentException("ID do cliente inválido.");
		}

		clienteDAO.atualizarCliente(cliente);
	}
	

	public void desativarCliente(int id) throws SQLException {
		clienteDAO.desativarCliente(id);
	}

	public List<ClienteDTO> listarClientesAtivos() throws SQLException {
		return clienteDAO.listarClientesAtivos();
	}

	public ClienteDTO buscarClientePorId(int id) throws SQLException {
		return clienteDAO.buscarClientePorId(id);
	}

	public boolean isClienteAtivo(int id) throws SQLException {
		ClienteDTO cliente = clienteDAO.buscarClientePorId(id);
		return cliente != null && cliente.isAtivo();
	}
	public ClienteDTO login(String email, String senha) {
        ClienteDTO cliente = clienteDAO.buscarPorEmail(email);
        
        if (cliente != null && cliente.getSenha().equals(senha)) {
            return cliente;
        } else {
            return null; 
        }
    }
	
	public List<ClienteDTO> listarClientes(Connection connection) {
	    ClienteDAO clienteDAO = new ClienteDAO(connection);
	    return clienteDAO.listar();
	}

}