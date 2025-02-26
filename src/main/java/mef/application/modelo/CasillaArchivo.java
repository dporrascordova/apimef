package mef.application.modelo;
 

import org.springframework.web.multipart.MultipartFile;

public class CasillaArchivo {
 
	private long id_casilla;
	private String codigo_archivo;
	private String nombre_archivo;
	private String extension_archivo;
	private int tamanio_archivo; 
	
	private String usu_creacion;
	private String ip_creacion; 
	private String fec_creacion; 
	private MultipartFile file;

	 
	public long getId_casilla() {
		return id_casilla;
	}
	public void setId_casilla(long id_casilla) {
		this.id_casilla = id_casilla;
	}
	public String getCodigo_archivo() {
		return codigo_archivo;
	}
	public void setCodigo_archivo(String codigo_archivo) {
		this.codigo_archivo = codigo_archivo;
	}
	public String getNombre_archivo() {
		return nombre_archivo;
	}
	public void setNombre_archivo(String nombre_archivo) {
		this.nombre_archivo = nombre_archivo;
	}
	public String getExtension_archivo() {
		return extension_archivo;
	}
	public void setExtension_archivo(String extension_archivo) {
		this.extension_archivo = extension_archivo;
	}
	public int getTamanio_archivo() {
		return tamanio_archivo;
	}
	public void setTamanio_archivo(int tamanio_archivo) {
		this.tamanio_archivo = tamanio_archivo;
	}
	public String getUsu_creacion() {
		return usu_creacion;
	}
	public void setUsu_creacion(String usu_creacion) {
		this.usu_creacion = usu_creacion;
	}
	public String getIp_creacion() {
		return ip_creacion;
	}
	public void setIp_creacion(String ip_creacion) {
		this.ip_creacion = ip_creacion;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFec_creacion() {
		return fec_creacion;
	}
	public void setFec_creacion(String fec_creacion) {
		this.fec_creacion = fec_creacion;
	}	
	

	
}
