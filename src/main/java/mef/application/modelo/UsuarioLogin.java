package mef.application.modelo;

public class UsuarioLogin {
	private long id_usuario;
	private String cod_usuario;
	private String clave_usuario;
	
	public String getCod_usuario() {
		return cod_usuario;
	}
	public void setCod_usuario(String cod_usuario) {
		this.cod_usuario = cod_usuario;
	}
	public String getClave_usuario() {
		return clave_usuario;
	}
	public void setClave_usuario(String clave_usuario) {
		this.clave_usuario = clave_usuario;
	}
	public long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

}
