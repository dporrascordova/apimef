package mef.application.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mef.application.dao.DocumentoObservacionDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.DocumentoObservacion;

@Repository
public class DocumentoObservacionDaoImpl implements DocumentoObservacionDao {

	@PersistenceContext
	EntityManager entityManager;

	@Value("${database.seg_pck_consulta}")
	private String seg_pck_consulta;
	
	@Override
	public Auditoria DocumentoObservacion_Listar(DocumentoObservacion modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Long PI_ID_DOCUMENTO = Long.valueOf(modelo.getId_documento());

			List<DocumentoObservacion> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(seg_pck_consulta + ".USP_OBSERVACION_LISTAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_DOCUMENTO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				DocumentoObservacion entidad = new DocumentoObservacion();

				entidad.setObservacion(String.valueOf(row[0] + ""));
				entidad.setUsu_creacion(String.valueOf(row[1] + ""));
				entidad.setFec_creacion(String.valueOf(row[2] + ""));
				entidad.setIp_creacion("-");
				lista.add(entidad);
			}
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

}
