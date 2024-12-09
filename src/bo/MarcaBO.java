package bo;

import dao.MarcaDAO;
import dto.MarcaDTO;

import java.util.List;

public class MarcaBO {
    private final MarcaDAO marcaDAO;

    public MarcaBO(MarcaDAO marcaDAO) {
        this.marcaDAO = marcaDAO;
    }

    public boolean salvarMarca(String nome) {
        MarcaDTO marca = new MarcaDTO();
        marca.setNome(nome);
        return marcaDAO.salvar(marca);
    }

    public List<MarcaDTO> listarMarcas() {
        return marcaDAO.listar();
    }
}
