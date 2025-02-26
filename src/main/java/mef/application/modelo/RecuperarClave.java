package mef.application.modelo;

import java.io.Serializable; 

public class RecuperarClave implements Serializable { 

    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sec_id_usuario_externo")
    //@SequenceGenerator(name="sec_id_usuario_externo", sequenceName = "sec_id_usuario_externo", allocationSize=1)
	//private int id_externo_natural; 

    private String UCorreo;
    private String UCodigo;
      
    private String UCaptcha;
    
	 
	public String getUCaptcha() {
		return UCaptcha;
	}
	public void setUCaptcha(String uCaptcha) {
		UCaptcha = uCaptcha;
	}
	public String getUCorreo() {
		return UCorreo;
	}
	public void setUCorreo(String uCorreo) {
		UCorreo = uCorreo;
	}
	public String getUCodigo() {
		return UCodigo;
	}
	public void setUCodigo(String uCodigo) {
		UCodigo = uCodigo;
	}
	 
	
}
