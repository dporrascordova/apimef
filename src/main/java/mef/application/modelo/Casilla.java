package mef.application.modelo;

import java.util.List;

import javax.persistence.Lob;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class Casilla  
{ 
	private Long id_casilla;
	private Long id_persona;
	private int id_usuario;
	private int flg_notificacion;
	public int getFlg_notificacion() {
		return flg_notificacion;
	}
	public void setFlg_notificacion(int flg_notificacion) {
		this.flg_notificacion = flg_notificacion;
	}
	private Long id_documento;
	private String observacion;
	@Lob
	private String mensaje_html;   
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;

	private String hoja_envio;
	private String nro_doc;
	private String tipo_doc;
	private String hoja_ruta;
	private String oficina;
	 
    
	@Transient
	private List<String> notificar; 

	@Transient
	private MultipartFile[] archivos;  
	
	public Long getId_casilla() {
		return id_casilla;
	}
	public void setId_casilla(Long id_casilla) {
		this.id_casilla = id_casilla;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public Long getId_documento() {
		return id_documento;
	}
	public void setId_documento(long id_documento) {
		this.id_documento = id_documento;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
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
	public Long getId_persona() {
		return id_persona;
	}
	public void setId_persona(Long id_persona) {
		this.id_persona = id_persona;
	}
	public List<String> getNotificar() {
		return notificar;
	}
	public void setNotificar(List<String> notificar) {
		this.notificar = notificar;
	}
	public String getMensaje_html() {
		return mensaje_html;
	}
	public void setMensaje_html(String mensaje_html) {
		this.mensaje_html = mensaje_html;
	}
	public MultipartFile[] getArchivos() {
		return archivos;
	}
	public void setArchivos(MultipartFile[] archivos) {
		this.archivos = archivos;
	}
	public String getHoja_envio() {
		return hoja_envio;
	}
	public void setHoja_envio(String hoja_envio) {
		this.hoja_envio = hoja_envio;
	}
	public String getNro_doc() {
		return nro_doc;
	}
	public void setNro_doc(String nro_doc) {
		this.nro_doc = nro_doc;
	}
	public String getTipo_doc() {
		return tipo_doc;
	}
	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
	}
	public String getHoja_ruta() {
		return hoja_ruta;
	}
	public void setHoja_ruta(String hoja_ruta) {
		this.hoja_ruta = hoja_ruta;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	 
}
