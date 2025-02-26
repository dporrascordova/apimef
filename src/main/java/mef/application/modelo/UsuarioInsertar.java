package mef.application.modelo;

public class UsuarioInsertar {
	
	private String nombre; 
    private String apellido_paterno; 
    private String apellido_materno; 
	private String cod_usuario; 
    private String id_oficina; 
    private String id_usuario; 
    private String correo; 
    private int tipo_usuario;
    private int id_perfil;
	private String fec_activacion;
	private String fec_desactivacion;
    
    
    
    
   // private String id_persona;  
	
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
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
	public String getCod_usuario() {
		return cod_usuario;
	}
	public void setCod_usuario(String cod_usuario) {
		this.cod_usuario = cod_usuario;
	}
	public String getId_oficina() {
		return id_oficina;
	}
	public void setId_oficina(String id_oficina) {
		this.id_oficina = id_oficina;
	}
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getTipo_usuario() {
		return tipo_usuario;
	}
	public void setTipo_usuario(int tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	public int getId_perfil() {
		return id_perfil;
	}
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
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
	
	
}
