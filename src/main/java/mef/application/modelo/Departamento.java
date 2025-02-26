package mef.application.modelo;

import java.io.Serializable;

public class Departamento implements Serializable { 

	private int id_departamento;
	private String desc_departamento;
	
	public int getId_departamento() {
		return id_departamento;
	}
	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}
	public String getDesc_departamento() {
		return desc_departamento;
	}
	public void setDesc_departamento(String desc_departamento) {
		this.desc_departamento = desc_departamento;
	}
}
