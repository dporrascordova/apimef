package mef.application.dao.impl;

import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.io.IOUtils;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mef.application.dao.NotificacionDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.NotificacionGrilla;

@Repository
public class NotificacionDaoImp  implements NotificacionDao{

	@PersistenceContext
	EntityManager entityManager;

	@Value("${database.seg_pck_consulta}")
	private String paquete_consulta;

	@Override
	public Auditoria Notificacion_Enviar_Listar(NotificacionGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_REMITENTE = modelo.getRemitente();
			String PI_ID_TIPO_DOCUMENTO = modelo.getId_tipo_documento();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_CORREO = modelo.getCorreo();
			String PI_ID_OFICINA = modelo.getId_oficina();
			String PI_ID_ESTADO = modelo.getId_estado();
			String PI_FEC_RECEPCION = modelo.getFec_recepcion();
			
			List<NotificacionGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_consulta + ".USP_NOTIFICA_ENVIAR_LISTAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_REMITENTE).setParameter(2, PI_ID_TIPO_DOCUMENTO)
					.setParameter(3, PI_NRO_DOCUMENTO).setParameter(4, PI_CORREO).setParameter(5, PI_ID_OFICINA)
					.setParameter(6, PI_ID_ESTADO)
					.setParameter(7, PI_FEC_RECEPCION);
	

			List<Object[]> TableST = query.getResultList();
			System.out.println(" Noti :  Notificacion_Enviar_Listar Total:" + TableST.size());
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				NotificacionGrilla entidad = new NotificacionGrilla();

				entidad.setId_persona(Long.valueOf(row[0] + ""));
				entidad.setId_usuario(Long.valueOf(row[1] + ""));
				entidad.setId_documento(Long.valueOf(row[2] + ""));
				entidad.setFec_recepcion(String.valueOf(row[3] + "")); 
				entidad.setRemitente(String.valueOf(row[4] + "")); 
				entidad.setDesc_tipo_documento(String.valueOf(row[5] + ""));
				entidad.setNro_documento(String.valueOf(row[6] + ""));
				entidad.setNro_folios(String.valueOf(row[7] + ""));
				entidad.setDesc_estado(String.valueOf(row[8] + "")); 
				entidad.setCorreo(String.valueOf(row[9] + ""));
				entidad.setDesc_oficina(String.valueOf(row[10] + "")); 
				entidad.setAsunto(String.valueOf(row[11] + ""));
				entidad.setHoja_ruta(String.valueOf(row[12] + ""));
				entidad.setUsu_creacion(String.valueOf(row[13] + ""));
				entidad.setFec_creacion(String.valueOf(row[14] + ""));
				entidad.setIp_creacion("-"); 
				entidad.setUsu_modificacion(String.valueOf(row[16] + ""));
				entidad.setFec_modificacion(String.valueOf(row[17] + ""));
				entidad.setIp_modificacion("-"); 			
				lista.add(entidad);
			}
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Notificacion_Enviado_Listar(NotificacionGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_REMITENTE = modelo.getRemitente();
			String PI_ID_TIPO_DOCUMENTO = modelo.getId_tipo_documento();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_CORREO = modelo.getCorreo();
			String PI_ID_OFICINA = modelo.getId_oficina();
			String PI_ID_ESTADO = modelo.getId_estado();
			String PI_FEC_INI = modelo.getFec_creacion() ;
			String PI_FEC_FIN = modelo.getFec_notificacion();
			System.out.println("noti enviados");
			
		    List<NotificacionGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_consulta + ".USP_NOTIFICA_ENVIADO_LISTAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_REMITENTE)
					.setParameter(2, PI_ID_TIPO_DOCUMENTO)
					.setParameter(3, PI_NRO_DOCUMENTO)
					.setParameter(4, PI_CORREO)
					.setParameter(5, PI_ID_OFICINA)
					.setParameter(6, PI_ID_ESTADO) 
					.setParameter(7, PI_FEC_INI)
					.setParameter(8, PI_FEC_FIN);
			
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				//System.out.println(i);
				NotificacionGrilla entidad = new NotificacionGrilla();

				entidad.setId_casilla(Long.valueOf(row[0] + ""));
				entidad.setId_documento(Long.valueOf(row[1] + ""));
				entidad.setFec_recepcion(String.valueOf(row[2] + ""));
				entidad.setFec_notificacion(String.valueOf(row[3] + ""));
				entidad.setRemitente(String.valueOf(row[4] + ""));
				entidad.setDesc_tipo_documento(String.valueOf(row[5] + ""));
				entidad.setNro_documento(String.valueOf(row[6] + ""));
				entidad.setCorreo(String.valueOf(row[7] + ""));
				entidad.setDesc_oficina(String.valueOf(row[8] + ""));
				entidad.setDesc_estado(String.valueOf(row[9] + ""));
				entidad.setAsunto(String.valueOf(row[10] + ""));
				entidad.setHoja_ruta(String.valueOf(row[11] + ""));
				entidad.setUsu_creacion(String.valueOf(row[12] + ""));
				entidad.setFec_creacion(String.valueOf(row[13] + ""));
				entidad.setIp_creacion("-"); 
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); 
				entidad.setDfec_notificacion(format.parse(entidad.getFec_notificacion()));
				
