package mef.application.modelo;
 

public class DocumentoEstado {

	private long id_documento;
	private int id_estado_documento;
	 
	private String usu_modificacion; 
	private String ip_modificacion;
	
	public long getId_documento() {
		return id_documento;
	}
	public void setId_documento(long id_documento) {
		this.id_documento = id_documento;
	}
	public int getId_estado_documento() {
		return id_estado_documento;
	}
	public void setId_estado_documento(int id_estado_documento) {
		this.id_estado_documento = id_estado_documento;
	}
	public String getUsu_modificacion() {
		return usu_modificacion;
	}
	public void setUsu_modificacion(String usu_modificacion) {
		this.usu_modificacion = usu_modificacion;
	}
	public String getIp_modificacion() {
		return ip_modificacion;
	}
	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}
	
	
}
