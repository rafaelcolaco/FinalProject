package dto;

public class VeiculoDTO {
    private int id;
    private String modelo;
    private double preco;
    private int marcaId;
    private String marcaNome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(int marcaId) {
        this.marcaId = marcaId;
    }

    public String getMarcaNome() {
        return marcaNome;
    }

    public void setMarcaNome(String marcaNome) {
        this.marcaNome = marcaNome;
    }
}
