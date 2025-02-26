package mef.application.modelo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UsuarioPersonaGrilla {
	private long id_usuario;
	private long id_persona;

	private int id_tipo_persona; 
	private String cod_usuario;
	private String desc_tipo_persona; 
	private String id_tipo_documento; 
	

	@NotNull(message = "[Tipo de Persona] no puede ser nulo") 
	@Pattern(regexp = "^[0-9]+$", message = "[Tipo de Persona] debe ser numerico") 
	@Size(min = 1, max = 2, message = "[Tipo de Persona] su longitud de estar entre 1 y 2")
	private String id_tipo_usuario; 
	
	private String desc_tipo_documento; 
	private String desc_tipo_usuario; 
	 
	private String desc_oficina; 
	
	@NotNull(message = "[Estado] no puede ser nulo")
	@Size(min = 0, max = 1, message = "[Estado] su longitud de estar entre 0 y 1")
	private String flg_estado;

	@NotNull(message = "[Valido] no puede ser nulo")
	@Size(min = 0, max = 1, message = "[Valido] su longitud de estar entre 0 y 1") 
	private String flg_valido;
	
	@NotNull(message = "[Nombre de Persona] no puede ser nulo")
	@Size(min = 0, max = 100, message = "[Nombre de Persona] su longitud de estar entre 0 y 100")
	private String nom_persona;

	@NotNull(message = "[Nro de Documento] no puede ser nulo") 
	@Size(min = 0, max = 15, message = "[Nro de Documento] su longitud de estar entre 0 y 15")
	private String nro_documento;
	
	@NotNull(message = "[Celular] no puede ser nulo") 
	@Size(min = 0, max = 15, message = "[Celular] su longitud de estar entre 0 y 15")
	private String celular;
	
	@NotNull(message = "[Telefono] no puede ser nulo")
	@Size(min = 0, max = 15, message = "[Telefono] su longitud de estar entre 0 y 15") 
	private String telefono;
	
	@NotNull(message = "[Correo] no puede ser nulo") 
	@Size(min = 0, max = 100, message = "[Correo] su longitud de estar entre 0 y 100")
	private String correo; 

	@NotNull(message = "[Direccion] no puede ser nulo")
	@Size(min = 0, max = 100, message = "[Direccion] su longitud de estar entre 0 y 100")
	private String direccion; 
     
	private String fec_activacion;
	private Date dfec_activacion;
	private String fec_desactivacion;
	private Date dfec_desactivacion;
	
	private String usu_creacion;
	private String fec_creacion; 
	private Date dfec_creacion;
	private String ip_creacion;
	

	private String usu_modificacion;
	private String fec_modificacion;
	private String ip_modificacion;
	private String fec_inicio;
	private String fec_fin; 

	private String departamento; 
	private String provincia; 
	private String distrito; 
	
	
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	private int numpag;
	private int numreg;	
	private int totalreg;
	
	
	private String delegado;
	private String representante;
	
	
	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}
	
	public String getDelegado() {
		return delegado;
	}
	
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	
	public String getRepresentante() {
		return representante;
	}
	
	
	public String getFec_fin() {
		return fec_fin;
	}
	public void setFec_fin(String fec_fin) {
		this.fec_fin = fec_fin;
	}
	
	
	public String getFec_inicio() {
		return fec_inicio;
	}
	public void setFec_inicio(String fec_inicio) {
		this.fec_inicio = fec_inicio;
	}
	
	
	public String getId_tipo_documento() {
		return id_tipo_documento;
	}
	public void setId_tipo_documenton(String id_tipo_documento) {
		this.id_tipo_documento = id_tipo_documento;
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
	public Date getDfec_creacion() {
		return dfec_creacion;
	}
	public void setDfec_creacion(Date dfec_creacion) {
		this.dfec_creacion = dfec_creacion;
	}
	public Date getDfec_activacion() {
		return dfec_activacion;
	}
	public void setDfec_activacion(Date dfec_activacion) {
		this.dfec_activacion = dfec_activacion;
	}
	public Date getDfec_desactivacion() {
		return dfec_desactivacion;
	}
	public void setDfec_desactivacion(Date dfec_desactivacion) {
		this.dfec_desactivacion = dfec_desactivacion;
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
