package mef.application.modelo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OficinaGrilla { 
	
	private int idunidad;

	@NotNull(message = "[Acronimo] no puede ser nulo")
	@Size(min = 0, max = 50, message = "[Acronimo] su longitud de estar entre 0 y 50")
	private String acronimo;

	@NotNull(message = "[Codigo] no puede ser nulo")
	@Size(min = 0, max = 20, message = "[Codigo] su longitud de estar entre 0 y 20")
	private String codigo;
	private String conjefe;

	@NotNull(message = "[Descripcion] no puede ser nulo")
	@Size(min = 0, max = 100, message = "[Descripcion] su longitud de estar entre 0 y 100")
	private String descripcion;

	@NotNull(message = "[Descripcion Completa] no puede ser nulo")
	@Size(min = 0, max = 100, message = "[Descripcion Completa] su longitud de estar entre 0 y 100")
	private String descripcioncompleta;
	private int flagofgeneral;

	@NotNull(message = "[Jefe] no puede ser nulo")
	@Size(min = 0, max = 50, message = "[Jefe] su longitud de estar entre 0 y 50")
	private String jefe;
	private int flg_externo;
	private int flg_interno;
  
	private int id_oficina;
	private String desc_oficina;

	public List<Oficinas> lista_oficinas;

	public int getId_oficina() {
		return id_oficina;
	}

	public void setId_oficina(int id_oficina) {
		this.id_oficina = id_oficina;
	}

	public String getDesc_oficina() {
		return desc_oficina;
	}

	public void setDesc_oficina(String desc_oficina) {
		this.desc_oficina = desc_oficina;
	}

	public int getFlg_externo() {
		return flg_externo;
	}

	public void setFlg_externo(int flg_externo) {
		this.flg_externo = flg_externo;
	}

	public int getFlg_interno() {
		return flg_interno;
	}

	public void setFlg_interno(int flg_interno) {
		this.flg_interno = flg_interno;
	}

	public int getIdunidad() {
		return idunidad;
	}

	public void setIdunidad(int idunidad) {
		this.idunidad = idunidad;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getConjefe() {
		return conjefe;
	}

	public void setConjefe(String conjefe) {
		this.conjefe = conjefe;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcioncompleta() {
		return descripcioncompleta;
	}

	public void setDescripcioncompleta(String descripcioncompleta) {
		this.descripcioncompleta = descripcioncompleta;
	}

	public int getFlagofgeneral() {
		return flagofgeneral;
	}

	public void setFlagofgeneral(int flagofgeneral) {
		this.flagofgeneral = flagofgeneral;
	}

	public String getJefe() {
		return jefe;
	}

	public void setJefe(String jefe) {
		this.jefe = jefe;
	}
}
