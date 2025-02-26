package mef.application.modelo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Table(name = "T_BITM_EXTERNO_NATURAL")
public class UsuarioNatural implements Serializable {
	
    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sec_id_usuario_externo")
    //@SequenceGenerator(name="sec_id_usuario_externo", sequenceName = "sec_id_usuario_externo", allocationSize=1)
	//private int id_externo_natural; 

    private MultipartFile UFile;  
    private int UIdPersona; 
    private String UTipoPersona; 
    private String UDescTipoPersona; 
    private int UTipoDoc;
    private String UDescTipoDoc;
    private int UDocumento;
    private String Nat_Nombres;
    private String Nat_Ape_Pat;
    private String Nat_Ape_Mat;
    private String Jur_RUC;
    private String Jur_RazonSocial;  
    private int UCelular;
    private int UTelefono;
    private String UCorreo;
    private String UDireccion;
    private String UDepartamento;
    private String UProvincia;
    private String UDistrito;
    private String UNacimiento;
    private String UCodigoVerificaDNI;
    private String UCodigoUbigeo;
    private String UCodigoDNI;
    private String UTerminos;
    private String UDeclaracion;
    private String observacion;
    
    private int rep_legal_DNI;
    private String rep_legal_Nombres;
    private String rep_legal_Ape_Pat;
    private String rep_legal_Ape_Mat;
    private String rep_legal_direccion;
    private int rep_legal_celular;
    private String rep_legal_correo;
    
    private int delegado_DNI;
    private String delegado_Nombres;
    private String delegado_Ape_Pat;
    private String delegado_Ape_Mat;
    private String delegado_Direccion;
    private int delegado_Celular;
    private String delegado_Correo;

    private int flg_valido;
    private int flg_tipo;

    private String usuario;
    private String clave;
    
    private String UCaptcha;
    
