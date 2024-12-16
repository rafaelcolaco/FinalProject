package dto;

public class VendaDTO {
    private int id;
    private int clienteId;
    private int vendedorId;
    private int interesseId;
    private double precoFinal;
    private int parcelas;
    private String dataVenda;
    private int veiculoId;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClienteId() {
		return clienteId;
	}
	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}
	public int getVendedorId() {
		return vendedorId;
	}
	public void setVendedorId(int vendedorId) {
		this.vendedorId = vendedorId;
	}
	public int getInteresseId() {
		return interesseId;
	}
	public void setInteresseId(int interesseId) {
		this.interesseId = interesseId;
	}
	public double getPrecoFinal() {
		return precoFinal;
	}
	public void setPrecoFinal(double precoFinal) {
		this.precoFinal = precoFinal;
	}
	public int getParcelas() {
		return parcelas;
	}
	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}
	public String getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}
	public int getVeiculoId() {
		return veiculoId;
	}
	public void setVeiculoId(int veiculoId) {
		this.veiculoId = veiculoId;
	}

   
}
