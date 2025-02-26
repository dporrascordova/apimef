package mef.application.modelo;

import java.sql.Clob;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CasillaGrilla { 

	private String id_casilla;
	private String id_usuario;
	private String id_documento;
	private String id_estado_documento;
	private String desc_estado_documento;

	@NotNull(message = "[Observacion] no puede ser nulo")   
	@Size(min = 0, max = 50, message = "[Observacion] su longitud de estar entre 0 y 50")
	private String observacion;

	@NotNull(message = "[Nro de Solicitud] no puede ser nulo")  
	//@Pattern(regexp = "^[0-9]+$", message = "[Nro de Solicitud] debe ser numerico") 
	@Size(min = 0, max = 50, message = "[Nro de Solicitud] su longitud de estar entre 0 y 50")
	private String nro_solicitud;

	@NotNull(message = "[Nro de Documento] no puede ser nulo")  
	@Size(min = 0, max = 50, message = "[Nro de Documento] su longitud de estar entre 0 y 50")
	private String nro_documento;
	private String hoja_ruta;
	private String asunto;

	@NotNull(message = "[Oficina] no puede ser nulo") 
	@Pattern(regexp = "^[0-9]+$", message = "[Oficina] debe ser numerico") 
	@Size(min = 0, max = 10, message = "[Oficina] su longitud de estar entre 0 y 10")
	private String id_oficina;
	private String desc_oficina;

	@NotNull(message = "[Tipo de Documento] no puede ser nulo") 
	@Pattern(regexp = "^[0-9]+$", message = "[Tipo de Documento] debe ser numerico") 
	@Size(min = 0, max = 10, message = "[Tipo de Documento] su longitud de estar entre 0 y 10")
	private String id_tipo_documento;
	private String desc_tipo_documento;
	private String usu_creacion;
	private String fec_creacion;
	private Date dfec_creacion;
	private String ip_creacion;
	private String mensaje_html;
	private String fec_modificacion;
	private int flg_tipo_noti;
	
	
	public int getFlg_tipo_noti() {
		return flg_tipo_noti;
	}
	
	public void setFlg_tipo_noti(int flg_tipo_noti) {
		this.flg_tipo_noti = flg_tipo_noti;
	}
	
	public String getNro_solicitud() {
		return nro_solicitud;
	}
	
	public void setNro_solicitud(String nro_solicitud) {
		this.nro_solicitud = nro_solicitud;
	}
	public String getId_oficina() {
		return id_oficina;
	}
	public void setId_oficina(String id_oficina) {
		this.id_oficina = id_oficina;
	}
	public String getId_tipo_documento() {
		return id_tipo_documento;
	}
	public void setId_tipo_documento(String id_tipo_documento) {
		this.id_tipo_documento = id_tipo_documento;
	}
	public String getId_documento() {
		return id_documento;
	}
	public void setId_documento(String id_documento) {
		this.id_documento = id_documento;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public String getHoja_ruta() {
		return hoja_ruta;
	}
	public void setHoja_ruta(String hoja_ruta) {
		this.hoja_ruta = hoja_ruta;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDesc_oficina() {
		return desc_oficina;
	}
	public void setDesc_oficina(String desc_oficina) {
		this.desc_oficina = desc_oficina;
	}
	public String getDesc_tipo_documento() {
		return desc_tipo_documento;
	}
	public void setDesc_tipo_documento(String desc_tipo_documento) {
		this.desc_tipo_documento = desc_tipo_documento;
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
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getMensaje_html() {
		return mensaje_html;
	}
	public void setMensaje_html(String mensaje_html) {
		this.mensaje_html = mensaje_html;
	}
	public String getId_casilla() {
		return id_casilla;
	}
	public void setId_casilla(String id_casilla) {
		this.id_casilla = id_casilla;
	}
	public String getIp_creacion() {
		return ip_creacion;
	}
	public void setIp_creacion(String ip_creacion) {
		this.ip_creacion = ip_creacion;
	}
	public String getFec_modificacion() {
		return fec_modificacion;
	}
	public void setFec_modificacion(String fec_modificacion) {
		this.fec_modificacion = fec_modificacion;
	}
	public String getDesc_estado_documento() {
		return desc_estado_documento;
	}
	public void setDesc_estado_documento(String desc_estado_documento) {
		this.desc_estado_documento = desc_estado_documento;
	}
	public String getId_estado_documento() {
		return id_estado_documento;
	}
	public void setId_estado_documento(String id_estado_documento) {
		this.id_estado_documento = id_estado_documento;
	}
	public Date getDfec_creacion() {
		return dfec_creacion;
	}
	public void setDfec_creacion(Date dfec_creacion) {
		this.dfec_creacion = dfec_creacion;
	}

	
}
