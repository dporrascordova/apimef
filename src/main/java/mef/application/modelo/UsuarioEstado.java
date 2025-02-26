package mef.application.modelo;

public class UsuarioEstado {
	private long id_usuario;
	private String flg_estado; 

	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;

	private String usu_modificacion;
	private String fec_modificacion;
	private String ip_modificacion;

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

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getFlg_estado() {
		return flg_estado;
	}

	public void setFlg_estado(String flg_estado) {
		this.flg_estado = flg_estado;
	}

	public String getIp_modificacion() {
		return ip_modificacion;
	}

	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}

	 
}
