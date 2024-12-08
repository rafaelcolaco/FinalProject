package bo;

import dao.MarcaDAO;
import dto.MarcaDTO;

import java.sql.SQLException;

public class MarcaBO {
    private MarcaDAO marcaDAO = new MarcaDAO();

    public void salvar(MarcaDTO marca) throws SQLException {
        marcaDAO.inserir(marca);
    }
}
