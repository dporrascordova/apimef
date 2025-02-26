package mef.application.dao.impl;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mef.application.dao.EnlaceEncuestaDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Documento;
import mef.application.modelo.EnlaceEncuesta;

@Repository
public class EnlaceEncuestaDaoImpl implements EnlaceEncuestaDao {

	@Value("${database.pck_ven_enlace_encuesta}")
	private String pck_ven_enlace_encuesta;

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Auditoria crearMensajeEncuesta(EnlaceEncuesta modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			
			Integer id = modelo.getId_encuesta() == null ? 0 : modelo.getId_encuesta().intValue();
		
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_ven_enlace_encuesta + ".P_CREAR_MENSAJE_ENCUESTA")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)
					.setParameter(1, id)
					.setParameter(2, modelo.getMensaje_encuesta())
					.setParameter(3, modelo.getUrl_encuesta())
					.setParameter(4, modelo.getIp_creacion())
					.setParameter(5, modelo.getUsu_creacion());
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(6);
			String mensaje = (String) query.getOutputParameterValue(7);
			Integer retVal = (Integer) query.getOutputParameterValue(8);
			query.unwrap(ProcedureOutputs.class).release();
			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.mensaje_salida = "Se realizo con exito la operacion";
				auditoria.objeto = retVal;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria obtenerEncuestaActiva() {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			EnlaceEncuesta enlaceEncuesta = null;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_ven_enlace_encuesta + ".P_OBTENER_ENCUESTA_ACTIVA")
					.registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR);

			Object[] row = (Object[]) query.getSingleResult();
			query.unwrap(ProcedureOutputs.class).release();
			enlaceEncuesta = new EnlaceEncuesta();
	        
			if (row != null) {
				enlaceEncuesta.setId_encuesta(Long.valueOf(row[0] + ""));
				enlaceEncuesta.setMensaje_encuesta(String.valueOf(row[1]));
				enlaceEncuesta.setUrl_encuesta(String.valueOf(row[2]));
				enlaceEncuesta.setUsu_creacion(String.valueOf(row[3]));
				enlaceEncuesta.setFec_creacion(String.valueOf(row[4]));
				enlaceEncuesta.setIp_creacion(String.valueOf(row[5]));
				enlaceEncuesta.setUsu_modificacion(String.valueOf(row[6]));
				enlaceEncuesta.setFec_modificacion(String.valueOf(row[7]));
				enlaceEncuesta.setIp_modificacion(String.valueOf(row[8]));	
				enlaceEncuesta.setMensaje_html(String.valueOf(row[9]));	
				enlaceEncuesta.setFlg_estado(true);
			} else {
				auditoria.rechazar = true;
			}
			auditoria.objeto = enlaceEncuesta;
		} catch (NoResultException ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}
}
