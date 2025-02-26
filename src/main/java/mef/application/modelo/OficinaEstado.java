package mef.application.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OficinaEstado {

	@NotNull(message = "[Codigo de Oficina] no puede ser nulo")
	@Pattern(regexp = "^[0-9]+$", message = "[Codigo de Oficina] debe ser numerico") 
	@Size(min = 1, max = 10, message = "[Codigo de Oficina] su longitud debe estar entre 1 y 10") 
	private String idunidad; 

	@NotNull(message = "[Estado externo] no puede ser nulo")
	@Pattern(regexp = "^[0-9]+$", message = "[Estado externo] debe ser numerico") 
	@Size(min = 1, max = 1, message = "[Estado externo] su longitud debe ser de un digito") 
	private String flg_externo;

	public String getIdunidad() {
		return idunidad;
	}

	public void setIdunidad(String idunidad) {
		this.idunidad = idunidad;
	}

	public String getFlg_externo() {
		return flg_externo;
	}

	public void setFlg_externo(String flg_externo) {
		this.flg_externo = flg_externo;
	}
	
}
