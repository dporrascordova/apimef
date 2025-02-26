package mef.application.modelo;

import java.io.Serializable;

public class Distrito implements Serializable{

	private int id_distrito;
	private int id_provincia;  
	private int id_departamento;
	private String desc_distrito;
	
	public int getId_distrito() {
		return id_distrito;
	}
	public void setId_distrito(int id_distrito) {
		this.id_distrito = id_distrito;
	}
	public String getDesc_distrito() {
		return desc_distrito;
	}
	public void setDesc_distrito(String desc_distrito) {
		this.desc_distrito = desc_distrito;
	}
	public int getId_provincia() {
		return id_provincia;
	}
	public void setId_provincia(int id_provincia) {
		this.id_provincia = id_provincia;
	}
	public int getId_departamento() {
		return id_departamento;
	}
	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}
}
