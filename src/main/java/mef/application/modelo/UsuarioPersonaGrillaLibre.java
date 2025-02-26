package mef.application.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UsuarioPersonaGrillaLibre {
	private long id_usuario;
	private long id_persona;

	private int id_tipo_persona; 
	private String cod_usuario;
	private String desc_tipo_persona; 
 
	private String id_tipo_usuario; 
	
	private String desc_tipo_documento; 
	private String desc_tipo_usuario; 
	 
	private String desc_oficina; 
	 
	private String flg_estado;
 
	private String flg_valido;
	
	 
	private String nom_persona;

	 
	private String nro_documento;
	 
	private String celular;
	 
	private String telefono;
	 
	private String correo; 
 
	private String direccion; 
     
	private String fec_activacion;
	private String fec_desactivacion;
	
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	

	private String usu_modificacion;
	private String fec_modificacion;
	private String ip_modificacion;
	
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
	public String getId_tipo_usuario() {
		return id_tipo_usuario;
	}
	public void setId_tipo_usuario(String id_tipo_usuario) {
		this.id_tipo_usuario = id_tipo_usuario;
	}
	public String getFlg_estado() {
		return flg_estado;
	}
	public void setFlg_estado(String flg_estado) {
		this.flg_estado = flg_estado;
	}
	public String getNom_persona() {
		return nom_persona;
	} 
	public void setNom_persona(String nom_persona) {
		this.nom_persona = nom_persona;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getCod_usuario() {
		return cod_usuario;
	}
	public void setCod_usuario(String cod_usuario) {
		this.cod_usuario = cod_usuario;
	}
	public String getDesc_tipo_usuario() {
		return desc_tipo_usuario;
	}
	public void setDesc_tipo_usuario(String desc_tipo_usuario) {
		this.desc_tipo_usuario = desc_tipo_usuario;
	}
	public String getDesc_oficina() {
		return desc_oficina;
	}
	public void setDesc_oficina(String desc_oficina) {
		this.desc_oficina = desc_oficina;
	}
	public String getDesc_tipo_persona() {
		return desc_tipo_persona;
	}
	public void setDesc_tipo_persona(String desc_tipo_persona) {
		this.desc_tipo_persona = desc_tipo_persona;
	}
	public long getId_persona() {
		return id_persona;
	}
	public void setId_persona(long id_persona) {
		this.id_persona = id_persona;
	}
	public String getDesc_tipo_documento() {
		return desc_tipo_documento;
	}
	public void setDesc_tipo_documento(String desc_tipo_documento) {
		this.desc_tipo_documento = desc_tipo_documento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getId_tipo_persona() {
		return id_tipo_persona;
	}
	public void setId_tipo_persona(int id_tipo_persona) {
		this.id_tipo_persona = id_tipo_persona;
	}
	public String getFlg_valido() {
		return flg_valido;
	}
	public void setFlg_valido(String flg_valido) {
		this.flg_valido = flg_valido;
	}
}
