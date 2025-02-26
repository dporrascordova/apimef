package mef.application.modelo;

public class UsuarioPersona {
	
	private long id_usuario; 
	private long id_tipo_usuario;
    private String id_persona;  
    private String id_tipo_persona;  
    private String cod_usuario; 
    private int id_oficina; 
    private String desc_oficina; 
    private String nombre_usuario; 
    private String desc_perfil;
    private String nro_documento;

    private String apellido_paterno; 
    private String apellido_materno; 
    private String celular; 
    private String telefono;
    private String correo;
    private String direccion;
    private String desc_departamento;
    private String desc_provincia;
    private String desc_distrito;
    private String correo_copia;
    private int id_perfil; 
    
	public String getApellido_paterno() {
		return apellido_paterno;
	}
	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}
	public String getApellido_materno() {
		return apellido_materno;
	}
	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
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
	public String getDesc_departamento() {
		return desc_departamento;
	}
	public void setDesc_departamento(String desc_departamento) {
		this.desc_departamento = desc_departamento;
	}
	public String getDesc_provincia() {
		return desc_provincia;
	}
	public void setDesc_provincia(String desc_provincia) {
		this.desc_provincia = desc_provincia;
	}
	public String getDesc_distrito() {
		return desc_distrito;
	}
	public void setDesc_distrito(String desc_distrito) {
		this.desc_distrito = desc_distrito;
	}
	public long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
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
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}
	public String getDesc_perfil() {
		return desc_perfil;
	}
	public void setDesc_perfil(String desc_perfil) {
		this.desc_perfil = desc_perfil;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public String getCod_usuario() {
		return cod_usuario;
	}
	public void setCod_usuario(String cod_usuario) {
		this.cod_usuario = cod_usuario;
	}
	public long getId_tipo_usuario() {
		return id_tipo_usuario;
	}
	public void setId_tipo_usuario(long id_tipo_usuario) {
		this.id_tipo_usuario = id_tipo_usuario;
	}
	public String getId_persona() {
		return id_persona;
	}
	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}
	public String getId_tipo_persona() {
		return id_tipo_persona;
	}
	public void setId_tipo_persona(String id_tipo_persona) {
		this.id_tipo_persona = id_tipo_persona;
	}
	public String getCorreo_copia() {
		return correo_copia;
	}
	public void setCorreo_copia(String correo_copia) {
		this.correo_copia = correo_copia;
	}   
	
	
	
	public String getId_perfil() {
		return correo_copia;
	}
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
	}  
	
}
