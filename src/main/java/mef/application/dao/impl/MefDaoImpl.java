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

import mef.application.dao.MefDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Departamento;
import mef.application.modelo.Distrito;
import mef.application.modelo.OficinaEstado;
import mef.application.modelo.OficinaGrilla;
import mef.application.modelo.Oficinas;
import mef.application.modelo.Provincia;
import mef.application.modelo.TipoDocumento;

@Repository
public class MefDaoImpl implements MefDao {

	@Value("${database.pck_mef_consulta}")
	private String paquete_mef_consulta;

	@Value("${database.pck_mef_mantenimiento}")
	private String paquete_mef_mantenimiento;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Auditoria Mef_Servicio(int ID_SERVICIO) {
		System.out.println("Actualizar Servicio");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_SERVICIO = ID_SERVICIO;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_SERVICIO_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_SERVICIO);

			System.out.println("Mef_Servicio: Paso 1");
			List result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			System.out.println("Mef_Servicio: Paso 2");
			System.out.println(result.get(0).toString());
			auditoria.objeto = result.get(0).toString();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Mef_Servicio: " + auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Oficinas_Listar(OficinaGrilla modelo) {
		System.out.println("Oficinas");
		Auditoria auditoria = new Auditoria();
		List<OficinaGrilla> oficinas = new ArrayList<>();
		auditoria.Limpiar();
		try {
			String PI_CODIGO = modelo.getCodigo();
			String PI_ACRONIMO = modelo.getAcronimo();
			String PI_DESCRIPCION = modelo.getDescripcion();
			String PI_DESCRIPCIONCOMPLETA = modelo.getDescripcioncompleta();
			String PI_JEFE = modelo.getJefe();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_OFICINA_LISTAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_CODIGO).setParameter(2, PI_ACRONIMO).setParameter(3, PI_DESCRIPCION)
					.setParameter(4, PI_DESCRIPCIONCOMPLETA).setParameter(5, PI_JEFE);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				OficinaGrilla entidad = new OficinaGrilla();
				entidad.setIdunidad(Integer.valueOf(row[0] + ""));
				entidad.setAcronimo(row[1].toString());
				entidad.setCodigo(row[2].toString());
				entidad.setConjefe(row[3].toString());
				entidad.setDescripcion(row[4].toString());
				entidad.setDescripcioncompleta(row[5].toString());
				entidad.setFlagofgeneral(Integer.valueOf(row[6] + ""));
				entidad.setJefe(row[7].toString());
				entidad.setFlg_externo(Integer.valueOf(row[8] + ""));
				entidad.setFlg_interno(Integer.valueOf(row[9] + ""));
				oficinas.add(entidad);
			}
			auditoria.objeto = oficinas;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Tipo_Doc_Listar_Lx(TipoDocumento modelo) {
		//System.out.println("Tipo Documento listar");
		Auditoria auditoria = new Auditoria();
		List<TipoDocumento> tipodoc = new ArrayList<>();
		auditoria.Limpiar();
		try {
			String PI_DESC_TIPO_DOC = modelo.getDesc_tipodocumento();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_TIPO_DOC_LISTAR_L")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR).setParameter(1, PI_DESC_TIPO_DOC);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				TipoDocumento entidad = new TipoDocumento();
				entidad.setId_tipodocumento(Integer.valueOf(row[0] + ""));
				entidad.setDesc_tipodocumento(row[1].toString());
				entidad.setFlg_estado(row[2].toString());

				tipodoc.add(entidad);
			}
			auditoria.objeto = tipodoc;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}
	
	@Override
	public Auditoria Mef_Tipo_Doc_Estado(TipoDocumento modelo) {
		//System.out.println("Actualizar Oficinas estado externo");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_IDTIPODOC = Integer.valueOf(modelo.getId_tipodocumento());
			int PI_FLG_ESTADO = Integer.valueOf(modelo.getFlg_estado());
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_TIPO_DOC_ESTADO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_IDTIPODOC).setParameter(2, PI_FLG_ESTADO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
			}

			if (PI_CUENTA == 0) {
				auditoria.Rechazar(PI_MENSAJE);
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}
	
	@Override
	public Auditoria Mef_Oficinas_Listar_Externo(Oficinas modelo) {
		System.out.println("Oficinas para externo");
		Auditoria auditoria = new Auditoria();
		List<Oficinas> oficinas = new ArrayList<>();
		auditoria.Limpiar();
		try {
			int PI_FLG_EXTERNO = modelo.getFlg_externo();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_OFICINA_EX_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_FLG_EXTERNO);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				Oficinas entidad = new Oficinas();
				entidad.setIdunidad(Integer.valueOf(row[0] + ""));
				entidad.setAcronimo(row[1].toString());
				entidad.setCodigo(row[2].toString());
				entidad.setConjefe(row[3].toString());
				entidad.setDescripcion(row[4].toString());
				entidad.setDescripcioncompleta(row[5].toString());
				entidad.setFlagofgeneral(Integer.valueOf(row[6] + ""));
				entidad.setJefe(row[7].toString());
				entidad.setFlg_externo(Integer.valueOf(row[8] + ""));
				entidad.setFlg_interno(Integer.valueOf(row[9] + ""));
				oficinas.add(entidad);
			}
			auditoria.objeto = oficinas;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Oficinas_Listar_Interno(Oficinas modelo) {
		System.out.println("Oficinas para interno");
		Auditoria auditoria = new Auditoria();
		List<Oficinas> oficinas = new ArrayList<>();
		auditoria.Limpiar();
		try {
			int PI_FLG_INTERNO = modelo.getFlg_interno();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_OFICINA_IN_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_FLG_INTERNO);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				Oficinas entidad = new Oficinas();
				entidad.setIdunidad(Integer.valueOf(row[0] + ""));
				entidad.setAcronimo(row[1].toString());
				entidad.setCodigo(row[2].toString());
				entidad.setConjefe(row[3].toString());
				entidad.setDescripcion(row[4].toString());
				entidad.setDescripcioncompleta(row[5].toString());
				entidad.setFlagofgeneral(Integer.valueOf(row[6] + ""));
				entidad.setJefe(row[7].toString());
				entidad.setFlg_externo(Integer.valueOf(row[8] + ""));
				entidad.setFlg_interno(Integer.valueOf(row[9] + ""));
				oficinas.add(entidad);
			}
			auditoria.objeto = oficinas;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Tipo_Doc_Listar() {
		System.out.println("Tipo Documento listar");
		Auditoria auditoria = new Auditoria();
		List<TipoDocumento> tipodoc = new ArrayList<>();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_TIPO_DOC_LISTAR")
					.registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				TipoDocumento entidad = new TipoDocumento();
				entidad.setId_tipodocumento(Integer.valueOf(row[0] + ""));
				entidad.setDesc_tipodocumento(row[1].toString());

				tipodoc.add(entidad);
			}
			auditoria.objeto = tipodoc;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	
	@Override
	public Auditoria Mef_Dep_Listar() {
		System.out.println("Dep listar");
		Auditoria auditoria = new Auditoria();
		List<Departamento> dep = new ArrayList<>();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_DEP_LISTAR")
					.registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				Departamento entidad = new Departamento();
				entidad.setId_departamento(Integer.valueOf(row[0] + ""));
				entidad.setDesc_departamento(row[1].toString());

				dep.add(entidad);
			}
			auditoria.objeto = dep;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Prov_Listar(Provincia modelo) {
		System.out.println("Dep listar");
		Auditoria auditoria = new Auditoria();
		List<Provincia> dep = new ArrayList<>();
		auditoria.Limpiar();
		try {
			int PI_ID_DEPARTAMENTO = modelo.getId_departamento();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_PROV_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_DEPARTAMENTO);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				Provincia entidad = new Provincia();
				entidad.setId_provincia(Integer.valueOf(row[0] + ""));
				entidad.setId_departamento(Integer.valueOf(row[1] + ""));
				entidad.setDesc_provincia(row[2].toString());

				dep.add(entidad);
			}
			auditoria.objeto = dep;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Dist_Listar(Distrito modelo) {
		System.out.println("Dep listar");
		Auditoria auditoria = new Auditoria();
		List<Distrito> dep = new ArrayList<>();
		auditoria.Limpiar();
		try {
			int PI_ID_PROVINCIA = modelo.getId_provincia();
			int PI_ID_DEPARTAMENTO = modelo.getId_departamento();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_consulta + ".USP_DIST_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_PROVINCIA).setParameter(2, PI_ID_DEPARTAMENTO);

			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				Distrito entidad = new Distrito();
				entidad.setId_distrito(Integer.valueOf(row[0] + ""));
				entidad.setId_provincia(Integer.valueOf(row[1] + ""));
				entidad.setId_departamento(Integer.valueOf(row[2] + ""));
				entidad.setDesc_distrito(row[3].toString());

				dep.add(entidad);
			}
			auditoria.objeto = dep;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Oficinas_Actualizar(String CADENA) {
		System.out.println("Actualizar Oficinas");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CADENA = CADENA;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_OFICINA_ACTUALIZAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, PI_CADENA);
			query.execute();
			query.unwrap(ProcedureOutputs.class).release();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Oficinas_Estado_Externo(OficinaEstado oficina) {
		System.out.println("Actualizar Oficinas estado externo");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_IDUNIDAD = Integer.valueOf(oficina.getIdunidad());
			int PI_FLG_EXTERNO = Integer.valueOf(oficina.getFlg_externo());
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_OFICINA_ESTADO_EX")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_IDUNIDAD).setParameter(2, PI_FLG_EXTERNO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
			}

			if (PI_CUENTA == 0) {
				auditoria.Rechazar(PI_MENSAJE);
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_TipoDoc_Actualizar(String CADENA) {
		System.out.println("Actualizar Tipo Documento");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CADENA = CADENA;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_TIPO_DOC_ACTUALIZAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, PI_CADENA);
			query.execute();
			query.unwrap(ProcedureOutputs.class).release();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Dep_Actualizar(String CADENA) {
		System.out.println("Actualizar Departamento");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CADENA = CADENA;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_DEP_ACTUALIZAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, PI_CADENA);
			query.execute();
			query.unwrap(ProcedureOutputs.class).release();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Prov_Actualizar(String CADENA) {
		System.out.println("Actualizar Provincia");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CADENA = CADENA;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_PROV_ACTUALIZAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, PI_CADENA);
			query.execute();
			query.unwrap(ProcedureOutputs.class).release();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Mef_Dist_Actualizar(String CADENA) {
		System.out.println("Actualizar Distrito");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CADENA = CADENA;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_mantenimiento + ".USP_DIST_ACTUALIZAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, PI_CADENA);
			query.execute();
			query.unwrap(ProcedureOutputs.class).release();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}
	
}
