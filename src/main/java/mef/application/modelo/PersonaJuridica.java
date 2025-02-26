package mef.application.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class PersonaJuridica {

	@NotNull(message = "[Archivo] es obligatorio")  
    private MultipartFile mifile; 
	 	
    private int idpersona; 

	@NotNull(message = "[Tipo Persona] es obligatorio")
	@Size(min = 1, max = 10, message = "[Tipo Persona] su longitud de estar entre 1 y 10")
	@Pattern(regexp = "^[0-9]+$", message = "[Tipo Persona] tiene caracteres no validos.")
    private String tipopersona; 
    private String desctipopersona; 

    private String desctipodoc;  
    
	@NotNull(message = "[RUC] es obligatorio")
	@Size(min = 11, max = 11, message = "[RUC] su longitud de tener 11 caracteres")
	@Pattern(regexp = "^[0-9]+$", message = "[RUC] tiene caracteres no validos.")
    private String ruc;
	 
	@NotNull(message = "[Razon Social] es obligatorio")
	@Size(min = 1, max = 100, message = "[Razon Social] su longitud de estar entre 1 y 100") 
    private String razon_social; 
	 
	@NotNull(message = "[Celular] es obligatorio")
	@Size(min = 1, max = 20, message = "[Celular] su longitud de estar entre 1 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Celular] tiene caracteres no validos.")
    private String celular;

	@NotNull(message = "[Telefono] es obligatorio")
	@Size(min = 1, max = 20, message = "[Telefono] su longitud de estar entre 1 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Telefono] tiene caracteres no validos.")
    private String telefono;
	
	@NotNull(message = "[Correo] es obligatorio")
	@Size(min = 1, max = 100, message = "[Correo] su longitud de estar entre 1 y 100") 
    private String correo;
	
	@NotNull(message = "[Direccion] es obligatorio")
	@Size(min = 1, max = 150, message = "[Direccion] su longitud de estar entre 1 y 150")  
    private String direccion;

	@NotNull(message = "[Departamento] es obligatorio")
	@Size(min = 1, max = 10, message = "[Departamento] su longitud debe estar entre 1 y 10") 
	@Pattern(regexp = "^[0-9]+$", message = "[Departamento] tiene caracteres no validos.")
    private String iddepartamento;
    private String departamento;

	@NotNull(message = "[Provincia] es obligatorio")
	@Size(min = 1, max = 10, message = "[Provincia] su longitud debe estar entre 1 y 10") 
	@Pattern(regexp = "^[0-9]+$", message = "[Provincia] tiene caracteres no validos.")
    private String idprovincia;
    private String provincia; 

	@NotNull(message = "[Distrito] es obligatorio")
	@Size(min = 1, max = 10, message = "[Distrito] su longitud debe estar entre 1 a 10 caracteres") 
	@Pattern(regexp = "^[0-9]+$", message = "[Distrito] tiene caracteres no validos.")
    private String iddistrito;
    private String distrito;

	@NotNull(message = "[DNI Representante] es obligatorio")
	@Size(min = 1, max = 15, message = "[DNI Representante] su longitud debe estar entre 1 a 15 caracteres") 
	@Pattern(regexp = "^[0-9]+$", message = "[DNI Representante] tiene caracteres no validos.")
    private String rep_legal_dni;

	@NotNull(message = "[Nombres Representante] es obligatorio")
	@Size(min = 1, max = 100, message = "[Nombres Representante] su longitud de estar entre 1 y 100")
	//@Pattern(regexp = "^[a-zA-Z0-9\u00f1\u00d1 ]+$", message = "[Nombres Representante] tiene caracteres no validos.") 
    private String rep_legal_nombres;
	 
	@NotNull(message = "[Apellido Paterno Representante] es obligatorio")
	@Size(min = 1, max = 100, message = "[Apellido Paterno Representante] su longitud de estar entre 1 y 100")
	//@Pattern(regexp = "^[a-zA-Z0-9\u00f1\u00d1 ]+$", message = "[Apellido Paterno Representante] tiene caracteres no validos.")  
    private String rep_legal_ape_pat;
	 
	@NotNull(message = "[Apellido Materno Representante] es obligatorio")
	@Size(min = 1, max = 100, message = "[Apellido Materno Representante] su longitud de estar entre 1 y 100")
	//@Pattern(regexp = "^[a-zA-Z0-9\u00f1\u00d1 ]+$", message = "[Apellido Materno Representante] tiene caracteres no validos.")  
    private String rep_legal_ape_mat;

	@NotNull(message = "[Direccion Representante] es obligatorio")
	@Size(min = 1, max = 150, message = "[Direccion Representante] su longitud de estar entre 1 y 150") 
    private String rep_legal_direccion;
	
	@NotNull(message = "[Celular Representante] es obligatorio")
	@Size(min = 1, max = 20, message = "[Celular Representante] su longitud de estar entre 1 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Celular Representante] tiene caracteres no validos.")  
    private String rep_legal_celular;
    
	@NotNull(message = "[Correo Representante] es obligatorio")
	@Size(min = 1, max = 100, message = "[Correo Representante] su longitud de estar entre 1 y 100") 
    private String rep_legal_correo;

	private int rep_legal_id_tipo_documento;
	
	//@NotNull(message = "[DNI Delegado] es obligatorio")
	@Size(min = 0, max = 15, message = "[DNI Delegado] su longitud debe estar entre 1 a 15 caracteres") 
	//@Pattern(regexp = "^[0-9]+$", message = "[DNI Delegado] tiene caracteres no validos.")
    private String delegado_dni;
	
	private int delegado_id_tipo_documento;
    
	//@NotNull(message = "[Nombres Delegado] es obligatorio")
	@Size(min = 0, max = 100, message = "[Nombres Delegado] su longitud de estar entre 1 y 100")
	//@Pattern(regexp = "^[a-zA-Z]+$", message = "[Nombres Delegado] tiene caracteres no validos.")
    private String delegado_nombres;
    
	//@NotNull(message = "[Apellido Paterno Delegado] es obligatorio")
	@Size(min = 0, max = 100, message = "[Apellido Paterno Delegado] su longitud de estar entre 1 y 100")
	//@Pattern(regexp = "^[a-zA-Z]+$", message = "[Apellido Paterno Delegado] tiene caracteres no validos.")
    private String delegado_ape_pat;
    
	//@NotNull(message = "[Apellido Materno Delegado] es obligatorio")
	@Size(min = 0, max = 100, message = "[Apellido Materno Delegado] su longitud de estar entre 1 y 100")
	//@Pattern(regexp = "^[a-zA-Z]+$", message = "[Apellido Materno Delegado] tiene caracteres no validos.")
    private String delegado_ape_mat;

	//@NotNull(message = "[Direccion Delegado] es obligatorio")
	@Size(min = 0, max = 150, message = "[Direccion Delegado] su longitud de estar entre 1 y 150") 
    private String delegado_direccion;

	//@NotNull(message = "[Celular Delegado] es obligatorio")
	@Size(min = 0, max = 20, message = "[Celular Delegado] su longitud de estar entre 1 y 20")
	//@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Celular Delegado] tiene caracteres no validos.")
    private String delegado_celular;
    
	//@NotNull(message = "[Correo Delegado] es obligatorio")
	@Size(min = 0, max = 100, message = "[Correo Delegado] su longitud de estar entre 1 y 100") 
    private String delegado_correo;
  
    private String codigoarchivo; 
    
    private String terminos;
    private String declaracion;
    private String observacion; 

	private String ip_creacion;
	
    private String usuario;
    private String clave;
    private String fec_creacion;
     
    @NotNull(message = "[Codigo captcha] es obligatorio")  
	private String captcha;
    
	public MultipartFile getMifile() {
		return mifile;
	}
	public void setMifile(MultipartFile mifile) {
		this.mifile = mifile;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public String getTipopersona() {
		return tipopersona;
	}
	public void setTipopersona(String tipopersona) {
		this.tipopersona = tipopersona;
	}
	public String getDesctipopersona() {
		return desctipopersona;
	}
	public void setDesctipopersona(String desctipopersona) {
		this.desctipopersona = desctipopersona;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getIddepartamento() {
		return iddepartamento;
	}
	public void setIddepartamento(String iddepartamento) {
		this.iddepartamento = iddepartamento;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getIdprovincia() {
		return idprovincia;
	}
	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIddistrito() {
		return iddistrito;
	}
	public void setIddistrito(String iddistrito) {
		this.iddistrito = iddistrito;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	
	// representante legal 
	
	public int getRep_legal_id_tipo_documento() {
		return rep_legal_id_tipo_documento;
	}
	public void setRep_legal_id_tipo_documento(int rep_legal_id_tipo_documento) {
		this.rep_legal_id_tipo_documento = rep_legal_id_tipo_documento;
	}
	
	
	public String getRep_legal_dni() {
		return rep_legal_dni;
	}
	public void setRep_legal_dni(String rep_legal_dni) {
		this.rep_legal_dni = rep_legal_dni;
	}
	public String getRep_legal_nombres() {
		return rep_legal_nombres;
	}
	public void setRep_legal_nombres(String rep_legal_nombres) {
		this.rep_legal_nombres = rep_legal_nombres;
	}
	public String getRep_legal_ape_pat() {
		return rep_legal_ape_pat;
	}
	public void setRep_legal_ape_pat(String rep_legal_ape_pat) {
		this.rep_legal_ape_pat = rep_legal_ape_pat;
	}
	public String getRep_legal_ape_mat() {
		return rep_legal_ape_mat;
	}
	public void setRep_legal_ape_mat(String rep_legal_ape_mat) {
		this.rep_legal_ape_mat = rep_legal_ape_mat;
	}
	public String getRep_legal_direccion() {
		return rep_legal_direccion;
	}
	public void setRep_legal_direccion(String rep_legal_direccion) {
		this.rep_legal_direccion = rep_legal_direccion;
	}
	public String getRep_legal_celular() {
		return rep_legal_celular;
	}
	public void setRep_legal_celular(String rep_legal_celular) {
		this.rep_legal_celular = rep_legal_celular;
	}
	public String getRep_legal_correo() {
		return rep_legal_correo;
	}
	public void setRep_legal_correo(String rep_legal_correo) {
		this.rep_legal_correo = rep_legal_correo;
	}
	
	
	// delegado 
	
	public int getDelegado_id_tipo_documento() {
		return delegado_id_tipo_documento;
	}
	public void setDelegado_id_tipo_documento(int delegado_id_tipo_documento) {
		this.delegado_id_tipo_documento = delegado_id_tipo_documento;
	}
	
	
	public String getDelegado_dni() {
		return delegado_dni;
	}
	public void setDelegado_dni(String delegado_dni) {
		this.delegado_dni = delegado_dni;
	}
	public String getDelegado_nombres() {
		return delegado_nombres;
	}
	public void setDelegado_nombres(String delegado_nombres) {
		this.delegado_nombres = delegado_nombres;
	}
	public String getDelegado_ape_pat() {
		return delegado_ape_pat;
	}
	public void setDelegado_ape_pat(String delegado_ape_pat) {
		this.delegado_ape_pat = delegado_ape_pat;
	}
	public String getDelegado_ape_mat() {
		return delegado_ape_mat;
	}
	public void setDelegado_ape_mat(String delegado_ape_mat) {
		this.delegado_ape_mat = delegado_ape_mat;
	}
	public String getDelegado_direccion() {
		return delegado_direccion;
	}
	public void setDelegado_direccion(String delegado_direccion) {
		this.delegado_direccion = delegado_direccion;
	}
	public String getDelegado_celular() {
		return delegado_celular;
	}
	public void setDelegado_celular(String delegado_celular) {
		this.delegado_celular = delegado_celular;
	}
	public String getDelegado_correo() {
		return delegado_correo;
	}
	public void setDelegado_correo(String delegado_correo) {
		this.delegado_correo = delegado_correo;
	}
	public String getCodigoarchivo() {
		return codigoarchivo;
	}
	public void setCodigoarchivo(String codigoarchivo) {
		this.codigoarchivo = codigoarchivo;
	}
	public String getTerminos() {
		return terminos;
	}
	public void setTerminos(String terminos) {
		this.terminos = terminos;
	}
	public String getDeclaracion() {
		return declaracion;
	}
	public void setDeclaracion(String declaracion) {
		this.declaracion = declaracion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getDesctipodoc() {
		return desctipodoc;
	}
	public void setDesctipodoc(String desctipodoc) {
		this.desctipodoc = desctipodoc;
	}
	public String getIp_creacion() {
		return ip_creacion;
	}
	public void setIp_creacion(String ip_creacion) {
		this.ip_creacion = ip_creacion;
	}
	public String getFec_creacion() {
		return fec_creacion;
	}
	public void setFec_creacion(String fec_creacion) {
		this.fec_creacion = fec_creacion;
	}
	 
}
