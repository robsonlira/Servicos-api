package br.com.dominio.servicosapi.model;

public enum Status {

	PENDENTE(1, "Pendente"),
	CONFIRMADO(2, "Confirmado"),
	CANCELADO(3,"Cancelado");
	
	private int cod;
	private String descricao;
	
	private Status(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
    public static Status toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (Status x : Status.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
