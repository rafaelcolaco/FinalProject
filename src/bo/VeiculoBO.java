package bo;

import dao.VeiculoDAO;
import dto.VeiculoDTO;
import java.util.List;

public class VeiculoBO {
    private VeiculoDAO veiculoDAO;

    public VeiculoBO(VeiculoDAO veiculoDAO) {
        this.veiculoDAO = veiculoDAO;
    }

    public void salvarVeiculo(VeiculoDTO veiculo) {
        veiculoDAO.salvar(veiculo); 
    }

    public List<VeiculoDTO> listarVeiculos() {
        return veiculoDAO.listar();
    }

    public void atualizarVeiculo(VeiculoDTO veiculo) {
        veiculoDAO.atualizar(veiculo);
    }

    public void excluirVeiculo(int id) {
        veiculoDAO.excluir(id);
    }
}
