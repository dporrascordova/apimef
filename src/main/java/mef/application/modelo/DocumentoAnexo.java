package mef.application.modelo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class DocumentoAnexo implements Serializable{	
	
	private int id_documento;
	private String codigo_archivo;
	private String nombre_archivo;
	private String extension_archivo;
	private int tamanio_archivo;
	private String mimetype_archivo;
	private short orden;
	private String fec_creacion;
	private Date fec_modificacion;
	private String usu_creacion;
	private String usu_modificacion;
	private MultipartFile file;	
	private String flg_link;
	private boolean crea_MPI;
	private int flg_estado;
	private int estadoAnexo;
	private String idAnexo;

	public int getEstadoAnexo() {
		return estadoAnexo;
	}

	public void setEstadoAnexo(int estadoAnexo) {
		this.estadoAnexo = estadoAnexo;
	}

	public String getIdAnexo() {
		return idAnexo;
	}

	public void setIdAnexo(String idAnexo) {
		this.idAnexo = idAnexo;
	}

	public int getFlg_estado() {
		return flg_estado;
	}
	public void setFlg_estado(int flg_estado) {
		this.flg_estado = flg_estado;
	}
	public String getFlg_link() {
		return flg_link;
	}
	public void setFlg_link(String flg_link) {
		this.flg_link = flg_link;
	}
	
	
	public int getId_documento() {
		return id_documento;
	}
	public void setId_documento(int id_documento) {
		this.id_documento = id_documento;
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
	public short getOrden() {
		return orden;
	}
	public void setOrden(short orden) {
		this.orden = orden;
	}
	public String getFec_creacion() {
		return fec_creacion;
	}
	public void setFec_creacion(String fec_creacion) {
		this.fec_creacion = fec_creacion;
	}
	public Date getFec_modificacion() {
		return fec_modificacion;
	}
	public void setFec_modificacion(Date fec_modificacion) {
		this.fec_modificacion = fec_modificacion;
	}
	public String getUsu_creacion() {
		return usu_creacion;
	}
	public void setUsu_creacion(String usu_creacion) {
		this.usu_creacion = usu_creacion;
	}
	public String getUsu_modificacion() {
		return usu_modificacion;
	}
	public void setUsu_modificacion(String usu_modificacion) {
		this.usu_modificacion = usu_modificacion;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getMimetype_archivo() {
		return mimetype_archivo;
	}
	public void setMimetype_archivo(String mimetype_archivo) {
		this.mimetype_archivo = mimetype_archivo;
	}

	public boolean isCrea_MPI() {
		return crea_MPI;
	}
	public void setCrea_MPI(boolean crea_MPI) {
		this.crea_MPI = crea_MPI;
	}	
	
}