				entidad.setDfec_creacion(format.parse(entidad.getFec_creacion()));
				entidad.setFec_modificacion(String.valueOf(row[18] + "")); 
				entidad.setHoja_envio(String.valueOf(row[19] + "")); 	
				
				lista.add(entidad);
			}
			//System.out.println("termino noti enviados");
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}
	
	
	@Override
	public Auditoria Notificacion_Enviado_Paginado(NotificacionGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_REMITENTE = modelo.getRemitente();
			String PI_ID_TIPO_DOCUMENTO = modelo.getId_tipo_documento();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_HOJA_RUTA = modelo.getHoja_ruta();
			String PI_HOJA_ENVIO = modelo.getHoja_envio();
			String PI_NRO_SOLICITUD = modelo.getNro_solicitud();		
			String PI_CORREO = modelo.getCorreo();
			String PI_ID_OFICINA = modelo.getId_oficina();
			String PI_ID_ESTADO = modelo.getId_estado();
			String PI_FEC_INI = modelo.getFec_creacion() ;
			String PI_FEC_FIN = modelo.getFec_notificacion();
			System.out.println("NOTI ENVIADOS PAGINADO");
			System.out.println("nro_soli" + modelo.getNro_solicitud());
			
		    List<NotificacionGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_consulta + ".USP_NOTIFICA_ENVIADO_PAGINADO")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(14, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_REMITENTE)
					.setParameter(2, PI_ID_TIPO_DOCUMENTO)
					.setParameter(3, PI_NRO_DOCUMENTO)
					.setParameter(4, PI_CORREO)
					.setParameter(5, PI_HOJA_RUTA)
					.setParameter(6, PI_HOJA_ENVIO)
					.setParameter(7, PI_NRO_SOLICITUD)
					.setParameter(8, PI_ID_OFICINA)
					.setParameter(9, PI_ID_ESTADO) 
					.setParameter(10, PI_FEC_INI)
					.setParameter(11, PI_FEC_FIN)
					.setParameter(12, modelo.getNumpag())
					.setParameter(13, modelo.getNumreg());
			
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				//System.out.println(i);
				NotificacionGrilla entidad = new NotificacionGrilla();

				entidad.setId_casilla(Long.valueOf(row[0] + ""));
				entidad.setId_documento(Long.valueOf(row[1] + ""));
				entidad.setFec_recepcion(String.valueOf(row[2] + ""));
				entidad.setFec_notificacion(String.valueOf(row[3] + ""));
				entidad.setRemitente(String.valueOf(row[4] + ""));
				entidad.setDesc_tipo_documento(String.valueOf(row[5] + ""));
				entidad.setNro_documento(String.valueOf(row[6] + ""));
				entidad.setCorreo(String.valueOf(row[7] + ""));
				entidad.setDesc_oficina(String.valueOf(row[8] + ""));
				entidad.setDesc_estado(String.valueOf(row[9] + ""));
				entidad.setAsunto(String.valueOf(row[10] + ""));
				entidad.setHoja_ruta(String.valueOf(row[11] + ""));
				entidad.setUsu_creacion(String.valueOf(row[12] + ""));
				entidad.setFec_creacion(String.valueOf(row[13] + ""));
				entidad.setIp_creacion(Objects.toString(row[14], "-")); 
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); 
				entidad.setDfec_notificacion(format.parse(entidad.getFec_notificacion()));
				
				entidad.setDfec_creacion(format.parse(entidad.getFec_creacion()));
				entidad.setFec_modificacion(String.valueOf(row[18] + "")); 
				entidad.setHoja_envio(String.valueOf(row[19] + "")); 				
				entidad.setTotalreg(Integer.valueOf(row[21] + ""));
				entidad.setNumreg(Integer.valueOf(row[22] + ""));
				
				lista.add(entidad);
			}
			//System.out.println("termino noti enviados");
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}
}
