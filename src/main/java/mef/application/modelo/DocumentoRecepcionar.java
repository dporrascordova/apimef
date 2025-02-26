package mef.application.modelo;

public class DocumentoRecepcionar {
	private Integer id_documento;
	private String usu_creacion;
	private String fecha_recepcion;

	public Integer getId_documento() {
		return id_documento;
	}

	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}

	public String getUsu_creacion() {
		return usu_creacion;
	}

	public void setUsu_creacion(String usu_creacion) {
		this.usu_creacion = usu_creacion;
	}

	public String getFecha_recepcion() {
		return fecha_recepcion;
	}

	public void setFecha_recepcion(String fecha_recepcion) {
		this.fecha_recepcion = fecha_recepcion;
	}

}
