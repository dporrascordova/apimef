package mef.application.modelo;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Auth implements Serializable {

	private static final long serialVersionUID = -8716260743080689756L;

	@NotNull(message = "[Nombre de usuario] es obligatorio")
	@Size(min = 5, max = 20, message = "[Nombre de usuario] su longitud de estar entre 5 y 20")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "[Nombre de usuario] tiene caracteres no validos.")
	private String user;

	@NotNull(message = "[Clave] es obligatorio")
	@Size(min = 5, max = 20, message = "[Clave] su longitud de estar entre 5 y 20") 
	private String password;
	private String captcha;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}