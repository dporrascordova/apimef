package mef.application.modelo;

public class DocumentoObservacion {
	
	private String id_documento;
	private String observacion;
	private String usu_creacion; 
	private String fec_creacion;  
	private String ip_creacion;
	private String fecha_anulacion;
	private int id_estado_documento;
	
	public String getId_documento() {
		return id_documento;
	}
	public void setId_documento(String id_documento) {
		this.id_documento = id_documento;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
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
	public String getFecha_anulacion() {
		return fecha_anulacion;
	}
	public void setFecha_anulacion(String fecha_anulacion) {
		this.fecha_anulacion = fecha_anulacion;
	}
	public int getId_estado_documento() {
		return id_estado_documento;
	}
	public void setId_estado_documento(int id_estado_documento) {
		this.id_estado_documento = id_estado_documento;
	}  
	
}
