package mef.application.modelo;

import java.util.List;

import javax.persistence.Lob;

public class CasillaUno {

	private String tipo_modulo;
	private String id_casilla;
	private String id_persona;
	private String id_usuario; 
	private String mensaje;
	@Lob
	private String mensaje_html;   
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	private String estado; 
	
	private List<CasillaArchivo> archivos;

	public String getId_casilla() {
		return id_casilla;
	}

	public void setId_casilla(String id_casilla) {
		this.id_casilla = id_casilla;
	}

	public String getId_persona() {
		return id_persona;
	}

	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje_html() {
		return mensaje_html;
	}

	public void setMensaje_html(String mensaje_html) {
		this.mensaje_html = mensaje_html;
	}

	public String getUsu_creacion() {
		return usu_creacion;
	}

	public void setUsu_creacion(String usu_creacion) {
		this.usu_creacion = usu_creacion;
	}

	public String getFec_creacion() {
		return fec_creacion;
	}

	public void setFec_creacion(String fec_creacion) {
		this.fec_creacion = fec_creacion;
	}

	public String getIp_creacion() {
		return ip_creacion;
	}

	public void setIp_creacion(String ip_creacion) {
		this.ip_creacion = ip_creacion;
	}

	public List<CasillaArchivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<CasillaArchivo> archivos) {
		this.archivos = archivos;
	}

	public String getTipo_modulo() {
		return tipo_modulo;
	}

	public void setTipo_modulo(String tipo_modulo) {
		this.tipo_modulo = tipo_modulo;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
	
	public String getEstado() {
		return estado;
	}
	
	
}
