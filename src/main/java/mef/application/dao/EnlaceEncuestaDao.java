package mef.application.dao;

import mef.application.modelo.Auditoria;
import mef.application.modelo.EnlaceEncuesta;;

public interface EnlaceEncuestaDao {

	/**
	 * Crear y actualizar los enlaces de las encuestas de satisfaccion.
	 * 
	 * @param modelo
	 * @return Retorna un objecto
	 */
	public Auditoria crearMensajeEncuesta(EnlaceEncuesta modelo);

	/**
	 * Obtener encuesta activa
	 * 
	 * @return
	 */
	public Auditoria obtenerEncuestaActiva();
}
