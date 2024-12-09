package dao;

import dto.MarcaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    private final Connection conn;

    public MarcaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(MarcaDTO marca) {
        String sql = "INSERT INTO marca (nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao salvar marca: " + e.getMessage());
            return false;
        }
    }

    public List<MarcaDTO> listar() {
        String sql = "SELECT * FROM marca";
        List<MarcaDTO> marcas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MarcaDTO marca = new MarcaDTO();
                marca.setId(rs.getInt("id"));
                marca.setNome(rs.getString("nome"));
                marcas.add(marca);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar marcas: " + e.getMessage());
        }
        return marcas;
    }
}
