package mef.application.modelo;

import java.util.Date;

import javax.persistence.Transient;

public class DocumentoGrilla {

	private int id_documento;

	private String id_tipo_documento;
	private String desc_tipo_documento;
	private int id_estado_documento;
	private String desc_estado_documento;
	private int id_oficina;
	private String desc_oficina;
	private String id_usuario;
	private String id_tipo_usuario;
	private String nro_documento;
	private int nro_folios;
	private String asunto;
	private String fec_recibido;
	private String codigo_archivo;
	private String flg_estado;
	private int anio;
	private String numero_sid;
	private String hoja_ruta;
	private String observacion;
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	private String usu_modificacion;
	private Date fec_modificacion;
	private String ip_modificacion;
	private int cuenta_anexos;
	
	@Transient
	private String fec_ini;

	@Transient
	private String fec_fin;
	
	public String getFec_ini() {
		return fec_ini;
	}
	public void setFec_ini(String fec_ini) {
		this.fec_ini = fec_ini;
	}
	public String getFec_fin() {
		return fec_fin;
	}
	public void setFec_fin(String fec_fin) {
		this.fec_fin = fec_fin;
	}
	public int getId_documento() {
		return id_documento;
	}
	public void setId_documento(int id_documento) {
		this.id_documento = id_documento;
	}
	public String getId_tipo_documento() {
		return id_tipo_documento;
	}
	public void setId_tipo_documento(String id_tipo_documento) {
		this.id_tipo_documento = id_tipo_documento;
	}
	public String getDesc_tipo_documento() {
		return desc_tipo_documento;
	}
	public void setDesc_tipo_documento(String desc_tipo_documento) {
		this.desc_tipo_documento = desc_tipo_documento;
	}
	public int getId_estado_documento() {
		return id_estado_documento;
	}
	public void setId_estado_documento(int id_estado_documento) {
		this.id_estado_documento = id_estado_documento;
	}
	public int getId_oficina() {
		return id_oficina;
	}
	public void setId_oficina(int id_oficina) {
		this.id_oficina = id_oficina;
	}
	public String getDesc_oficina() {
		return desc_oficina;
	}
	public void setDesc_oficina(String desc_oficina) {
		this.desc_oficina = desc_oficina;
	}
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getId_tipo_usuario() {
		return id_tipo_usuario;
	}
	public void setId_tipo_usuario(String id_tipo_usuario) {
		this.id_tipo_usuario = id_tipo_usuario;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public int getNro_folios() {
		return nro_folios;
	}
	public void setNro_folios(int nro_folios) {
		this.nro_folios = nro_folios;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getFec_recibido() {
		return fec_recibido;
	}
	public void setFec_recibido(String fec_recibido) {
		this.fec_recibido = fec_recibido;
	}
	public String getCodigo_archivo() {
		return codigo_archivo;
	}
	public void setCodigo_archivo(String codigo_archivo) {
		this.codigo_archivo = codigo_archivo;
	}
	public String getFlg_estado() {
		return flg_estado;
	}
	public void setFlg_estado(String flg_estado) {
		this.flg_estado = flg_estado;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public String getNumero_sid() {
		return numero_sid;
	}
	public void setNumero_sid(String numero_sid) {
		this.numero_sid = numero_sid;
	}
	public String getHoja_ruta() {
		return hoja_ruta;
	}
	public void setHoja_ruta(String hoja_ruta) {
		this.hoja_ruta = hoja_ruta;
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
	public String getUsu_modificacion() {
		return usu_modificacion;
	}
	public void setUsu_modificacion(String usu_modificacion) {
		this.usu_modificacion = usu_modificacion;
	}
	public Date getFec_modificacion() {
		return fec_modificacion;
	}
	public void setFec_modificacion(Date fec_modificacion) {
		this.fec_modificacion = fec_modificacion;
	}
	public String getIp_modificacion() {
		return ip_modificacion;
	}
	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}
	public int getCuenta_anexos() {
		return cuenta_anexos;
	}
	public void setCuenta_anexos(int cuenta_anexos) {
		this.cuenta_anexos = cuenta_anexos;
	}
	public String getDesc_estado_documento() {
		return desc_estado_documento;
	}
	public void setDesc_estado_documento(String desc_estado_documento) {
		this.desc_estado_documento = desc_estado_documento;
	}
	
}
