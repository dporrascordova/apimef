package mef.application.modelo;

import java.io.Serializable;

public class Auditoria implements Serializable {

	public Object objeto;
	public String mensaje_salida; // no se encontro solicitud en BD
	public String error_log;
	public Boolean ejecucion_procedimiento; // true // false -- "ERROR 404"
	public Boolean rechazar; // false si lo encontro -- true no lo encontro rechazas y mustras
	public Boolean error_lista;

	public void Limpiar() {
		error_lista = false;
		rechazar = false;
		mensaje_salida = "";
		error_log = "";
		ejecucion_procedimiento = true;
	}

	public void Error(Exception ex) {
		error_lista = false;
		if (ex.getMessage() != null)
			mensaje_salida = ex.getMessage();
		else
			mensaje_salida = ex.toString(); 
		error_log = ex.toString();
		ejecucion_procedimiento = false;
		rechazar = true;
	}
	
	public void ErrorSTD(Exception ex) {
		Error(ex);
		mensaje_salida = "Error de servicio del STD: "+ mensaje_salida;
	}

	public void Rechazar(String mensaje) {
		error_lista = false;
		error_log = mensaje;
		mensaje_salida = mensaje;
		rechazar = true;
	}

}
