package mef.application.modelo;

import java.io.Serializable;

public class Provincia implements Serializable{

	private int id_provincia; 
	private int id_departamento;
	private String desc_provincia;
	
	public int getId_provincia() {
		return id_provincia;
	}
	public void setId_provincia(int id_provincia) {
		this.id_provincia = id_provincia;
	}
	public String getDesc_provincia() {
		return desc_provincia;
	}
	public void setDesc_provincia(String desc_provincia) {
		this.desc_provincia = desc_provincia;
	}
	public int getId_departamento() {
		return id_departamento;
	}
	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}
}