	public int getUTipoDoc() {
		return UTipoDoc;
	}
	public void setUTipoDoc(int uTipoDoc) {
		UTipoDoc = uTipoDoc;
	}
	public String getUTipoPersona() {
		return UTipoPersona;
	}
	public void setUTipoPersona(String uTipoPersona) {
		UTipoPersona = uTipoPersona;
	}
	public int getUDocumento() {
		return UDocumento;
	}
	public void setUDocumento(int uDocumento) {
		UDocumento = uDocumento;
	}
	public String getNat_Nombres() {
		return Nat_Nombres;
	}
	public void setNat_Nombres(String nat_Nombres) {
		Nat_Nombres = nat_Nombres;
	}
	public String getNat_Ape_Pat() {
		return Nat_Ape_Pat;
	}
	public void setNat_Ape_Pat(String nat_Ape_Pat) {
		Nat_Ape_Pat = nat_Ape_Pat;
	}
	public String getNat_Ape_Mat() {
		return Nat_Ape_Mat;
	}
	public void setNat_Ape_Mat(String nat_Ape_Mat) {
		Nat_Ape_Mat = nat_Ape_Mat;
	}
	public int getUCelular() {
		return UCelular;
	}
	public void setUCelular(int uCelular) {
		UCelular = uCelular;
	}
	public int getUTelefono() {
		return UTelefono;
	}
	public void setUTelefono(int uTelefono) {
		UTelefono = uTelefono;
	}
	public String getUCorreo() {
		return UCorreo;
	}
	public void setUCorreo(String uCorreo) {
		UCorreo = uCorreo;
	}
	public String getUDireccion() {
		return UDireccion;
	}
	public void setUDireccion(String uDireccion) {
		UDireccion = uDireccion;
	}
	public String getUDepartamento() {
		return UDepartamento;
	}
	public void setUDepartamento(String uDepartamento) {
		UDepartamento = uDepartamento;
	}
	public String getUProvincia() {
		return UProvincia;
	}
	public void setUProvincia(String uProvincia) {
		UProvincia = uProvincia;
	}
	public String getUDistrito() {
		return UDistrito;
	}
	public void setUDistrito(String uDistrito) {
		UDistrito = uDistrito;
	}
	public String getUNacimiento() {
		return UNacimiento;
	}
	public void setUNacimiento(String uNacimiento) {
		UNacimiento = uNacimiento;
	}
	public String getUCodigoDNI() {
		return UCodigoDNI;
	}
	public void setUCodigoDNI(String uCodigoDNI) {
		UCodigoDNI = uCodigoDNI;
	}
	public String getUCodigoUbigeo() {
		return UCodigoUbigeo;
	}
	public void setUCodigoUbigeo(String uCodigoUbigeo) {
		UCodigoUbigeo = uCodigoUbigeo;
	}
	public String getUTerminos() {
		return UTerminos;
	}
	public void setUTerminos(String uTerminos) {
		UTerminos = uTerminos;
	}
	public String getUDeclaracion() {
		return UDeclaracion;
	}
	public void setUDeclaracion(String uDeclaracion) {
		UDeclaracion = uDeclaracion;
	}
	public String getJur_RUC() {
		return Jur_RUC;
	}
	public void setJur_RUC(String jur_RUC) {
		Jur_RUC = jur_RUC;
	}
	public String getJur_RazonSocial() {
		return Jur_RazonSocial;
	}
	public void setJur_RazonSocial(String jur_RazonSocial) {
		Jur_RazonSocial = jur_RazonSocial;
	}
	public MultipartFile getUFile() {
		return UFile;
	}
	public void setUFile(MultipartFile uFile) {
		UFile = uFile;
	}
	public String getUCodigoVerificaDNI() {
		return UCodigoVerificaDNI;
	}
	public void setUCodigoVerificaDNI(String uCodigoVerificaDNI) {
		UCodigoVerificaDNI = uCodigoVerificaDNI;
	}
	public int getRep_legal_DNI() {
		return rep_legal_DNI;
	}
	public void setRep_legal_DNI(int rep_legal_DNI) {
		this.rep_legal_DNI = rep_legal_DNI;
	}
	public String getRep_legal_Nombres() {
		return rep_legal_Nombres;
	}
	public void setRep_legal_Nombres(String rep_legal_Nombres) {
		this.rep_legal_Nombres = rep_legal_Nombres;
	}
	public String getRep_legal_Ape_Pat() {
		return rep_legal_Ape_Pat;
	}
	public void setRep_legal_Ape_Pat(String rep_legal_Ape_Pat) {
		this.rep_legal_Ape_Pat = rep_legal_Ape_Pat;
	}
	public String getRep_legal_Ape_Mat() {
		return rep_legal_Ape_Mat;
	}
	public void setRep_legal_Ape_Mat(String rep_legal_Ape_Mat) {
		this.rep_legal_Ape_Mat = rep_legal_Ape_Mat;
	}
	public String getRep_legal_direccion() {
		return rep_legal_direccion;
	}
	public void setRep_legal_direccion(String rep_legal_direccion) {
		this.rep_legal_direccion = rep_legal_direccion;
	}
	public int getRep_legal_celular() {
		return rep_legal_celular;
	}
	public void setRep_legal_celular(int rep_legal_celular) {
		this.rep_legal_celular = rep_legal_celular;
	}
	public String getRep_legal_correo() {
		return rep_legal_correo;
	}
	public void setRep_legal_correo(String rep_legal_correo) {
		this.rep_legal_correo = rep_legal_correo;
	}
	public int getDelegado_DNI() {
		return delegado_DNI;
	}
	public void setDelegado_DNI(int delegado_DNI) {
		this.delegado_DNI = delegado_DNI;
	}
	public String getDelegado_Nombres() {
		return delegado_Nombres;
	}
	public void setDelegado_Nombres(String delegado_Nombres) {
		this.delegado_Nombres = delegado_Nombres;
	}
	public String getDelegado_Ape_Pat() {
		return delegado_Ape_Pat;
	}
	public void setDelegado_Ape_Pat(String delegado_Ape_Pat) {
		this.delegado_Ape_Pat = delegado_Ape_Pat;
	}
	public String getDelegado_Ape_Mat() {
		return delegado_Ape_Mat;
	}
	public void setDelegado_Ape_Mat(String delegado_Ape_Mat) {
		this.delegado_Ape_Mat = delegado_Ape_Mat;
	}
	public String getDelegado_Direccion() {
		return delegado_Direccion;
	}
	public void setDelegado_Direccion(String delegado_Direccion) {
		this.delegado_Direccion = delegado_Direccion;
	}
	public int getDelegado_Celular() {
		return delegado_Celular;
	}
	public void setDelegado_Celular(int delegado_Celular) {
		this.delegado_Celular = delegado_Celular;
	}
	public String getDelegado_Correo() {
		return delegado_Correo;
	}
	public void setDelegado_Correo(String delegado_Correo) {
		this.delegado_Correo = delegado_Correo;
	}
	public String getUCaptcha() {
		return UCaptcha;
	}
	public void setUCaptcha(String uCaptcha) {
		UCaptcha = uCaptcha;
	}
	public int getUIdPersona() {
		return UIdPersona;
	}
	public void setUIdPersona(int uIdPersona) {
		UIdPersona = uIdPersona;
	}
	public String getUDescTipoDoc() {
		return UDescTipoDoc;
	}
	public void setUDescTipoDoc(String uDescTipoDoc) {
		UDescTipoDoc = uDescTipoDoc;
	}
	public String getUDescTipoPersona() {
		return UDescTipoPersona;
	}
	public void setUDescTipoPersona(String uDescTipoPersona) {
		UDescTipoPersona = uDescTipoPersona;
	}
	public int getFlg_valido() {
		return flg_valido;
	}
	public void setFlg_valido(int flg_valido) {
		this.flg_valido = flg_valido;
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
	public int getFlg_tipo() {
		return flg_tipo;
	}
	public void setFlg_tipo(int flg_tipo) {
		this.flg_tipo = flg_tipo;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	 
	

}