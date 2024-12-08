package controller;

import bo.VeiculoBO;
import dto.VeiculoDTO;

import java.sql.SQLException;

public class VeiculoController {
    private VeiculoBO veiculoBO = new VeiculoBO();

    public void inserirVeiculo(String modelo, double preco, int marcaId) {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setModelo(modelo);
        veiculo.setPreco(preco);
        veiculo.setId(marcaId);
        try {
            veiculoBO.salvar(veiculo);
        } catch (SQLException e) {
            System.out.println("Erro ao salvar veículo: " + e.getMessage());
        }
    }
}