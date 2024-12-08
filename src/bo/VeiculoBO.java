package bo;

import dao.VeiculoDAO;
import dto.VeiculoDTO;

import java.sql.SQLException;

public class VeiculoBO {
    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public void salvar(VeiculoDTO veiculo) throws SQLException {
        veiculoDAO.inserir(veiculo);
    }
}
