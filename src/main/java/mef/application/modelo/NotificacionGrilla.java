package mef.application.modelo;

import java.util.Date;

public class NotificacionGrilla {
	private Long id_casilla;
	private Long id_documento;
	private Long id_usuario;
	private Long id_persona;
	private String id_tipo_documento;
	private String id_oficina;
	private String id_estado;
	private String fec_recepcion;
	private String fec_notificacion;
	private Date dfec_notificacion;
	private String remitente;
	private String desc_tipo_documento;
	private String nro_documento; 
	private String nro_folios; 
	private String asunto; 
	private String correo;
	private String desc_oficina;
	private String desc_estado;
	private String hoja_ruta;
	private String hoja_envio;
	private String nro_solicitud;
	private String mensaje_html;
	
	private String usu_creacion;
	private String fec_creacion;
	private Date dfec_creacion;
	private String ip_creacion;
	
	private String usu_modificacion;
	private String fec_modificacion;
	private String ip_modificacion;
	
	private int numpag;
	private int numreg;	
	private int totalreg;
	
	public String getNro_solicitud() {
		return nro_solicitud;
	}
	public void setNro_solicitud(String nro_solicitud) {
		this.nro_solicitud = nro_solicitud;
	}
	
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
	public String getIp_modificacion() {
		return ip_modificacion;
	}
	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}
	public Long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public Long getId_persona() {
		return id_persona;
	}
	public void setId_persona(Long id_persona) {
		this.id_persona = id_persona;
	}
	public Long getId_casilla() {
		return id_casilla;
	}
	public void setId_casilla(Long id_casilla) {
		this.id_casilla = id_casilla;
	}
	public Long getId_documento() {
		return id_documento;
	}
	public void setId_documento(Long id_documento) {
		this.id_documento = id_documento;
	}
	public String getFec_recepcion() {
		return fec_recepcion;
	}
	public void setFec_recepcion(String fec_recepcion) {
		this.fec_recepcion = fec_recepcion;
	}
	public String getRemitente() {
		return remitente;
	}
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	public String getDesc_tipo_documento() {
		return desc_tipo_documento;
	}
	public void setDesc_tipo_documento(String desc_tipo_documento) {
		this.desc_tipo_documento = desc_tipo_documento;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDesc_oficina() {
		return desc_oficina;
	}
	public void setDesc_oficina(String desc_oficina) {
		this.desc_oficina = desc_oficina;
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
	public String getDesc_estado() {
		return desc_estado;
	}
	public void setDesc_estado(String desc_estado) {
		this.desc_estado = desc_estado;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getHoja_ruta() {
		return hoja_ruta;
	}
	public void setHoja_ruta(String hoja_ruta) {
		this.hoja_ruta = hoja_ruta;
	}
	public String getFec_notificacion() {
		return fec_notificacion;
	}
	public void setFec_notificacion(String fec_notificacion) {
		this.fec_notificacion = fec_notificacion;
	}
	public String getMensaje_html() {
		return mensaje_html;
	}
	public void setMensaje_html(String mensaje_html) {
		this.mensaje_html = mensaje_html;
	}
	public String getId_tipo_documento() {
		return id_tipo_documento;
	}
	public void setId_tipo_documento(String id_tipo_documento) {
		this.id_tipo_documento = id_tipo_documento;
	}
	public String getId_oficina() {
		return id_oficina;
	}
	public void setId_oficina(String id_oficina) {
		this.id_oficina = id_oficina;
	}
	public String getId_estado() {
		return id_estado;
	}
	public void setId_estado(String id_estado) {
		this.id_estado = id_estado;
	}
	public String getNro_folios() {
		return nro_folios;
	}
	public void setNro_folios(String nro_folios) {
		this.nro_folios = nro_folios;
	}
	public String getHoja_envio() {
		return hoja_envio;
	}
	public void setHoja_envio(String hoja_envio) {
		this.hoja_envio = hoja_envio;
	}
	public Date getDfec_notificacion() {
		return dfec_notificacion;
	}
	public void setDfec_notificacion(Date dfec_notificacion) {
		this.dfec_notificacion = dfec_notificacion;
	}
	public Date getDfec_creacion() {
		return dfec_creacion;
	}
	public void setDfec_creacion(Date dfec_creacion) {
		this.dfec_creacion = dfec_creacion;
	}
	public int getNumpag() {
		return numpag;
	}
	public void setNumpag(int numpag) {
		this.numpag = numpag;
	}
	public int getNumreg() {
		return numreg;
	}
	public void setNumreg(int numreg) {
		this.numreg = numreg;
	}
	public int getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(int totalreg) {
		this.totalreg = totalreg;
	}
}
