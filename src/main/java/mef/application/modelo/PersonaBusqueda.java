package mef.application.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class PersonaBusqueda {

	private int idpersona;

	@Size(min = 0, max = 10, message = "[Tipo Persona] su longitud de estar entre 1 y 10")
	@Pattern(regexp = "^[0-9]+$", message = "[Tipo Persona] tiene caracteres no validos.")
	private String tipopersona;
	private String desctipopersona;

	@Positive(message = "[Tipo Documento] debe ser numerico")
	@Range(min = 0, max = 10, message = "[Tipo Documento] su longitud de estar entre 1 y 10")
	private int tipodoc;
	private String desctipodoc;

	@Size(min = 0, max = 15, message = "[Nro Documento] su longitud de estar entre 0 y 15")
	@Pattern(regexp = "^[0-9]+$", message = "[Nro Documento] tiene caracteres no validos.")
	private String nrodocumento;

	@Size(min = 0, max = 100, message = "[Razon Social] su longitud de estar entre 1 y 100")
	private String nombre;

	@Size(min = 0, max = 20, message = "[Celular] su longitud de estar entre 1 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Celular] tiene caracteres no validos.")
	private String celular;

	@Size(min = 1, max = 20, message = "[Telefono] su longitud de estar entre 1 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Telefono] tiene caracteres no validos.")
	private String telefono;

	@Size(min = 1, max = 100, message = "[Correo] su longitud de estar entre 1 y 100")
	private String correo;

	@Size(min = 1, max = 150, message = "[Direccion] su longitud de estar entre 1 y 150")
	private String direccion;

	@Size(min = 1, max = 10, message = "[Departamento] su longitud debe estar entre 1 y 10")
	@Pattern(regexp = "^[0-9]+$", message = "[Departamento] tiene caracteres no validos.")
	private String iddepartamento;
	private String departamento;

	@Size(min = 1, max = 10, message = "[Provincia] su longitud debe estar entre 1 y 10")
	@Pattern(regexp = "^[0-9]+$", message = "[Provincia] tiene caracteres no validos.")
	private String idprovincia;
	private String provincia;

	@Size(min = 1, max = 10, message = "[Distrito] su longitud debe estar entre 1 a 10 caracteres")
	@Pattern(regexp = "^[0-9]+$", message = "[Distrito] tiene caracteres no validos.")
	private String iddistrito;
	private String distrito;

	private String codigoarchivo;

	private String terminos;
	private String declaracion;
	private String observacion;

	private String usuario;
	private String clave;

	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

}
