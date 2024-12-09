package bo;

import dao.VeiculoDAO;
import dto.VeiculoDTO;
import java.util.List;

public class VeiculoBO {

    private VeiculoDAO veiculoDAO;

    public VeiculoBO(VeiculoDAO veiculoDAO) {
        this.veiculoDAO = veiculoDAO;
    }

    public boolean salvarVeiculo(String modelo, double preco, int marcaId) {
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setModelo(modelo);
        veiculoDTO.setPreco(preco);
        veiculoDTO.setMarcaId(marcaId);

        return veiculoDAO.salvar(veiculoDTO);
    }

    public List<VeiculoDTO> listarVeiculos() {
        return veiculoDAO.listar();
    }
}
