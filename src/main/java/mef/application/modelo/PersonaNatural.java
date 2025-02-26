package mef.application.modelo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class PersonaNatural implements Serializable  {

	private static final long serialVersionUID = -8716260743080689756L;
	
	//@NotNull(message = "[Archivo] es obligatorio")  
    private MultipartFile mifile; 
	 	
    private int idpersona; 

	@NotNull(message = "[Tipo Persona] es obligatorio")
	@Size(min = 1, max = 10, message = "[Tipo Persona] su longitud de estar entre 1 y 10")
	@Pattern(regexp = "^[0-9]+$", message = "[Tipo Persona] tiene caracteres no validos.")
    private String tipopersona; 
    private String desctipopersona; 

	@NotNull(message = "[Tipo Documento] es obligatorio")
	@Positive(message = "[Tipo Documento] debe ser numerico")
	@Range(min=1, max=10, message = "[Tipo Documento] su longitud de estar entre 1 y 10") 
    private int tipodoc;
    private String desctipodoc;
     
	@NotNull(message = "[Nro documento] es obligatorio")
	@Size(min = 1, max = 15, message = "[Nro documento] su longitud de estar entre 1 y 15")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Nro documento] tiene caracteres no validos.")
    private String nrodocumento;
	 
	@NotNull(message = "[Nombres] es obligatorio")
	@Size(min = 1, max = 60, message = "[Nombres] su longitud de estar entre 1 y 60")
	//@Pattern(regexp = "^[a-zA-Z0-9\u00f1\u00d1 ]+$", message = "[Nombres] tiene caracteres no validos.")
    private String nombres;

	@NotNull(message = "[Apellido Paterno] es obligatorio")
	@Size(min = 1, max = 60, message = "[Apellido Paterno] su longitud de estar entre 1 y 60")
	//@Pattern(regexp = "^[a-zA-Z0-9\u00f1\u00d1 ]+$", message = "[Apellido Paterno] tiene caracteres no validos.")
    private String apellidopaterno;

	@NotNull(message = "[Apellido Materno] es obligatorio")
	@Size(min = 1, max = 60, message = "[Apellido Materno] su longitud de estar entre 1 y 60")
	//@Pattern(regexp = "^[a-zA-Z0-9\u00f1\u00d1 ]+$", message = "[Apellido Materno] tiene caracteres no validos.")
    private String apellidomaterno;   
	 
	@NotNull(message = "[Celular] es obligatorio")
	@Size(min = 1, max = 20, message = "[Celular] su longitud de estar entre 1 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Celular] tiene caracteres no validos.")
    private String celular;

	//@NotNull(message = "[Telefono] es obligatorio")
	//@Size(min = 1, max = 20, message = "[Telefono] su longitud de estar entre 1 y 20")
	//@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Telefono] tiene caracteres no validos.")
    private String telefono;
	
	@NotNull(message = "[Correo] es obligatorio")
	@Size(min = 1, max = 100, message = "[Correo] su longitud de estar entre 1 y 100") 
    private String correo;
	
	@NotNull(message = "[Direccion] es obligatorio")
	@Size(min = 1, max = 100, message = "[Direccion] su longitud de estar entre 1 y 100")  
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

    @NotNull(message = "[Fecha de nacimiento] es obligatorio")
	//@Size(min = 10, max = 10, message = "[Fecha de nacimiento] su longitud debe tener 10 caracteres") 
	//@Pattern(regexp = "^[0-9]+$", message = "[Fecha de nacimiento] tiene caracteres no validos.")
    private String fecnacimiento;

    @NotNull(message = "[Codigo de DNI] es obligatorio")
	@Size(min = 1, max = 1, message = "[Codigo de DNI] su longitud debe tener 1 caracter") 
	@Pattern(regexp = "^[0-9]+$", message = "[Codigo de DNI] tiene caracteres no validos.")
    private String codigoverificadni;
     
    @NotNull(message = "[Codigo de ubigeo] es obligatorio")
	@Size(min = 6, max = 6, message = "[Fecha de ubigeo] su longitud debe tener 6 caracteres") 
	@Pattern(regexp = "^[0-9]+$", message = "[Fecha de ubigeo] tiene caracteres no validos.")
    private String codigoubigeo;
  
    private String codigoarchivo; 
    
    private String terminos;
    private String declaracion;
    private String observacion; 

    private String usuario;
    private String clave;
    
    private int flg_valido;
 
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

	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	
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

	public int getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(int tipodoc) {
		this.tipodoc = tipodoc;
	}

	public String getDesctipodoc() {
		return desctipodoc;
	}

	public void setDesctipodoc(String desctipodoc) {
		this.desctipodoc = desctipodoc;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidopaterno() {
		return apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getApellidomaterno() {
		return apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
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

	 
	public String getCodigoverificadni() {
		return codigoverificadni;
	}

	public void setCodigoverificadni(String codigoverificadni) {
		this.codigoverificadni = codigoverificadni;
	}

	public String getCodigoubigeo() {
		return codigoubigeo;
	}

	public void setCodigoubigeo(String codigoubigeo) {
		this.codigoubigeo = codigoubigeo;
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

	/*public int getFlg_valido() {
		return flg_valido;
	}

	public void setFlg_valido(int flg_valido) {
		this.flg_valido = flg_valido;
	}

	public int getFlg_tipo() {
		return flg_tipo;
	}

	public void setFlg_tipo(int flg_tipo) {
		this.flg_tipo = flg_tipo;
	}*/

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

	public String getFecnacimiento() {
		return fecnacimiento;
	}

	public void setFecnacimiento(String fecnacimiento) {
		this.fecnacimiento = fecnacimiento;
	}

	public String getIddepartamento() {
		return iddepartamento;
	}

	public void setIddepartamento(String iddepartamento) {
		this.iddepartamento = iddepartamento;
	}

	public String getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getIddistrito() {
		return iddistrito;
	}

	public void setIddistrito(String iddistrito) {
		this.iddistrito = iddistrito;
	}

	public int getFlg_valido() {
		return flg_valido;
	}

	public void setFlg_valido(int flg_valido) {
		this.flg_valido = flg_valido;
	}

	


	  
	 
	 
}
