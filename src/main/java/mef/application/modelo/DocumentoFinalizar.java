package mef.application.modelo;
  

import java.util.List;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class DocumentoFinalizar {

	private String id_usuario;
	private String id_persona;
	private String asunto; 
	private long id_documento;
	private long id_casilla;  
	private int id_estado_documento;
	private String numero_sid; 
	private String anio; 
	private String mensaje; 
	
	private String usu_modificacion; 
	private String tipo_documento;  
	private String nro_documento;  
	private String hoja_envio;  
	private String oficina;  

	@Transient
	private MultipartFile[] filesAnexos;
	
	@Transient
	private MultipartFile[] archivos;  
	
	public long getId_documento() {
		return id_documento;
	}
	public void setId_documento(long id_documento) {
		this.id_documento = id_documento;
	}
	public long getId_casilla() {
		return id_casilla;
	}
	public void setId_casilla(long id_casilla) {
		this.id_casilla = id_casilla;
	}
	public int getId_estado_documento() {
		return id_estado_documento;
	}
	public void setId_estado_documento(int id_estado_documento) {
		this.id_estado_documento = id_estado_documento;
	}
	 
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public MultipartFile[] getArchivos() {
		return archivos;
	}
	public void setArchivos(MultipartFile[] archivos) {
		this.archivos = archivos;
	}
	public String getUsu_modificacion() {
		return usu_modificacion;
	}
	public void setUsu_modificacion(String usu_modificacion) {
		this.usu_modificacion = usu_modificacion;
	}
	public String getNumero_sid() {
		return numero_sid;
	}
	public void setNumero_sid(String numero_sid) {
		this.numero_sid = numero_sid;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	 
	public MultipartFile[] getFilesAnexos() {
		return filesAnexos;
	}
	public void setFilesAnexos(MultipartFile[] filesAnexos) {
		this.filesAnexos = filesAnexos;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getId_persona() {
		return id_persona;
	}
	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}
	public String getTipo_documento() {
		return tipo_documento;
	}
	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public String getHoja_envio() {
		return hoja_envio;
	}
	public void setHoja_envio(String hoja_envio) {
		this.hoja_envio = hoja_envio;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	
	
}
