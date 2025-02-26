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

import mef.application.dao.SeguridadDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.SeguridadPerfil;
import mef.application.modelo.SeguridadPersonal;

@Repository
public class SeguridadDaoImpl implements SeguridadDao {

	@Value("${database.idsistema}")
	private String idsistema;

	@PersistenceContext
	EntityManager entityManager;

	@Value("${database.pck_seg_consulta}")
	private String pck_seg_consulta;
	
	@Override
	public Auditoria Perfil_Listar() {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_SISTEMA = Integer.valueOf(idsistema + "");
			int PI_FLG_ESTADO = 1;

			List<SeguridadPerfil> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_consulta + ".USP_SISTEMAS_PERFIL_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_SISTEMA).setParameter(2, PI_FLG_ESTADO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				SeguridadPerfil entidad = new SeguridadPerfil();
				entidad.setId_perfil(Integer.valueOf(row[0] + ""));
				entidad.setDesc_perfil(String.valueOf(row[3] + ""));

				lista.add(entidad);
			}
			System.out.println("Paso 6");
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Personal_Listar() {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			List<SeguridadPersonal> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_consulta + ".USP_PERSONAL_LISTAR")
					.registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				SeguridadPersonal entidad = new SeguridadPersonal();
				entidad.setId_personal(Long.valueOf(row[0] + ""));
				entidad.setNombre_personal(String.valueOf(row[1] + ""));

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
