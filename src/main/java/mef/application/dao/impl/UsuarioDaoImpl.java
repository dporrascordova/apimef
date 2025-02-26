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

import mef.application.dao.UsuarioDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Modulos;
import mef.application.modelo.NotificacionGrilla;
import mef.application.modelo.RecuperarClave;
import mef.application.modelo.UsuarioActivacion;
import mef.application.modelo.UsuarioEstado;
import mef.application.modelo.UsuarioInsertar;
import mef.application.modelo.UsuarioLogin;
import mef.application.modelo.UsuarioOlvidoClave;
import mef.application.modelo.UsuarioPersona;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@Value("${database.seg_pck_mef_perfil}")
	private String paquete_mef_perfil;

	@Value("${database.seg_pck_usuario}")
	private String paquete_seg_pck_usuario;

	@Value("${database.seg_pck_login}")
	private String paquete_seg_pck_login;

	@Value("${database.pck_seg_menu}")
	private String pck_seg_menu;

	@Value("${database.idsistema}")
	private String idsistema;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Auditoria Usuario_Insertar(UsuarioInsertar modelo) {
		System.out.println("vas");
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			//System.out.println("Llego222222");
			long PI_ID_PERSONA = 0;
			int PI_ID_SISTEMA = Integer.valueOf(idsistema + "");
			int PI_ID_TIPO_USUARIO = modelo.getTipo_usuario();
			int PI_ID_PERFIL = modelo.getId_perfil();

			String PI_NOMBRE = modelo.getNombre();
			String PI_APELLIDO_PATERNO = modelo.getApellido_paterno();
			String PI_APELLIDO_MATERNO = modelo.getApellido_materno();
			String PI_COD_USUARIO = modelo.getCod_usuario();
			String PI_ID_OFICINA = modelo.getId_oficina();
			String PI_ID_USUARIO = modelo.getId_usuario();
			String PI_CORREO = modelo.getCorreo();

			String PI_USU_CREACION = modelo.getUsu_creacion();
			String PI_IP_CREACION = modelo.getIp_creacion();
			String PI_FEC_ACT = modelo.getFec_activacion() ;
			String PI_FEC_DESACT = modelo.getFec_desactivacion();
 
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_seg_pck_usuario + ".USP_USUARIO_INSERTAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(14, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(15, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(16, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_PERSONA)
					.setParameter(2, PI_ID_SISTEMA)
					.setParameter(3, PI_ID_TIPO_USUARIO)
					.setParameter(4, PI_ID_PERFIL) 
					.setParameter(5, PI_NOMBRE)
					.setParameter(6, PI_APELLIDO_PATERNO)
					.setParameter(7, PI_APELLIDO_MATERNO)
					.setParameter(8, PI_COD_USUARIO)
					.setParameter(9, PI_ID_OFICINA)
					.setParameter(10, PI_ID_USUARIO)
					.setParameter(11, PI_CORREO) 
					.setParameter(12, PI_USU_CREACION)
					.setParameter(13, PI_IP_CREACION)
					.setParameter(14, PI_FEC_ACT)
					.setParameter(15, PI_FEC_DESACT);
 
			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i); 
				PI_CUENTA = Integer.valueOf(row[0] + ""); 
				PI_MENSAJE = row[1].toString(); 
				if (PI_CUENTA != 0) { 
					System.out.println(PI_MENSAJE);
					auditoria.Rechazar(PI_MENSAJE);
					break;
				}else {
					auditoria.objeto = PI_MENSAJE;
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_Validar(UsuarioLogin modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		StoredProcedureQuery query;
		try {
			String PI_CODIGO_USUARIO = modelo.getCod_usuario();
			String PI_CLAVE = modelo.getClave_usuario();

			query = entityManager.createStoredProcedureQuery(paquete_seg_pck_login + ".USP_USUARIO_VALIDAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_CODIGO_USUARIO).setParameter(2, PI_CLAVE);

			try {
				List<Object[]> result = query.getResultList();
				query.unwrap(ProcedureOutputs.class).release();
				int PI_CUENTA = 0;
				String PI_MENSAJE = "";
				for (int i = 0; i < result.size(); i++) {
					Object[] row = result.get(i);
					PI_CUENTA = Integer.valueOf(row[0] + "");
					PI_MENSAJE = String.valueOf(row[1]);
					if (PI_CUENTA != 0) {
						UsuarioPersona retorno = new UsuarioPersona();

						retorno.setId_usuario(Long.valueOf(row[2] + ""));
						retorno.setCod_usuario(String.valueOf(row[3] + ""));
						retorno.setId_oficina(Integer.valueOf(row[4] + ""));
						retorno.setDesc_oficina(String.valueOf(row[5]));
						retorno.setNombre_usuario(String.valueOf(row[6]));
						retorno.setId_tipo_usuario(Long.valueOf(row[7] + ""));
						retorno.setId_persona(String.valueOf(row[8] + ""));
						retorno.setId_tipo_persona(String.valueOf(row[9] + ""));
				
						//(id_tipo_usuario); Id_persona(String.valueOf(row[9] + ""));
						//TIPO_PERSONA
						auditoria.objeto = retorno;
						break;
					} else {
						System.out.println(PI_MENSAJE);
						auditoria.Rechazar(PI_MENSAJE);
						break;
					}
				}
			} catch (Exception ex) {
				auditoria.Error(ex);
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_RecuperarClave(RecuperarClave modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CORREO = modelo.getUCorreo();
			String PI_CODIGO = modelo.getUCodigo();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_perfil + ".USP_USUARIO_OLVIDO_CLAVE")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Class.class, ParameterMode.REF_CURSOR).setParameter(1, 2)
					.setParameter(2, PI_CORREO).setParameter(3, PI_CODIGO);

			// query.execute();
			List<Object[]> result = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_ID_PERSONA = 0;
			String PI_MENSAJE = "";
			String PI_NOMBRE = "";
			for (int i = 0; i < result.size(); i++) {
				Object[] row = result.get(i);
				PI_ID_PERSONA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
				PI_NOMBRE = String.valueOf(row[2]);
			}
			auditoria.objeto = PI_NOMBRE;
			if (PI_ID_PERSONA == 0) {
				auditoria.rechazar = true;
				auditoria.mensaje_salida = PI_MENSAJE;
			}
			
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_VerificaCodigo(RecuperarClave modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String PI_CORREO = modelo.getUCorreo();
			String PI_CODIGO = modelo.getUCodigo();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_perfil + ".USP_USUARIO_VERIFICA")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Class.class, ParameterMode.REF_CURSOR).setParameter(1, 2)
					.setParameter(2, PI_CORREO).setParameter(3, PI_CODIGO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_VALIDO = 0;
			String PI_MENSAJE = "";
			String PI_COD_USUARIO = "";
			String PI_CLAVE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_VALIDO = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
				PI_COD_USUARIO = String.valueOf(row[2]);
				PI_CLAVE = String.valueOf(row[3]);
			}

			if (PI_VALIDO == 0) {
				auditoria.Rechazar(PI_MENSAJE);
			} else {
				UsuarioLogin usu = new UsuarioLogin();
				usu.setCod_usuario(PI_COD_USUARIO);
				usu.setClave_usuario(PI_CLAVE);
				auditoria = Usuario_Validar(usu);
				if (!auditoria.ejecucion_procedimiento) {
					System.out.println(auditoria.mensaje_salida);
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_ActualizaClave(UsuarioLogin modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			long PI_ID_USUARIO = modelo.getId_usuario();
			String PI_CLAVE = modelo.getClave_usuario();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_perfil + ".USP_USUARIO_PASSWORD")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, PI_CLAVE);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_ID_USUARIO_EXTERNO = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_ID_USUARIO_EXTERNO = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
			}

			if (PI_ID_USUARIO_EXTERNO == 0) {
				auditoria.ejecucion_procedimiento = false;
				auditoria.mensaje_salida = PI_MENSAJE;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_VerificaModificaClave(UsuarioOlvidoClave modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_USUARIO = modelo.getId_usuario();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_mef_perfil + ".USP_USUARIO_CAMBIO_CLAVE")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_FLG_MODIFICA_CLAVE = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_FLG_MODIFICA_CLAVE = Integer.valueOf(row[0] + "");
				PI_MENSAJE = row[0].toString();
			}
			auditoria.objeto = PI_FLG_MODIFICA_CLAVE;
			/*
			 * if (PI_ID_USUARIO_EXTERNO == 1) { auditoria.ejecucion_procedimiento = false;
			 * auditoria.mensaje_salida = PI_MENSAJE; }
			 */
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_Modulos(int PI_ID_USUARIO) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		List<Modulos> modulos = new ArrayList<Modulos>();
		try {

			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(pck_seg_menu + ".USP_MENU_USUARIO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, idsistema);

			query.execute();
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				Modulos modulo = new Modulos();
				modulo.setId_sistema_modulo(Integer.valueOf(row[0] + ""));
				modulo.setId_sistema_modulo_padre(Integer.valueOf(row[1] + ""));
				modulo.setId_tipo_modulo(Integer.valueOf(row[2] + ""));
				modulo.setId_a(row[3].toString());
				modulo.setId_li(row[4].toString());
				modulo.setDesc_modulo(row[5].toString());
				modulo.setImagen(row[6].toString());
				modulo.setOrden(Integer.valueOf(row[7] + ""));
				
				modulo.setFlg_alerta(Integer.valueOf(row[8] + ""));
				modulo.setCount_alera(Integer.valueOf(row[9] + ""));
				
				modulos.add(modulo);
			}
			auditoria.objeto = modulos;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_Estado(UsuarioEstado modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			long PI_ID_USUARIO = modelo.getId_usuario();
			String PI_FLG_ESTADO = modelo.getFlg_estado();
			String PI_USU_MODIFICACION = modelo.getUsu_modificacion();
			String PI_IP_MODIFICACION = modelo.getIp_modificacion();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_seg_pck_usuario + ".USP_USUARIO_ESTADO")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, PI_FLG_ESTADO).setParameter(3, PI_USU_MODIFICACION)
					.setParameter(4, PI_IP_MODIFICACION);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = row[1].toString();
			}
			if (PI_CUENTA != 0) {
				auditoria.Rechazar(PI_MENSAJE);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_Eliminar(UsuarioEstado modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			long PI_ID_USUARIO = modelo.getId_usuario();
			String PI_USU_MODIFICACION = modelo.getUsu_modificacion();
			String PI_IP_MODIFICACION = modelo.getIp_modificacion();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_seg_pck_usuario + ".USP_USUARIO_ELIMINAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, PI_USU_MODIFICACION)
					.setParameter(3, PI_IP_MODIFICACION);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release(); 
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				System.out.println(Integer.valueOf(row[0] + ""));
				System.out.println(row[1].toString());
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = row[1].toString();
			}
			if (PI_CUENTA == 0) {
				System.out.println("Entro 12");
				auditoria.Rechazar(PI_MENSAJE);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Usuario_Activar(UsuarioActivacion modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			long PI_ID_USUARIO = Long.valueOf(modelo.getId_usuario());
			String PI_FECHA_ACTIVACION = modelo.getFec_activacion();
			String PI_FECHA_DESACTIVACION = modelo.getFec_desactivacion();
			String PI_USU_MODIFICACION = modelo.getUsu_modificacion();
			String PI_IP_MODIFICACION = modelo.getIp_modificacion();
			System.out.println(PI_FECHA_ACTIVACION);
			System.out.println(PI_FECHA_DESACTIVACION);
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_seg_pck_usuario + ".USP_USUARIO_ACTIVACION")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, PI_FECHA_ACTIVACION)
					.setParameter(3, PI_FECHA_DESACTIVACION).setParameter(4, PI_USU_MODIFICACION)
					.setParameter(5, PI_IP_MODIFICACION);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = row[1].toString();
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
	public Auditoria Usuario_Noti_Count(int PI_ID_USUARIO) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			//Long PI_ID_USUARIO = modelo.getId_usuario();
		
		    List<Modulos> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_menu + ".USP_NOTIFICA_COUNT_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO); 
			
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			
			System.out.println("Count lista"+ TableST.size()); 
			
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				//System.out.println(i);
				Modulos entidad = new Modulos();

				entidad.setId_sistema_modulo(Integer.valueOf(row[0] + ""));
				entidad.setCount_alera(Integer.valueOf(row[1] + ""));
	
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
