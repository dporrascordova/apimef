package mef.application.modelo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class PersonaUsuario {

	private int idpersona;
	
	private int flgtipousuario; // 1 Interno - 2 Externo
 
	private String desctipopersona;
 
	private String desctipodoc; 
	
	private String nrodocumento;
 
	private String nombre;
  
	private String nombreusuario;
	
	private String clave;

	private String correo;
	private String correo_copia;
	
	public int getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}

	public String getDesctipopersona() {
		return desctipopersona;
	}

	public void setDesctipopersona(String desctipopersona) {
		this.desctipopersona = desctipopersona;
	}

	public String getDesctipodoc() {
		return desctipodoc;
	}

	public void setDesctipodoc(String desctipodoc) {
		this.desctipodoc = desctipodoc;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getFlgtipousuario() {
		return flgtipousuario;
	}

	public void setFlgtipousuario(int flgtipousuario) {
		this.flgtipousuario = flgtipousuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCorreo_copia() {
		return correo_copia;
	}

	public void setCorreo_copia(String correo_copia) {
		this.correo_copia = correo_copia;
	}
	
}
