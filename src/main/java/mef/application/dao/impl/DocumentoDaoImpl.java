package mef.application.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mef.application.dao.DocumentoDao;
import mef.application.infrastructure.CommonHelpers;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Documento;
import mef.application.modelo.DocumentoAnexo;
import mef.application.modelo.DocumentoEstado;
import mef.application.modelo.DocumentoFinalizar;
import mef.application.modelo.DocumentoGrilla;
import mef.application.modelo.DocumentoObservacion;
import mef.application.modelo.RespuestaMessage;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DocumentoDaoImpl implements DocumentoDao {

	@Value("${database.package}")
	private String databasePackage;

	@Value("${database.seg_pck_consulta}")
	private String seg_pck_consulta;

	@Value("${database.pck_ven_mantenimiento}")
	private String pck_ven_mantenimiento;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Auditoria Documento_Listar(Documento doc) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<Documento> lista = new ArrayList<>();
		System.out.println("ENTRO XD 1");
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_LISTAR_DOCUMENTOS")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, doc.getId_estado_documento()).setParameter(2, doc.getId_documento())
					.setParameter(3, doc.getId_oficina()).setParameter(4, doc.getId_tipo_documento())
					.setParameter(5, doc.getNro_documento()).setParameter(6, doc.getAsunto())
					.setParameter(7, doc.getFec_ini()).setParameter(8, doc.getFec_fin())
					.setParameter(9, doc.getGrupo_estados()).setParameter(10, doc.getId_usuario());

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				Documento docMap = new Documento();

				docMap.setId_documento(Integer.valueOf(row[0] + ""));
				docMap.setAsunto(String.valueOf(row[1]));
				docMap.setCodigo_archivo(String.valueOf(row[2]));

				if (String.valueOf(row[3]) != null) {
					docMap.setFec_creacion(String.valueOf(row[3]));
					docMap.setSrt_fecha_creacion(String.valueOf(row[3]));
				}

				if (String.valueOf(row[5]) != null)
					docMap.setSrt_fecha_recibido(String.valueOf(row[5]));

				docMap.setFlg_estado(String.valueOf(row[6]));
				docMap.setId_estado_documento(Integer.valueOf(row[7] + ""));
				docMap.setDesc_estado_documento(String.valueOf(row[8] + ""));
				docMap.setDesc_oficina(String.valueOf(row[10] + ""));
				docMap.setDesc_tipo_documento(String.valueOf(row[12]));
				docMap.setId_tipo_usuario(String.valueOf(row[13]));
				docMap.setId_usuario(String.valueOf(row[14]));
				docMap.setIp_creacion("-");
				docMap.setIp_modificacion("-");
				docMap.setNro_documento(String.valueOf(row[17]));
				docMap.setNro_folios(Integer.valueOf(row[18] + ""));
				docMap.setUsu_creacion(String.valueOf(row[19]));
				docMap.setUsu_modificacion(String.valueOf(row[20]));
				docMap.setCuenta_anexos(Integer.valueOf(row[21] + ""));

				if (row[23] != null)
					docMap.setAnio(Integer.valueOf(row[23] + ""));
				docMap.setNumero_sid(Objects.toString(row[24], ""));
				docMap.setNombre_persona(Objects.toString(row[25], ""));
				docMap.setHoja_ruta(Objects.toString(row[26], "-"));

				lista.add(docMap);
			}
			auditoria.objeto = lista;
			entityManager.close();
		} catch (Exception e) {
			auditoria.Error(e);
			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		}

		return auditoria;
	}

	@Override
	public Auditoria Documento_Listar_Paginado(Documento doc) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<Documento> lista = new ArrayList<>();
		try {

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_LISTAR_DOCUMENTOS_PAGINADO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)

					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(14, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(15, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(16, Class.class, ParameterMode.REF_CURSOR)

					.setParameter(1, doc.getId_estado_documento()).setParameter(2, doc.getId_documento())
					.setParameter(3, doc.getId_oficina()).setParameter(4, doc.getId_tipo_documento())
					.setParameter(5, doc.getNro_documento()).setParameter(6, doc.getAsunto())
					.setParameter(7, doc.getRazon_social()).setParameter(8, doc.getDestino())
					.setParameter(9, doc.getFec_ini()).setParameter(10, doc.getFec_fin())
					.setParameter(11, doc.getGrupo_estados()).setParameter(12, doc.getId_usuario())
					.setParameter(13, Objects.toString(doc.getOrden(), "")).setParameter(14, doc.getNumpag())
					.setParameter(15, doc.getNumreg());

			List<Object[]> TableST = query.getResultList();
			// query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				Documento docMap = new Documento();

				docMap.setId_documento(Integer.valueOf(row[0] + ""));
				docMap.setAsunto(String.valueOf(row[1]));
				docMap.setCodigo_archivo(String.valueOf(row[2]));

				if (String.valueOf(row[3]) != null) {
					docMap.setFec_creacion(String.valueOf(row[3]));
					docMap.setSrt_fecha_creacion(String.valueOf(row[3]));
				}

				if (String.valueOf(row[4]) != null)
					docMap.setStr_fec_modificacion(String.valueOf(row[4]));

				if (String.valueOf(row[5]) != null)
					docMap.setSrt_fecha_recibido(String.valueOf(row[5]));

				docMap.setFlg_estado(String.valueOf(row[6]));
				docMap.setId_estado_documento(Integer.valueOf(row[7] + ""));
				docMap.setDesc_estado_documento(String.valueOf(row[8] + ""));
				docMap.setDesc_oficina(String.valueOf(row[10] + ""));
				docMap.setDesc_tipo_documento(String.valueOf(row[12]));
				docMap.setId_tipo_usuario(String.valueOf(row[13]));
				docMap.setId_usuario(String.valueOf(row[14]));
				docMap.setIp_creacion("-");
				docMap.setIp_modificacion("-");
				docMap.setNro_documento(String.valueOf(row[17]));
				docMap.setNro_folios(Integer.valueOf(row[18] + ""));
				docMap.setUsu_creacion(String.valueOf(row[19]));
				docMap.setUsu_modificacion(String.valueOf(row[20]));
				docMap.setCuenta_anexos(Integer.valueOf(row[21] + ""));

				if (row[23] != null)
					docMap.setAnio(Integer.valueOf(row[23] + ""));
				docMap.setNumero_sid(Objects.toString(row[24], ""));
				docMap.setNombre_persona(Objects.toString(row[25], ""));
				docMap.setTotalreg(Integer.valueOf(row[26] + ""));
				docMap.setHoja_ruta(Objects.toString(row[27], "-"));
				docMap.setFecha_anulacion(Objects.toString(row[28], "-"));
				docMap.setNumreg(Integer.valueOf(row[29] + ""));
				docMap.setFecha_observacion(Objects.toString(row[30], "-"));
				docMap.setFecha_subsanacion(Objects.toString(row[31], "-"));
				docMap.setUsu_asignacion(String.valueOf(row[32]));
				docMap.setAsignado(!Objects.toString(row[32], "").isEmpty());
				docMap.setNomb_usu_asignacion(String.valueOf(row[33]));
				docMap.setHoja_ruta(Objects.toString(row[34], "-"));
				docMap.setObsSgdd(Objects.toString(row[35], "-"));

				lista.add(docMap);
			}
			auditoria.objeto = lista;
		} catch (Exception e) {
			auditoria.Error(e);
			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		} finally {
			entityManager.close();
		}

		return auditoria;
	}

	@Override
	public Auditoria DocumentoSolicitud_Listar(DocumentoGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<DocumentoGrilla> lista = new ArrayList<>();
		try {
			/*
			 * DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); String finicio =
			 * modelo.getFec_ini() == null || modelo.getFec_ini().equals("") ? "" :
			 * dateFormat.format(new
			 * SimpleDateFormat("yyyy-MM-dd").parse(modelo.getFec_ini().toString())); String
			 * ffin = modelo.getFec_fin() == null || modelo.getFec_fin().equals("") ? "" :
			 * dateFormat.format(new
			 * SimpleDateFormat("yyyy-MM-dd").parse(modelo.getFec_fin().toString()));
			 */

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(seg_pck_consulta + ".USP_DOCUMENTO_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, modelo.getId_estado_documento()).setParameter(2, modelo.getId_documento())
					.setParameter(3, modelo.getId_oficina()).setParameter(4, modelo.getId_tipo_documento())
					.setParameter(5, modelo.getNro_documento()).setParameter(6, modelo.getAsunto())
					.setParameter(7, modelo.getFec_ini()).setParameter(8, modelo.getFec_fin())
					.setParameter(9, modelo.getId_usuario());

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				DocumentoGrilla docMap = new DocumentoGrilla();

				docMap.setId_documento(Integer.valueOf(row[0] + ""));
				docMap.setAsunto(String.valueOf(row[1]));
				docMap.setCodigo_archivo(String.valueOf(row[2]));

				if (String.valueOf(row[3]) != null) {
					docMap.setFec_creacion(String.valueOf(row[3]));
					// docMap.setSrt_fecha_creacion(String.valueOf(row[3]));
				}

				if (String.valueOf(row[5]) != null)
					docMap.setFec_recibido(String.valueOf(row[5]));

				docMap.setFlg_estado(String.valueOf(row[6]));
				docMap.setId_estado_documento(Integer.valueOf(row[7] + ""));
				docMap.setDesc_oficina(String.valueOf(row[9] + ""));
				docMap.setDesc_tipo_documento(String.valueOf(row[11]));
				docMap.setId_tipo_usuario(String.valueOf(row[12]));
				docMap.setId_usuario(String.valueOf(row[13]));
				docMap.setIp_creacion("-");
				docMap.setIp_modificacion("-");
				docMap.setNro_documento(String.valueOf(row[16]));
				docMap.setNro_folios(Integer.valueOf(row[17] + ""));
				docMap.setUsu_creacion(String.valueOf(row[18]));
				docMap.setUsu_modificacion(String.valueOf(row[19]));
				docMap.setCuenta_anexos(Integer.valueOf(row[20] + ""));

				if (row[20] != null)
					docMap.setAnio(Integer.valueOf(row[20] + ""));
				docMap.setNumero_sid(String.valueOf(row[21] + ""));
				docMap.setDesc_estado_documento(String.valueOf(row[24] + ""));
				lista.add(docMap);
			}
			auditoria.objeto = lista;
		} catch (Exception e) {
			auditoria.Error(e);
			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		}

		return auditoria;
	}

	String cambiarcaracteres(String texto) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < texto.length(); i++) {
			int codePoint = texto.codePointAt(i);
			if (codePoint > 0xFFFF) {
				i++;
			}
			if ((codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
					|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
					|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
					|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF))) {
				sb.appendCodePoint(codePoint);
			}
		}
		return sb.toString();
	}

	@Override
	public Auditoria Documento_ListarUno(Integer id_documento) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Documento documento = null;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_OBTENER_DOCUMENTO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, id_documento);

			Object[] row = (Object[]) query.getSingleResult();
			query.unwrap(ProcedureOutputs.class).release();

			documento = new Documento();
			if (row != null) {
				documento.setId_documento(Integer.valueOf(row[0] + ""));
				documento.setAsunto(String.valueOf(row[1]));
				documento.setCodigo_archivo(String.valueOf(row[2]));
				documento.setFec_creacion(Objects.toString(row[3], ""));
				documento.setSrt_fecha_creacion(Objects.toString(row[3], ""));
				documento.setStr_fec_modificacion(Objects.toString(row[4], ""));
				documento.setSrt_fecha_recibido(Objects.toString(row[5], ""));
				documento.setFlg_estado(String.valueOf(row[6]));
				documento.setId_estado_documento(Integer.valueOf(row[7] + ""));
				documento.setId_oficina(Integer.valueOf(row[8] + ""));
				documento.setId_tipo_documento(String.valueOf(row[9]));
				documento.setId_tipo_usuario(String.valueOf(row[10]));
				documento.setId_usuario(String.valueOf(row[11]));
				documento.setIp_creacion("-");
				documento.setIp_modificacion("-");
				documento.setNro_documento(String.valueOf(row[14]));
				documento.setNro_folios(Integer.valueOf(row[15] + ""));
				documento.setUsu_creacion(String.valueOf(row[16]));
				documento.setUsu_modificacion(Objects.toString(row[17], ""));

				if (row[19] != null)
					documento.setAnio(Integer.valueOf(row[18] + ""));
				documento.setNumero_sid(Objects.toString(row[19], ""));
				documento.setHoja_ruta(Objects.toString(row[20], ""));
				documento.setNombre_persona(Objects.toString(row[21], ""));
				documento.setNrodocumento_persona(Objects.toString(row[22], ""));
				documento.setTipopersona(Integer.valueOf(row[23] + ""));
				documento.setId_persona(String.valueOf(row[24]));
				documento.setDesc_tipo_documento(String.valueOf(row[25]));
				documento.setDesc_oficina(String.valueOf(row[26]));
				// documento.setHoja_ruta(Objects.toString(row[28], ""));

				System.out.println("Asunto C:"+cambiarcaracteres(documento.getAsunto()));
				System.out.println("Asunto:"+documento.getAsunto());
				
			} else {
				auditoria.rechazar = true;
				documento.setId_documento(0);
			}
			auditoria.objeto = documento;
		} catch (NoResultException ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Documento_Insertar(Documento doc) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_ASUNTO = doc.getAsunto();
			String PI_CODIGO_ARCHIVO = doc.getCodigo_archivo();
			String PI_FLG_ESTADO = doc.getFlg_estado();
			int PI_ESTADO_DOC = doc.getId_estado_documento();
			int PI_ID_OFICINA = doc.getId_oficina();
			String PI_TIPO_DOC = doc.getId_tipo_documento();
			String PI_NRO_DOCUMENTO = doc.getNro_documento();
			int PI_NRO_FOLIOS = doc.getNro_folios();
			String PI_HOJA_RUTA = doc.getHoja_ruta();
			String PI_USU_CREACION = doc.getUsu_creacion();
			String PI_ID_USUARIO = doc.getId_usuario();
			String PI_IP_CREACION = doc.getIp_creacion();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_INSERT_DOCUMENTO")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(14, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(15, String.class, ParameterMode.OUT)
					.setParameter(1, doc.getAsunto()).setParameter(2, doc.getCodigo_archivo())
					.setParameter(3, doc.getFlg_estado()).setParameter(4, doc.getId_estado_documento())
					.setParameter(5, doc.getId_oficina()).setParameter(6, doc.getId_tipo_documento())
					.setParameter(7, doc.getNro_documento()).setParameter(8, doc.getNro_folios())
					.setParameter(9, doc.getHoja_ruta()).setParameter(10, doc.getUsu_creacion())
					.setParameter(11, doc.getId_usuario()).setParameter(12, PI_IP_CREACION);

			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(13);
			Object retVal = query.getOutputParameterValue(14);
			String mensaje = (String) query.getOutputParameterValue(15);
			query.unwrap(ProcedureOutputs.class).release();
			if (!estado.equals(100)) {
				auditoria.rechazar = true;
				auditoria.mensaje_salida = "Se ha producido un error al registrar la solicitud";
			}
			auditoria.objeto = retVal;
			// System.out.println(retVal);
			// result = new RespuestaMessage(estado, retVal, mensaje);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	@Transactional
	public Auditoria Documento_FlgServicioError(int id_documento) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_UPDATE_FLGERROR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT).setParameter(1, id_documento);
			entityManager.joinTransaction();
			query.execute();

			// Integer estado = (Integer) query.getOutputParameterValue(13);
			// Object retVal = query.getOutputParameterValue(14);
			// String mensaje = (String) query.getOutputParameterValue(15);
			query.unwrap(ProcedureOutputs.class).release();

			// System.out.println(retVal);
			// result = new RespuestaMessage(estado, retVal, mensaje);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Actualizar(Documento doc) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_UPDATE_DOCUMENTO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(13, String.class, ParameterMode.OUT)
					.setParameter(1, doc.getId_documento()).setParameter(2, doc.getAsunto())
					.setParameter(3, doc.getCodigo_archivo()).setParameter(4, doc.getFlg_estado())
					.setParameter(5, doc.getId_estado_documento()).setParameter(6, doc.getId_oficina())
					.setParameter(7, doc.getId_tipo_documento()).setParameter(8, doc.getNro_documento())
					.setParameter(9, doc.getNro_folios()).setParameter(10, doc.getHoja_ruta())
					.setParameter(11, doc.getUsu_modificacion());
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(12);
			String mensaje = (String) query.getOutputParameterValue(13);
			query.unwrap(ProcedureOutputs.class).release();
			auditoria.objeto = mensaje;
			// result = new RespuestaMessage(estado, mensaje);
			// System.out.println(estado);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public RespuestaMessage crearAnexo(DocumentoAnexo anexo) {
		RespuestaMessage result = null;
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_INSERT_DOC_ANEXO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, Boolean.class, ParameterMode.IN)

					.registerStoredProcedureParameter(10, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.OUT)
					.setParameter(1, anexo.getId_documento()).setParameter(2, anexo.getCodigo_archivo())
					.setParameter(3, anexo.getNombre_archivo()).setParameter(4, anexo.getExtension_archivo())
					.setParameter(5, anexo.getTamanio_archivo()).setParameter(6, anexo.getMimetype_archivo())
					.setParameter(7, anexo.getUsu_creacion()).setParameter(8, anexo.getFlg_link())
					.setParameter(9, anexo.isCrea_MPI());
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(10);
			String mensaje = (String) query.getOutputParameterValue(11);
			query.unwrap(ProcedureOutputs.class).release();
			// System.out.println(mensaje);
			result = new RespuestaMessage(estado, mensaje);
		} catch (Exception e) {
			// auditoria.Error(e);
			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		}
		return result;
	}

	@Override
	public List<DocumentoAnexo> getAnexosDocumentoById(Integer id_documento) {
		List<DocumentoAnexo> anexos = new ArrayList<>();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_LISTAR_DOCUMENTO_ANEXOS")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, id_documento);

			List<Object[]> TableST = query.getResultList();
//			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				DocumentoAnexo anexo = new DocumentoAnexo();
				anexo.setId_documento(Integer.valueOf(row[0] + ""));
				anexo.setCodigo_archivo(String.valueOf(row[1]));
				anexo.setNombre_archivo(String.valueOf(row[2]));
				anexo.setExtension_archivo(String.valueOf(row[3]));
				anexo.setTamanio_archivo(Integer.valueOf(row[4] + ""));
				anexo.setMimetype_archivo(String.valueOf(row[5]));
				anexo.setOrden(Short.valueOf(row[6] + ""));

				if (row[7] != null)
					anexo.setFec_creacion(String.valueOf(row[7]));

				if (row[8] != null)
					anexo.setFec_modificacion(CommonHelpers.formatoStringToDate(String.valueOf(row[8])));

				anexo.setUsu_creacion(String.valueOf(row[9]));
				anexo.setUsu_modificacion(String.valueOf(row[10]));
				anexo.setFlg_link(String.valueOf(row[11]));
				anexo.setCrea_MPI(String.valueOf(row[12]).equals("1"));
				anexo.setFlg_estado(Integer.valueOf(row[13] + ""));

				anexo.setEstadoAnexo(Integer.valueOf(row[14] + ""));
				anexo.setIdAnexo(String.valueOf(row[15]));

				anexos.add(anexo);
			}
		} catch (NoResultException e) {
			System.out.println("VACIO: " + e.getMessage());
			// return null;
		} catch (Exception e) {

			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		}

		return anexos;
	}

	@Override
	public RespuestaMessage anularAnexos(Integer documentoId, String codigos, String usuarioModifica) {
		RespuestaMessage result = null;
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(databasePackage + ".P_ANULAR_ANEXOS")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT).setParameter(1, documentoId)
					.setParameter(2, codigos).setParameter(3, usuarioModifica);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(4);
			String mensaje = (String) query.getOutputParameterValue(5);
			query.unwrap(ProcedureOutputs.class).release();
			// System.out.println(estado);
			result = new RespuestaMessage(estado, mensaje);
		} catch (Exception e) {
			result = new RespuestaMessage(0, "Error");
			// auditoria.Error(ex);
			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Auditoria Documento_Recepcionar(Integer documentoId, Integer anio, String numeroSid, int nro_folios,
			String asunto, String usuarioModifica, String ipmodifica, int ID_OFICINA, String ID_TIPO_DOCUMENTO,
			String HojaRuta, String Nro_Documento, String Desc_congresista, int id_prioridad) {

		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();

		System.out.println("congresista " + Desc_congresista);
		System.out.println("prioridad " + id_prioridad);

		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_RECEPCIONAR_DOCUMENTO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(14, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(15, String.class, ParameterMode.OUT)

					.setParameter(1, documentoId).setParameter(2, anio).setParameter(3, numeroSid)
					.setParameter(4, asunto).setParameter(5, nro_folios).setParameter(6, ID_OFICINA)
					.setParameter(7, ID_TIPO_DOCUMENTO).setParameter(8, HojaRuta).setParameter(9, Nro_Documento)
					.setParameter(10, usuarioModifica).setParameter(11, ipmodifica).setParameter(12, id_prioridad)
					.setParameter(13, Desc_congresista == null ? "" : Desc_congresista);

			query.execute();

			/*
			 * IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE, IN_USU_MODIFICACION IN
			 * T_VUDM_DOCUMENTO.USU_MODIFICACION%TYPE, IN_ANIO IN
			 * T_VUDM_DOCUMENTO.ANIO%TYPE, IN_NUMERO_SID IN
			 * T_VUDM_DOCUMENTO.NUMERO_SID%TYPE, OUT_ESTADO OUT NUMBER, OUT_MENSAJE OUT
			 * VARCHAR2) AS
			 */

			Integer estado = (Integer) query.getOutputParameterValue(14);
			String mensaje = (String) query.getOutputParameterValue(15);
			query.unwrap(ProcedureOutputs.class).release();

			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.objeto = estado;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
			// e.printStackTrace();
			// System.out.println("ERROR STORE: " + e.getMessage());
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Observar(DocumentoObservacion obs) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Long PI_ID_DOCUMENTO = Long.valueOf(obs.getId_documento());

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_INSERT_DOC_OBSERVACION")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)
					.setParameter(1, PI_ID_DOCUMENTO).setParameter(2, obs.getObservacion())
					.setParameter(3, obs.getFecha_anulacion()).setParameter(4, obs.getUsu_creacion());

			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(5);
			String mensaje = (String) query.getOutputParameterValue(6);
			query.unwrap(ProcedureOutputs.class).release();

			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.mensaje_salida = "Se realizo con exito la observacion de la solicitud";
				auditoria.objeto = PI_ID_DOCUMENTO;
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Actualizar_Estado(DocumentoObservacion obs) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			// System.out.println(obs.getId_documento());
			// System.out.println("ENTRO");
			Long PI_ID_DOCUMENTO = Long.valueOf(obs.getId_documento());
			Long PI_ID_ESTADO = Long.valueOf(obs.getId_estado_documento());

			System.out.println("id_doc " + PI_ID_DOCUMENTO);
			System.out.println(" estado " + PI_ID_ESTADO);

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_UPDATE_ESTADO_DOC")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
					.setParameter(1, PI_ID_DOCUMENTO).setParameter(2, PI_ID_ESTADO);

			query.execute();

			// Integer estado = (Integer) query.getOutputParameterValue(4);
			String mensaje = (String) query.getOutputParameterValue(4);
			query.unwrap(ProcedureOutputs.class).release();
			auditoria.objeto = mensaje;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria DocumentoActualizar_Estado(long ID_DOCUMENTO , long ID_ESTADO ) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			// System.out.println(obs.getId_documento());
			// System.out.println("ENTRO");
			 

				StoredProcedureQuery query = entityManager
						.createStoredProcedureQuery(databasePackage + ".P_UPDATE_ESTADO_DOC")
						.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
						.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
						.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
						.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
						.setParameter(1, ID_DOCUMENTO).setParameter(2, ID_ESTADO);

				query.execute();

				// Integer estado = (Integer) query.getOutputParameterValue(4);
				//String mensaje = (String) query.getOutputParameterValue(4);
				query.unwrap(ProcedureOutputs.class).release();
			//auditoria.objeto = mensaje;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}
	
	@Override
	public Auditoria Documento_Observacion_Listar(Integer documentoId) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<DocumentoObservacion> docObs = new ArrayList<>();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_LISTAR_DOCUMENTO_OBS")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, documentoId);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				DocumentoObservacion obs = new DocumentoObservacion();
				obs.setId_documento(String.valueOf(row[0] + ""));
				obs.setObservacion(String.valueOf(row[1]));
				obs.setFec_creacion(String.valueOf(row[2]));
				// obs.setStr_fec_modificacion(String.valueOf(row[3]));
				obs.setUsu_creacion(String.valueOf(row[4] + ""));
				// obs.setUsu_modificacion(String.valueOf(row[5] + ""));
				docObs.add(obs);
			}
			auditoria.objeto = docObs;
		} catch (NoResultException ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Documento_Estado(DocumentoEstado modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {

			Long PI_ID_DOCUMENTO = modelo.getId_documento();
			int PI_ID_ESTADO_DOCUMENTO = modelo.getId_estado_documento();
			String PI_USU_MODIFICACION = modelo.getUsu_modificacion();
			String PI_IP_MODIFICACION = modelo.getIp_modificacion();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_ven_mantenimiento + ".USP_DOCUMENTO_ESTADO")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)
					.setParameter(1, PI_ID_DOCUMENTO).setParameter(2, PI_ID_ESTADO_DOCUMENTO)
					.setParameter(3, PI_USU_MODIFICACION).setParameter(4, PI_IP_MODIFICACION);
			query.execute();

			Integer cuenta = (Integer) query.getOutputParameterValue(5);
			String mensaje = (String) query.getOutputParameterValue(6);
			query.unwrap(ProcedureOutputs.class).release();
			if (cuenta == 0)
				auditoria.Rechazar(mensaje);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Atender(DocumentoFinalizar doc) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {

			String PI_USU_MODIFICACION = doc.getUsu_modificacion();
			String PI_NUMERO_SID = doc.getNumero_sid();
			String PI_ANIO = doc.getAnio();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_ESTADO_DOCUMENTO")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT).setParameter(1, PI_NUMERO_SID)
					.setParameter(2, PI_ANIO).setParameter(3, PI_USU_MODIFICACION);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(4);
			String mensaje = (String) query.getOutputParameterValue(5);
			query.unwrap(ProcedureOutputs.class).release();
			if (estado.equals(0)) {
				auditoria.Rechazar(mensaje);
				// auditoria.mensaje_salida = "Se ha producido un error al registrar la
				// solicitud";
			} else {
				auditoria.objeto = estado;
			}
			// System.out.println(retVal);
			// result = new RespuestaMessage(estado, retVal, mensaje);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_HojaRuta(DocumentoFinalizar doc) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_NUMERO_SID = doc.getNumero_sid();
			String PI_ANIO = doc.getAnio();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_HOJARUTA_DOCUMENTO")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT).setParameter(1, PI_NUMERO_SID)
					.setParameter(2, PI_ANIO);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(3);
			String mensaje = (String) query.getOutputParameterValue(4);
			query.unwrap(ProcedureOutputs.class).release();
			if (estado.equals(0)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.objeto = estado;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Asignar(Integer id_documento, String username) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_ASIGNAR_DOCUMENTO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT).setParameter(1, id_documento)
					.setParameter(2, username);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(3);
			String mensaje = (String) query.getOutputParameterValue(4);
			query.unwrap(ProcedureOutputs.class).release();
			if (estado.equals(0)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.objeto = estado;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Validar_Recep_Observar(Integer id_documento, String username) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_RECEP_Y_OBSERVAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT).setParameter(1, id_documento)
					.setParameter(2, username);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(3);
			String mensaje = (String) query.getOutputParameterValue(4);
			query.unwrap(ProcedureOutputs.class).release();
			if (estado.equals(0)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.objeto = estado;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public RespuestaMessage anularAnexo(Integer documentoId, String codigoArchivo, String usuarioModifica) {
		RespuestaMessage result = null;
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(databasePackage + ".P_ANULAR_ANEXO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT).setParameter(1, documentoId)
					.setParameter(2, codigoArchivo).setParameter(3, usuarioModifica);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(4);
			String mensaje = (String) query.getOutputParameterValue(5);
			query.unwrap(ProcedureOutputs.class).release();
			// System.out.println(estado);
			result = new RespuestaMessage(estado, mensaje);
		} catch (Exception e) {
			result = new RespuestaMessage(0, "Error");
			// auditoria.Error(ex);
			// e.printStackTrace();
			System.out.println("ERROR STORE: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Auditoria Validar_Hoja_Ruta_Anexo(Integer documentoId, String hojaRuta) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_VAL_HOJA_RUTA_ANEXO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT).setParameter(1, documentoId)
					.setParameter(2, hojaRuta);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(3);
			String mensaje = (String) query.getOutputParameterValue(4);
			query.unwrap(ProcedureOutputs.class).release();
			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.objeto = estado;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Desasignar(Integer id_documento) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_DESASIGNAR_DOCUMENTO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT).setParameter(1, id_documento);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(2);
			String mensaje = (String) query.getOutputParameterValue(3);
			query.unwrap(ProcedureOutputs.class).release();
			if (estado.equals(0)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.mensaje_salida = "Se realizo con exito la desasignacion";
				auditoria.objeto = estado;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_NoPresentar(Integer documentoId) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_DOCUMENTO_NOPRESENTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT).setParameter(1, documentoId);
			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(2);
			String mensaje = (String) query.getOutputParameterValue(3);
			query.unwrap(ProcedureOutputs.class).release();
			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.mensaje_salida = "Se realizo con oxito la actualizacion de la solicitud";
				auditoria.objeto = documentoId;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	@Transactional
	public Auditoria Documento_Agregar_HojaRuta(Integer documentoId, Integer anio, String numeroSid, String usuario) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
			try {
				StoredProcedureQuery query = entityManager
						.createStoredProcedureQuery(databasePackage + ".P_DOCUMENTO_HOJARUTA")
						.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT)
						.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT).setParameter(1, documentoId)
						.setParameter(2, anio).setParameter(3, numeroSid).setParameter(4, usuario);

				entityManager.joinTransaction();
				query.execute();

				Integer estado = (Integer) query.getOutputParameterValue(5);
				String mensaje = (String) query.getOutputParameterValue(6);
//			query.unwrap(ProcedureOutputs.class).release();
			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.mensaje_salida = "Se realizo con oxito la actualizacion de la hoja de ruta";
				auditoria.objeto = documentoId;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
			ex.printStackTrace();
		}
		return auditoria;
	}

	@Override
	public Auditoria Documento_Recepcionar(Integer documentoId, Date fechaRecepcion, String usuarioModifica) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_DOCUMENTO_RECEPCIONAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT).setParameter(1, documentoId)
					.setParameter(2, fechaRecepcion).setParameter(3, usuarioModifica);

			query.execute();

			Integer estado = (Integer) query.getOutputParameterValue(4);
			String mensaje = (String) query.getOutputParameterValue(5);
			query.unwrap(ProcedureOutputs.class).release();
			if (!estado.equals(100)) {
				auditoria.Rechazar(mensaje);
			} else {
				auditoria.mensaje_salida = "Se realizo con oxito la recepcion de la solicitud";
				auditoria.objeto = documentoId;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	@Transactional
	public Auditoria Documento_Listar_PorEstado(Integer estadoId) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<Documento> lista = new ArrayList<>();

		try {
			Documento documento = null;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_LISTAR_DOCUMENTO_POR_ESTADO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, estadoId);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);

				try {
					// Verificar si row[23] es null ANTES de procesar
					if (row[23] == null) {
						throw new NullPointerException("tipoPersona es null en el registro " + i);
					}

					// Si llegamos aquí, row[23] NO es null
					documento = new Documento();
					documento.setId_documento(Integer.valueOf(row[0] + ""));
					documento.setAsunto(String.valueOf(row[1]));
					documento.setCodigo_archivo(String.valueOf(row[2]));
					documento.setFec_creacion(Objects.toString(row[3], ""));
					documento.setSrt_fecha_creacion(Objects.toString(row[3], ""));
					documento.setStr_fec_modificacion(Objects.toString(row[4], ""));
					documento.setSrt_fecha_recibido(Objects.toString(row[5], ""));
					documento.setFlg_estado(String.valueOf(row[6]));
					documento.setId_estado_documento(Integer.valueOf(row[7] + ""));
					documento.setId_oficina(Integer.valueOf(row[8] + ""));
					documento.setId_tipo_documento(String.valueOf(row[9]));
					documento.setId_tipo_usuario(String.valueOf(row[10]));
					documento.setId_usuario(String.valueOf(row[11]));
					documento.setIp_creacion(String.valueOf(row[12]));
					documento.setIp_modificacion(String.valueOf(row[13]));
					documento.setNro_documento(String.valueOf(row[14]));
					documento.setNro_folios(Integer.valueOf(row[15] + ""));
					documento.setUsu_creacion(String.valueOf(row[16]));
					documento.setUsu_modificacion(Objects.toString(row[17], ""));

					if (row[18] != null) {
						documento.setAnio(Integer.valueOf(row[18] + ""));
					}
					documento.setNumero_sid(Objects.toString(row[19], ""));
					documento.setHoja_ruta(Objects.toString(row[20], ""));
					documento.setNombre_persona(Objects.toString(row[21], ""));
					documento.setNrodocumento_persona(Objects.toString(row[22], ""));
					documento.setTipopersona(Integer.valueOf(row[23] + "")); // Aquí sabemos que no es null
					documento.setId_persona(String.valueOf(row[24]));
					documento.setDesc_tipo_documento(String.valueOf(row[25]));
					documento.setDesc_oficina(String.valueOf(row[26]));

					lista.add(documento);

				} catch (NullPointerException e) {
					// Capturamos específicamente cuando row[23] es null
					System.err.println("Error en registro " + i + ": " + e.getMessage());
					// También puedes usar un logger: logger.warn("Error en registro {}: {}", i, e.getMessage());
				} catch (Exception e) {
					// Capturamos otros errores inesperados
					System.err.println("Error inesperado en registro " + i + ": " + e.getMessage());
				}
			}
			auditoria.objeto = lista;
			entityManager.close();
		} catch (NoResultException ex) {
			ex.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return auditoria;
	}
	
	@Override
	public Auditoria Documento_Listar_PorEstado2(Integer estadoId) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<Documento> lista = new ArrayList<>();

		try {
			Documento documento = null;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_LISTAR_DOCUMENTO_POR_ESTADO2")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, estadoId);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				documento = new Documento();
				documento.setId_documento(Integer.valueOf(row[0] + ""));
				documento.setNumero_sid(Objects.toString(row[1], ""));
				documento.setAnio(Integer.valueOf(row[2] + ""));
				//documento.setHoja_ruta(Objects.toString(row[1], "")); 

				lista.add(documento);
			}
			auditoria.objeto = lista;
			entityManager.close();
		} catch (NoResultException ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return auditoria;
	}





	@Override
	public Auditoria Actualizar_Estado(long ID_DOCUMENTO , long ID_ESTADO, String des_error ) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			// System.out.println(obs.getId_documento());
			// System.out.println("ENTRO");


			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery("SISVENVI.PQ_ESTADOS_SGDD" + ".P_UPDATE_ESTADO_DOC")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT)
					.setParameter(1, ID_DOCUMENTO)
					.setParameter(2, ID_ESTADO)
					.setParameter(3, des_error);

			query.execute();

			// Integer estado = (Integer) query.getOutputParameterValue(4);
			//String mensaje = (String) query.getOutputParameterValue(4);
			query.unwrap(ProcedureOutputs.class).release();
			//auditoria.objeto = mensaje;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}



	@Override
	public Auditoria Documento_Listar_Pendiente_Bandeja(String tab) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<Documento> lista = new ArrayList<>();

		try {
			Documento documento = null;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery("SISVENVI.PQ_ESTADOS_SGDD" + ".P_LISTAR_PENDIENTES_TAB")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, tab);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				if( row[0] !=null & row[1] !=null & row[2] !=null & row[3] !=null ){
					documento = new Documento();

					documento.setId_documento(Integer.valueOf( row[0] != null ? row[0]+"" : ""));
					documento.setNumero_sid(Objects.toString(row[2] != null ? row[2] : "", ""));
					documento.setAnio(Integer.valueOf( row[3] != null ? row[3]+"" : ""));
					documento.setHoja_ruta(Objects.toString(row[1] != null ? row[2] : "", ""));

					lista.add(documento);
				}

			}
			auditoria.objeto = lista;
			entityManager.close();
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
