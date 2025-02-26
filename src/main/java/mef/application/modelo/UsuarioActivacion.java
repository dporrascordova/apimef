package mef.application.modelo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioActivacion { 
	
	@NotNull(message = "[Codigo de Usuario] es obligatorio") 
	@Pattern(regexp = "^[0-9]+$", message = "[Codigo de Usuario] debe ser numerico")
	//@Positive(message = "[Codigo de Usuario] debe ser numerico") 
	@Size(min = 1, max = 15, message = "[Codigo de Usuario] su longitud de estar entre 1 y 15")
    //@Range(min  = 1, message = "[Codigo de Usuario] no puede ser 0")
	private String id_usuario; 

	@NotNull(message = "[Fecha de Activacion] es obligatorio")
	@Size(min = 1, max = 10, message = "[Fecha de Activacion] su longitud de estar entre 1 y 10")
	@Pattern(regexp = "^\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}$", message = "[Fecha de Activacion] no tiene el formato correcto")
	//@Past(message="Invalid past date") 
	//^(0?[1-9]|[12][0-9]|3[01])/(\\\\d{4})/(0?[1-9]|1[012])$
    //@JsonFormat(pattern="dd/MM/yyyy") 
	private String fec_activacion;
	

	@NotNull(message = "[Fecha de Desactivacion] es obligatorio")
	@Size(min = 1, max = 10, message = "[Fecha de Desactivacion] su longitud de estar entre 1 y 10")
	@Pattern(regexp = "^\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}$", message = "[Fecha de Desactivacion] no tiene el formato correcto")
	//@Pattern(regexp = "^\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}$", message = "[Fecha de Desactivacion] no tiene el formato correcto")
	//@Past(message="[Fecha de Desactivacion] la fecha no es valida") 
    //@JsonFormat(pattern="dd/MM/yyyy")
	private String fec_desactivacion; 

	private String usu_modificacion;
	private String fec_modificacion;
	private String ip_modificacion;

	public String getUsu_modificacion() {
		return usu_modificacion;
	}

	public void setUsu_modificacion(String usu_modificacion) {
		this.usu_modificacion = usu_modificacion;
	}

	public String getFec_modificacion() {
		return fec_modificacion;
	}

	public void setFec_modificacion(String fec_modificacion) {
		this.fec_modificacion = fec_modificacion;
	}
 
	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
 
	public String getIp_modificacion() {
		return ip_modificacion;
	}

	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}

	public String getFec_activacion() {
		return fec_activacion;
	}

	public void setFec_activacion(String fec_activacion) {
		this.fec_activacion = fec_activacion;
	}

	public String getFec_desactivacion() {
		return fec_desactivacion;
	}

	public void setFec_desactivacion(String fec_desactivacion) {
		this.fec_desactivacion = fec_desactivacion;
	}
}
