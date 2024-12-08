package controller;

import bo.MarcaBO;
import dto.MarcaDTO;

import java.sql.SQLException;

public class MarcaController {
    private MarcaBO marcaBO = new MarcaBO();

    public void inserirMarca(String nome) {
        MarcaDTO marca = new MarcaDTO();
        marca.setNome(nome);
        try {
            marcaBO.salvar(marca);
        } catch (SQLException e) {
            System.out.println("Erro ao salvar marca: " + e.getMessage());
        }
    }
}