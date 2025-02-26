package mef.application.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive; 

public class PersonaValida {

	@NotNull(message = "[Codigo de la persona] es obligatorio")
	@Positive(message = "[Codigo de la persona] debe ser numerico") 
	private int idpersona;
	
	private int idtipousuario;
	private int flgvalida;
	private String observacion;
	private String correo;
	
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public int getFlgvalida() {
		return flgvalida;
	}
	public void setFlgvalida(int flgvalida) {
		this.flgvalida = flgvalida;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getIdtipousuario() {
		return idtipousuario;
	}
	public void setIdtipousuario(int idtipousuario) {
		this.idtipousuario = idtipousuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}
