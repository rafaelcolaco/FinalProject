package bo;

import dao.MarcaDAO;
import dto.MarcaDTO;

import java.util.List;

public class MarcaBO {
    private MarcaDAO marcaDAO;

    public MarcaBO(MarcaDAO marcaDAO) {
        this.marcaDAO = marcaDAO;
    }

    public boolean salvarMarca(MarcaDTO marca) {
        if (marca.getNome() == null || marca.getNome().isEmpty()) {
            System.out.println("Nome da marca não pode ser vazio!");
            return false;
        }
        return marcaDAO.salvar(marca);
    }

    public List<MarcaDTO> listarMarcas() {
    	return marcaDAO.listarMarcas();
    }

    public boolean atualizarMarca(MarcaDTO marca) {
        if (marca.getId() <= 0) {
            System.out.println("ID da marca inválido!");
            return false;
        }
        return marcaDAO.atualizar(marca);
    }

    public boolean excluirMarca(int id) {
        return marcaDAO.excluir(id);
    }
}
