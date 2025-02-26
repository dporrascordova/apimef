package mef.application.modelo;

public class EnlaceEncuesta {

	private Long id_encuesta;
	private String mensaje_encuesta;
	private String url_encuesta;
	private boolean flg_estado;
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	private String usu_modificacion;
	private String fec_modificacion;
	private String ip_modificacion;
	private String mensaje_html;

	public Long getId_encuesta() {
		return id_encuesta;
	}

	public void setId_encuesta(Long id_encuesta) {
		this.id_encuesta = id_encuesta;
	}

	public String getMensaje_encuesta() {
		return mensaje_encuesta;
	}

	public void setMensaje_encuesta(String mensaje_encuesta) {
		this.mensaje_encuesta = mensaje_encuesta;
	}

	public String getUrl_encuesta() {
		return url_encuesta;
	}

	public void setUrl_encuesta(String url_encuesta) {
		this.url_encuesta = url_encuesta;
	}

	public boolean isFlg_estado() {
		return flg_estado;
	}

	public void setFlg_estado(boolean flg_estado) {
		this.flg_estado = flg_estado;
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

	public String getIp_modificacion() {
		return ip_modificacion;
	}

	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}

	public String getMensaje_html() {
		return mensaje_html;
	}

	public void setMensaje_html(String mensaje_html) {
		this.mensaje_html = mensaje_html;
	}

}
