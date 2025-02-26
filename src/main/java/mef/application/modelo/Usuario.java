package mef.application.modelo;

import java.io.Serializable;
 
 
public class Usuario implements Serializable {  

    private long id_usuario; 
    private long id_persona;  
    private int tipo_usuario;
    private int id_perfil;
	private String fec_activacion;
	private String fec_desactivacion;
	
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	
	public long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
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
	public long getId_persona() {
		return id_persona;
	}
	public void setId_persona(long id_persona) {
		this.id_persona = id_persona;
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
     
    
}
