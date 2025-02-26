package mef.application.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.procedure.ProcedureOutputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import mef.application.dao.PersonaDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.PersonaCorreo;
import mef.application.modelo.PersonaJuridica;
import mef.application.modelo.PersonaNatural;
import mef.application.modelo.PersonaUsuario;
import mef.application.modelo.PersonaValida;
import mef.application.modelo.UsuarioPersona;
import mef.application.modelo.UsuarioPersonaGrilla;

@Repository

public class PersonaDaoImpl implements PersonaDao {

	private static final Logger logger = LoggerFactory.getLogger(PersonaDaoImpl.class);


	@PersistenceContext
	EntityManager entityManager;
	
	@Value("${database.package}")
	private String databasePackage;

	@Value("${database.pck_seg_persona}")
	private String pck_seg_persona;

	@Value("${database.seg_pck_usuario}")
	private String seg_pck_usuario;

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaNatural_Insertar(PersonaNatural modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			System.out.println("Entro 2 d");
			String PI_ID_TIPO_PERSONA = modelo.getTipopersona();
			Integer PI_ID_TIPO_DOCUMENTO = modelo.getTipodoc();
			String PI_DNI = modelo.getNrodocumento();
			String PI_NOMBRES = modelo.getNombres();
			String PI_APELLIDO_MATERNO = modelo.getApellidomaterno();
			String PI_APELLIDO_PATERNO = modelo.getApellidopaterno();
			String PI_RUC = "";
			String PI_RAZON_SOCIAL = "";
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();
			String PI_DIRECCION = modelo.getDireccion();
			String PI_ID_DEPARTAMENTO = modelo.getIddepartamento();
			String PI_ID_PROVINCIA = modelo.getIdprovincia();
			String PI_ID_DISTRITO = modelo.getIddistrito();
			String PI_FECHA_NACIMIENTO = modelo.getFecnacimiento(); // "29/07/2020";//
																	// dateFormat.format(modelo.getUNacimiento());
			String PI_CODIGO_VERIFICA_DNI = modelo.getCodigoverificadni();
			String PI_CODIGO_UBIGEO_DNI = modelo.getCodigoubigeo();
			String PI_CODIGO_DNI = modelo.getCodigoarchivo();
			String PI_FLG_TERMINO = modelo.getTerminos() == "true" ? "1" : "0";
			String PI_FLG_DECLARACION = modelo.getDeclaracion() == "true" ? "1" : "0";
			String PI_IP_CREACION = modelo.getIp_creacion();

			System.out.println("Entro 3");
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_INSERTAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
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
					.registerStoredProcedureParameter(14, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(15, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(16, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(17, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(18, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(19, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(20, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(21, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(22, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(23, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_TIPO_PERSONA).setParameter(2, PI_ID_TIPO_DOCUMENTO).setParameter(3, PI_DNI)
					.setParameter(4, PI_NOMBRES).setParameter(5, PI_APELLIDO_MATERNO)
					.setParameter(6, PI_APELLIDO_PATERNO).setParameter(7, PI_RUC).setParameter(8, PI_RAZON_SOCIAL)
					.setParameter(9, PI_CELULAR).setParameter(10, PI_TELEFONO).setParameter(11, PI_CORREO)
					.setParameter(12, PI_DIRECCION).setParameter(13, PI_ID_DEPARTAMENTO)
					.setParameter(14, PI_ID_PROVINCIA).setParameter(15, PI_ID_DISTRITO)
					.setParameter(16, PI_FECHA_NACIMIENTO).setParameter(17, PI_CODIGO_VERIFICA_DNI)
					.setParameter(18, PI_CODIGO_UBIGEO_DNI).setParameter(19, PI_CODIGO_DNI)
					.setParameter(20, PI_FLG_TERMINO).setParameter(21, PI_FLG_DECLARACION)
					.setParameter(22, PI_IP_CREACION);

			// query.execute();
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
				auditoria.Rechazar(PI_MENSAJE);
				// auditoria.ejecucion_procedimiento = false;
				// auditoria.mensaje_salida = PI_MENSAJE;
			}else {
				auditoria.objeto = PI_ID_USUARIO_EXTERNO;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria PersonaJuridica_Insertar(PersonaJuridica modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			
		
			
			String PI_ID_TIPO_PERSONA = modelo.getTipopersona();
			Integer PI_ID_TIPO_DOCUMENTO = 0;	
			String PI_DNI = "";
			String PI_NOMBRES = "";
			String PI_APELLIDO_MATERNO = "";
			String PI_APELLIDO_PATERNO = "";
			String PI_RUC = modelo.getRuc().toUpperCase();
			String PI_RAZON_SOCIAL = modelo.getRazon_social();
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();
			String PI_DIRECCION = modelo.getDireccion().toUpperCase();
			String PI_ID_DEPARTAMENTO = modelo.getIddepartamento();
			String PI_ID_PROVINCIA = modelo.getIdprovincia();
			String PI_ID_DISTRITO = modelo.getIddistrito();
			String PI_FECHA_NACIMIENTO = "29/07/2020";// dateFormat.format(modelo.getUNacimiento());
			String PI_CODIGO_VERIFICA_DNI = "";
			String PI_CODIGO_UBIGEO_DNI = "";
			String PI_CODIGO_DNI = modelo.getCodigoarchivo();
			String PI_FLG_TERMINO = modelo.getTerminos() == "true" ? "1" : "0";
			String PI_FLG_DECLARACION = modelo.getDeclaracion() == "true" ? "1" : "0";
			String PI_IP_CREACION = modelo.getIp_creacion();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_INSERTAR")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
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
					.registerStoredProcedureParameter(14, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(15, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(16, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(17, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(18, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(19, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(20, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(21, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(22, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(23, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_TIPO_PERSONA).setParameter(2, PI_ID_TIPO_DOCUMENTO).setParameter(3, PI_DNI)
					.setParameter(4, PI_NOMBRES).setParameter(5, PI_APELLIDO_MATERNO)
					.setParameter(6, PI_APELLIDO_PATERNO).setParameter(7, PI_RUC).setParameter(8, PI_RAZON_SOCIAL)
					.setParameter(9, PI_CELULAR).setParameter(10, PI_TELEFONO).setParameter(11, PI_CORREO)
					.setParameter(12, PI_DIRECCION).setParameter(13, PI_ID_DEPARTAMENTO)
					.setParameter(14, PI_ID_PROVINCIA).setParameter(15, PI_ID_DISTRITO)
					.setParameter(16, PI_FECHA_NACIMIENTO).setParameter(17, PI_CODIGO_VERIFICA_DNI)
					.setParameter(18, PI_CODIGO_UBIGEO_DNI).setParameter(19, PI_CODIGO_DNI)
					.setParameter(20, PI_FLG_TERMINO).setParameter(21, PI_FLG_DECLARACION)
					.setParameter(22, PI_IP_CREACION);

			// query.execute();
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_ID_USUARIO_EXTERNO = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_ID_USUARIO_EXTERNO = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
			}

			if (PI_ID_USUARIO_EXTERNO != 0 && PI_ID_TIPO_PERSONA.equals("02")) {
	
				StoredProcedureQuery queryR = entityManager
						.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_REPRESENTANTE")
						.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
						.setParameter(1, PI_ID_USUARIO_EXTERNO) // PI_ID_USUARIO_EXTERNO
						.setParameter(2, modelo.getRep_legal_id_tipo_documento())
						.setParameter(3, modelo.getRep_legal_dni()) // PI_DNI
						.setParameter(4, modelo.getRep_legal_nombres()) // PI_NOMBRES
						.setParameter(5, modelo.getRep_legal_ape_mat()) // PI_APELLIDO_MATERNO
						.setParameter(6, modelo.getRep_legal_ape_pat()) // PI_APELLIDO_PATERNO
						.setParameter(7, modelo.getRep_legal_celular()) // PI_CELULAR
						.setParameter(8, modelo.getRep_legal_correo()) // PI_CORREO
						.setParameter(9, modelo.getRep_legal_direccion()); // PI_DIRECCION
				queryR.execute();
				query.unwrap(ProcedureOutputs.class).release();

				if (modelo.getDelegado_nombres() != "") {
					StoredProcedureQuery queryD = query = entityManager
							.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_DELEGADO")
							.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
							.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
							.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
							.registerStoredProcedureParameter(10, Integer.class, ParameterMode.IN)
							.setParameter(1, PI_ID_USUARIO_EXTERNO) // PI_ID_USUARIO_EXTERNO
							.setParameter(2, modelo.getDelegado_id_tipo_documento())
							.setParameter(3, modelo.getDelegado_dni()) // PI_DNI		
							.setParameter(4, modelo.getDelegado_nombres()) // PI_NOMBRES
							.setParameter(5, modelo.getDelegado_ape_mat()) // PI_APELLIDO_MATERNO
							.setParameter(6, modelo.getDelegado_ape_pat()) // PI_APELLIDO_PATERNO
							.setParameter(7, modelo.getDelegado_celular()) // PI_CELULAR
							.setParameter(8, modelo.getDelegado_correo()) // PI_CORREO
							.setParameter(9, modelo.getDelegado_direccion()) // PI_DIRECCION
							.setParameter(10, 1); // PI_FLG_NOTIFICAR
					queryD.execute();
					query.unwrap(ProcedureOutputs.class).release();
				}
			}
			if (PI_ID_USUARIO_EXTERNO == 0) {
				auditoria.Rechazar(PI_MENSAJE);
				// auditoria.ejecucion_procedimiento = false;
				// auditoria.mensaje_salida = PI_MENSAJE;
			}else {
				auditoria.objeto = PI_ID_USUARIO_EXTERNO;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonasSolicitudes_Listar(UsuarioPersonaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_TIPO_USUARIO = Integer.valueOf(modelo.getId_tipo_usuario());
			String PI_NOM_PERSONA = modelo.getNom_persona();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();  
			int PI_ID_TIPO_DOCUMENTO = Integer.valueOf(modelo.getId_tipo_documento()); 
			String PI_FEC_INICIO = modelo.getFec_inicio();  
			String PI_FEC_FIN = modelo.getFec_fin();  
		
			
			String PI_DIRECCION = modelo.getDireccion();
			String PI_FLG_ESTADO = modelo.getFlg_estado();
			String PI_FLG_VALIDO = modelo.getFlg_valido();
			System.out.println(PI_ID_TIPO_USUARIO);
			System.out.println(PI_NOM_PERSONA);
			System.out.println(PI_NRO_DOCUMENTO);
			System.out.println(PI_CELULAR);
			System.out.println(PI_TELEFONO);
			System.out.println(PI_CORREO);
			System.out.println(PI_DIRECCION);
			System.out.println(PI_FLG_ESTADO);
			System.out.println(PI_FLG_VALIDO);
			
	
			
			List<UsuarioPersonaGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					
					.registerStoredProcedureParameter(11, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(14, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(15, Class.class, ParameterMode.REF_CURSOR)
				
					.setParameter(1, PI_ID_TIPO_USUARIO)
					.setParameter(2, PI_NOM_PERSONA)
					.setParameter(3, PI_NRO_DOCUMENTO)
					.setParameter(4, PI_CELULAR)
					.setParameter(5, PI_TELEFONO)
					.setParameter(6, PI_CORREO)
					.setParameter(7, PI_DIRECCION)
					.setParameter(8, PI_ID_TIPO_DOCUMENTO)
					.setParameter(9, PI_FEC_INICIO)
					.setParameter(10,PI_FEC_FIN)
					
			
					.setParameter(11, PI_FLG_ESTADO)
					.setParameter(12, PI_FLG_VALIDO)
					.setParameter(13, modelo.getNumpag())
					.setParameter(14, modelo.getNumreg());
			
		
			
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);

				UsuarioPersonaGrilla entidad = new UsuarioPersonaGrilla();

				entidad.setId_persona(Integer.valueOf(row[0] + ""));
				entidad.setId_tipo_persona(Integer.valueOf(row[1] + ""));
				entidad.setDesc_tipo_persona(String.valueOf(row[2] + ""));
				entidad.setDesc_tipo_documento(String.valueOf(row[3] + ""));
				entidad.setNro_documento(String.valueOf(row[4] + ""));
				entidad.setNom_persona(String.valueOf(row[5]));
				entidad.setCelular(String.valueOf(row[6] + ""));
				entidad.setTelefono(String.valueOf(row[7] + ""));
				entidad.setCorreo(String.valueOf(row[8]));
				entidad.setDireccion(String.valueOf(row[9]));
				entidad.setFlg_valido(String.valueOf(row[13]));

				entidad.setUsu_creacion(String.valueOf(row[14]));
				entidad.setFec_creacion(String.valueOf(row[15]));
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				//SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
				entidad.setDfec_creacion(format.parse(entidad.getFec_creacion()));
				
				entidad.setIp_creacion("-");
				entidad.setTotalreg(Integer.valueOf(row[19] + ""));
				entidad.setNumreg(Integer.valueOf(row[20] + ""));
				

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
	public Auditoria PersonaUsuario_Listar(UsuarioPersonaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_TIPO_USUARIO = Integer.valueOf(modelo.getId_tipo_usuario());
			String PI_FLG_ESTADO = modelo.getFlg_estado();
			String PI_NOM_PERSONA = modelo.getNom_persona();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();
			int PI_ID_TIPO_DOCUMENTO = Integer.valueOf(modelo.getId_tipo_documento()); 
			String PI_FEC_INICIO = modelo.getFec_inicio();  
			String PI_FEC_FIN = modelo.getFec_fin();  

			List<UsuarioPersonaGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_USUARIO_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
					
					.registerStoredProcedureParameter(11, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(12, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(13, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_TIPO_USUARIO)
					.setParameter(2, PI_FLG_ESTADO)
					.setParameter(3, PI_NOM_PERSONA)
					.setParameter(4, PI_NRO_DOCUMENTO)
					.setParameter(5, PI_CELULAR).setParameter(6, PI_TELEFONO)
					.setParameter(7, PI_CORREO)
					.setParameter(8, PI_ID_TIPO_DOCUMENTO)
					.setParameter(9, PI_FEC_INICIO)
					.setParameter(10,PI_FEC_FIN)
					
					.setParameter(11, modelo.getNumpag())
					.setParameter(12, modelo.getNumreg());

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			System.out.println(" Usuarios :  PersonaUsuario_Listar Tama:" + TableST.size());
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				UsuarioPersonaGrilla entidad = new UsuarioPersonaGrilla();
				entidad.setId_usuario(Long.valueOf(row[0] + ""));
				entidad.setCod_usuario(String.valueOf(row[1] + ""));
				entidad.setId_persona(Long.valueOf(row[2] + ""));
				entidad.setDesc_tipo_usuario(String.valueOf(row[3] + ""));
				entidad.setDesc_tipo_persona(String.valueOf(row[4] + ""));
				entidad.setDesc_oficina(String.valueOf(row[5] + ""));
				entidad.setNom_persona(String.valueOf(row[6] + ""));
				entidad.setNro_documento(String.valueOf(row[7] + ""));
				entidad.setCelular(String.valueOf(row[8] + ""));
				entidad.setTelefono(String.valueOf(row[9] + ""));
				entidad.setCorreo(String.valueOf(row[10] + ""));
				entidad.setId_tipo_persona(Integer.valueOf(row[11] + ""));
				
				entidad.setFec_activacion(String.valueOf(row[12] + ""));
				entidad.setFec_desactivacion(String.valueOf(row[13] + ""));
				
				System.out.println("fecha activ y desac" + row[12] + " ," + row[12]); 
					

				entidad.setFlg_estado(String.valueOf(row[14] + ""));
				entidad.setUsu_creacion(String.valueOf(row[15] + ""));
				entidad.setFec_creacion(String.valueOf(row[16] + ""));
				
				System.out.println("fecha creacion" + row[16] ); 
				
				entidad.setIp_creacion("-");
				entidad.setUsu_modificacion(String.valueOf(row[18] + ""));
				entidad.setFec_modificacion(String.valueOf(row[19] + ""));
				entidad.setIp_modificacion("-");
				
				entidad.setRepresentante(String.valueOf(row[23] + ""));
				entidad.setDelegado(String.valueOf(row[24] + ""));

				entidad.setDepartamento(String.valueOf(row[25] + ""));
				entidad.setProvincia(String.valueOf(row[26] + ""));
				entidad.setDistrito(String.valueOf(row[27] + ""));

				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
				entidad.setDfec_creacion(format.parse(entidad.getFec_creacion()));
				
                if(!entidad.getFec_activacion().equals("-"))
				entidad.setDfec_activacion(format2.parse(entidad.getFec_activacion()));
				
                if(!entidad.getFec_desactivacion().equals("-"))
				entidad.setDfec_desactivacion(format2.parse(entidad.getFec_desactivacion()));
		
				entidad.setTotalreg(Integer.valueOf(row[28] + ""));
				entidad.setNumreg(Integer.valueOf(row[29] + ""));
 

				lista.add(entidad);
			}
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("PersonaServiceImpl - PersonaUsuario_Listar:" + auditoria.error_log);
		}
		return auditoria;
	}
	
	@Override
	public Auditoria PersonaUsuario_ListarGeneral(UsuarioPersonaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_TIPO_USUARIO = Integer.valueOf(modelo.getId_tipo_usuario());
			String PI_FLG_ESTADO = modelo.getFlg_estado();
			String PI_NOM_PERSONA = modelo.getNom_persona();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();
			int PI_ID_TIPO_DOCUMENTO = Integer.valueOf(modelo.getId_tipo_documento()); 
			String PI_FEC_INICIO = modelo.getFec_inicio();  
			String PI_FEC_FIN = modelo.getFec_fin();  

			List<UsuarioPersonaGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(databasePackage + ".P_PERSONA_USUARIO_LISTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
				
					.registerStoredProcedureParameter(11, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_TIPO_USUARIO)
					.setParameter(2, PI_FLG_ESTADO)
					.setParameter(3, PI_NOM_PERSONA)
					.setParameter(4, PI_NRO_DOCUMENTO)
					.setParameter(5, PI_CELULAR).setParameter(6, PI_TELEFONO)
					.setParameter(7, PI_CORREO)
					.setParameter(8, PI_ID_TIPO_DOCUMENTO)
					.setParameter(9, PI_FEC_INICIO)
					.setParameter(10,PI_FEC_FIN); 
					

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			System.out.println(" Usuarios :  PersonaUsuario_Listar Tama:" + TableST.size());
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				UsuarioPersonaGrilla entidad = new UsuarioPersonaGrilla();
				entidad.setId_usuario(Long.valueOf(row[0] + ""));
				entidad.setCod_usuario(String.valueOf(row[1] + ""));
				entidad.setId_persona(Long.valueOf(row[2] + ""));
				entidad.setDesc_tipo_usuario(String.valueOf(row[3] + ""));
				entidad.setDesc_tipo_persona(String.valueOf(row[4] + ""));
				entidad.setDesc_oficina(String.valueOf(row[5] + ""));
				entidad.setNom_persona(String.valueOf(row[6] + ""));
				entidad.setNro_documento(String.valueOf(row[7] + ""));
				entidad.setCelular(String.valueOf(row[8] + ""));
				entidad.setTelefono(String.valueOf(row[9] + ""));
				entidad.setCorreo(String.valueOf(row[10] + ""));
				entidad.setId_tipo_persona(Integer.valueOf(row[11] + ""));
				
				entidad.setFec_activacion(String.valueOf(row[12] + ""));
				entidad.setFec_desactivacion(String.valueOf(row[13] + ""));
				
				System.out.println("fecha activ y desac" + row[12] + " ," + row[12]); 
					

				entidad.setFlg_estado(String.valueOf(row[14] + ""));
				entidad.setUsu_creacion(String.valueOf(row[15] + ""));
				entidad.setFec_creacion(String.valueOf(row[16] + ""));
				
				System.out.println("fecha creacion" + row[16] ); 
				
				entidad.setIp_creacion("-");
				entidad.setUsu_modificacion(String.valueOf(row[18] + ""));
				entidad.setFec_modificacion(String.valueOf(row[19] + ""));
				entidad.setIp_modificacion("-");
				
				entidad.setRepresentante(String.valueOf(row[23] + ""));
				entidad.setDelegado(String.valueOf(row[24] + ""));


				entidad.setDepartamento(String.valueOf(row[25] + ""));
				entidad.setProvincia(String.valueOf(row[26] + ""));
				entidad.setDistrito(String.valueOf(row[27] + ""));
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
				entidad.setDfec_creacion(format.parse(entidad.getFec_creacion()));
				
                if(!entidad.getFec_activacion().equals("-"))
				entidad.setDfec_activacion(format2.parse(entidad.getFec_activacion()));
				
                if(!entidad.getFec_desactivacion().equals("-"))
				entidad.setDfec_desactivacion(format2.parse(entidad.getFec_desactivacion()));
		
				entidad.setTotalreg(Integer.valueOf(row[28] + ""));
				entidad.setNumreg(Integer.valueOf(row[29] + ""));

				lista.add(entidad);
			}
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("PersonaServiceImpl - PersonaUsuario_Listar:" + auditoria.error_log);
		}
		return auditoria;
	}
	
	

	@Override
	public Auditoria PersonaNatural_ListarUno(PersonaNatural modelo, int flg_valido) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		PersonaNatural entidad = new PersonaNatural();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONANATURAL_LISTAR_UNO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, modelo.getIdpersona()).setParameter(2, flg_valido);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				entidad = new PersonaNatural();

				entidad.setIdpersona(Integer.valueOf(row[0] + ""));
				entidad.setTipopersona(String.valueOf(row[1] + ""));
				entidad.setDesctipopersona(String.valueOf(row[2] + ""));
				entidad.setDesctipodoc(String.valueOf(row[3] + ""));
				entidad.setNrodocumento(String.valueOf(row[4] + ""));
				entidad.setNombres(String.valueOf(row[5]));
				entidad.setApellidopaterno(String.valueOf(row[6]));
				entidad.setApellidomaterno(String.valueOf(row[7]));
				entidad.setCelular(String.valueOf(row[8] + ""));
				entidad.setTelefono(String.valueOf(row[9] + ""));
				entidad.setCorreo(String.valueOf(row[10]));
				entidad.setDireccion(String.valueOf(row[11]));

				entidad.setDepartamento(String.valueOf(row[12]));
				entidad.setProvincia(String.valueOf(row[13]));
				entidad.setDistrito(String.valueOf(row[14]));

				entidad.setFecnacimiento(String.valueOf(row[15]));
				entidad.setCodigoverificadni(String.valueOf(row[16]));
				entidad.setCodigoubigeo(String.valueOf(row[17]));
				entidad.setCodigoarchivo(String.valueOf(row[18]));

				entidad.setTerminos(String.valueOf(row[19]));
				entidad.setDeclaracion(String.valueOf(row[20]));

				entidad.setUsu_creacion(String.valueOf(row[23]));
				entidad.setFec_creacion(String.valueOf(row[24]));
				entidad.setIp_creacion(String.valueOf(row[25]));

				entidad.setObservacion(Objects.toString(row[26], ""));  

				entidad.setIddepartamento(String.valueOf(row[27]));
				entidad.setIdprovincia(String.valueOf(row[28]));
				entidad.setIddistrito(String.valueOf(row[29]));			
				entidad.setTipodoc(Integer.valueOf(row[30]+""));	
				
				auditoria.objeto = entidad;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_ListarUno : " + auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria PersonaJuridica_ListarUno(int idpersona, int flg_valido) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		PersonaJuridica entidad = new PersonaJuridica();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONAJURIDICA_LISTAR_UNO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, idpersona).setParameter(2, flg_valido);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				entidad = new PersonaJuridica();

				entidad.setIdpersona(Integer.valueOf(row[0] + ""));
				entidad.setTipopersona(String.valueOf(row[1] + ""));
				entidad.setDesctipopersona(String.valueOf(row[2] + ""));
				entidad.setDesctipodoc(String.valueOf(row[3] + ""));

				entidad.setRuc(String.valueOf(row[4] + ""));
				entidad.setRazon_social(String.valueOf(row[5]));
				entidad.setCelular(String.valueOf(row[6] + ""));
				entidad.setTelefono(String.valueOf(row[7] + ""));
				entidad.setCorreo(String.valueOf(row[8]));
				entidad.setDireccion(String.valueOf(row[9]));

				entidad.setDepartamento(String.valueOf(row[10]));
				entidad.setProvincia(String.valueOf(row[11]));
				entidad.setDistrito(String.valueOf(row[12]));

				entidad.setCodigoarchivo(String.valueOf(row[13]));

				entidad.setTerminos(String.valueOf(row[14]));
				entidad.setDeclaracion(String.valueOf(row[15]));
				entidad.setFec_creacion(String.valueOf(row[18]));
				entidad.setRep_legal_dni(String.valueOf(row[19]));
				entidad.setRep_legal_nombres(String.valueOf(row[20]));
				entidad.setRep_legal_ape_pat(String.valueOf(row[21]));
				entidad.setRep_legal_ape_mat(String.valueOf(row[22]));
				entidad.setRep_legal_correo(String.valueOf(row[23]));
				entidad.setRep_legal_celular(String.valueOf(row[24]));
				entidad.setRep_legal_direccion(String.valueOf(row[25]));

				entidad.setDelegado_dni(String.valueOf(row[26]));
				entidad.setDelegado_nombres(String.valueOf(row[27]));
				entidad.setDelegado_ape_pat(String.valueOf(row[28]));
				entidad.setDelegado_ape_mat(String.valueOf(row[29]));
				entidad.setDelegado_correo(String.valueOf(row[30]));
				entidad.setDelegado_celular(String.valueOf(row[31]));
				entidad.setDelegado_direccion(String.valueOf(row[32]));
				entidad.setObservacion(Objects.toString(row[33], "")); 

				entidad.setIddepartamento(String.valueOf(row[34]));
				entidad.setIdprovincia(String.valueOf(row[35]));
				entidad.setIddistrito(String.valueOf(row[36]));
				
				entidad.setRep_legal_id_tipo_documento(Integer.valueOf(row[37] + ""));
				entidad.setDelegado_id_tipo_documento(Integer.valueOf(row[38] + ""));
				
				
				auditoria.objeto = entidad;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_ListarUno : " + auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria PersonaInterno_ListarUno(long idpersona) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		PersonaNatural entidad = new PersonaNatural();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONAINTERNO_LISTAR_UNO")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, idpersona);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				entidad = new PersonaNatural();

				entidad.setIdpersona(Integer.valueOf(row[0] + ""));
				entidad.setDesctipopersona(String.valueOf(row[1] + ""));
				entidad.setDesctipodoc(String.valueOf(row[2] + ""));
				entidad.setNrodocumento(String.valueOf(row[3] + ""));
				entidad.setNombres(String.valueOf(row[4]));
				entidad.setApellidopaterno(String.valueOf(row[5]));
				entidad.setApellidomaterno(String.valueOf(row[6]));
				entidad.setCelular(String.valueOf(row[7] + ""));
				entidad.setTelefono(String.valueOf(row[8] + ""));
				entidad.setCorreo(String.valueOf(row[9]));
				entidad.setDireccion(String.valueOf(row[10]));

				entidad.setTipopersona(String.valueOf(row[11]));
				entidad.setUsu_creacion(String.valueOf(row[12]));
				entidad.setFec_creacion(String.valueOf(row[13]));

				auditoria.objeto = entidad;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_ListarUno : " + auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Persona_Usuario(PersonaUsuario modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		PersonaUsuario entidad = new PersonaUsuario();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_USUARIO")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, modelo.getIdpersona()).setParameter(2, modelo.getFlgtipousuario());

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				entidad = new PersonaUsuario();

				entidad.setIdpersona(Integer.valueOf(row[0] + ""));
				entidad.setDesctipopersona(String.valueOf(row[2] + ""));
				entidad.setDesctipodoc(String.valueOf(row[3] + ""));
				entidad.setNrodocumento(String.valueOf(row[4] + ""));
				entidad.setNombre(String.valueOf(row[5]));
				entidad.setCorreo(String.valueOf(row[8]));
				entidad.setNombreusuario(String.valueOf(row[9]));
				entidad.setClave(String.valueOf(row[10]));
				entidad.setCorreo_copia(String.valueOf(row[11]));

				auditoria.objeto = entidad;
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_ListarUno : " + auditoria.error_log);
		}

		return auditoria;
	}

	@Override
	public Auditoria Persona_Validar(PersonaValida modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Integer PI_ID_PERSONA = modelo.getIdpersona();
			Integer PI_ID_TIPO_USUARIO = modelo.getIdtipousuario();
			Integer PI_FLG_VALIDO = modelo.getFlgvalida();
			String PI_OBSERVACION = modelo.getObservacion();

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_VALIDAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_PERSONA).setParameter(2, PI_ID_TIPO_USUARIO).setParameter(3, PI_FLG_VALIDO)
					.setParameter(4, PI_OBSERVACION);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_CUENTA = 1;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
			}
			if (PI_CUENTA != 0) {
				auditoria.Rechazar(PI_MENSAJE);
			} else {
				auditoria.objeto = PI_MENSAJE;
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_ListarUno : " + auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria UsuarioPersona_Listar(long ID_USUARIO) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			UsuarioPersona entidad = new UsuarioPersona();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(seg_pck_usuario + ".USP_USUARIO_PERSONA_LISTAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, ID_USUARIO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);

				entidad.setId_usuario(Long.valueOf(row[0] + ""));
				entidad.setCod_usuario(String.valueOf(row[1] + ""));
				entidad.setNombre_usuario(String.valueOf(row[2] + ""));
				entidad.setApellido_paterno(String.valueOf(row[3] + ""));
				entidad.setApellido_materno(String.valueOf(row[4] + ""));
				entidad.setNro_documento(String.valueOf(row[5] + ""));
				entidad.setDireccion(String.valueOf(row[6] + ""));
				entidad.setCelular(String.valueOf(row[7] + ""));
				entidad.setTelefono(String.valueOf(row[8] + ""));
				entidad.setCorreo(String.valueOf(row[9] + ""));
				entidad.setDesc_departamento(String.valueOf(row[10] + ""));
				entidad.setDesc_provincia(String.valueOf(row[11] + ""));
				entidad.setDesc_distrito(String.valueOf(row[12] + ""));
				entidad.setCorreo_copia(String.valueOf(row[13] + ""));

			}
			auditoria.objeto = entidad;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Persona_Correo(Long id_persona) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		PersonaCorreo entidad = new PersonaCorreo();
		try {
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_CORREO")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, id_persona);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();

			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				entidad = new PersonaCorreo();

				entidad.setDesctipousuario(String.valueOf(row[0] + ""));
				entidad.setDesctipopersona(String.valueOf(row[1] + ""));
				entidad.setNombrepersona(String.valueOf(row[2] + ""));
				entidad.setDesctipodocumento(String.valueOf(row[3] + ""));
				entidad.setNrodocumento(String.valueOf(row[4]));
				entidad.setCelular(String.valueOf(row[5]));
				entidad.setTelefono(String.valueOf(row[6]));
				entidad.setCorreo(String.valueOf(row[7]));
				entidad.setCorreo_copia(String.valueOf(row[8]));
				entidad.setFec_creacion(String.valueOf(row[9]));

				auditoria.objeto = entidad;
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_ListarUno : " + auditoria.error_log);
		}

		return auditoria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaNatural_Actualizar(PersonaNatural modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Integer PI_ID_PERSONA = modelo.getIdpersona();
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();
			String PI_DIRECCION = modelo.getDireccion();

			String PI_ID_DEPARTAMENTO = modelo.getIddepartamento();
			String PI_ID_PROVINCIA = modelo.getIdprovincia();
			String PI_ID_DISTRITO = modelo.getIddistrito();
			String PI_ID_COD_ARCHIVO = modelo.getCodigoarchivo();

			System.out.println("PI_ID_DEPARTAMENTO : " + PI_ID_DEPARTAMENTO);
			System.out.println("PI_ID_PROVINCIA : " + PI_ID_PROVINCIA);
			System.out.println(PI_DIRECCION);
			System.out.println(PI_ID_COD_ARCHIVO);
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_ACTUALIZAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_PERSONA)
					.setParameter(2, PI_CELULAR)
					.setParameter(3, PI_TELEFONO)
					.setParameter(4, PI_CORREO)
					.setParameter(5, PI_DIRECCION)
					.setParameter(6, PI_ID_DEPARTAMENTO)
					.setParameter(7, PI_ID_PROVINCIA)
					.setParameter(8, PI_ID_DISTRITO)
					.setParameter(9, PI_ID_COD_ARCHIVO);

			// query.execute();
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
				auditoria.Rechazar(PI_MENSAJE);
				// auditoria.ejecucion_procedimiento = false;
				// auditoria.mensaje_salida = PI_MENSAJE;
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaJuridica_Actualizar(PersonaJuridica modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Integer PI_ID_PERSONA = modelo.getIdpersona();
			Integer PI_REP_LEGAL_ID_TIPO_DOCUMENTO = modelo.getRep_legal_id_tipo_documento();
			Integer PI_DELEGADO_ID_TIPO_DOCUMENTO = modelo.getDelegado_id_tipo_documento();
			String PI_CELULAR = modelo.getCelular();
			String PI_TELEFONO = modelo.getTelefono();
			String PI_CORREO = modelo.getCorreo();
			String PI_DIRECCION = modelo.getDireccion();

			String PI_ID_DEPARTAMENTO = modelo.getIddepartamento();
			String PI_ID_PROVINCIA = modelo.getIdprovincia();
			String PI_ID_DISTRITO = modelo.getIddistrito();
			String PI_ID_COD_ARCHIVO = modelo.getCodigoarchivo();

			logger.info("PI_ID_PERSONA:  {}", PI_ID_PERSONA);
			logger.info("PI_CORREO:  {}", PI_CORREO);
			logger.info("PI_REP_LEGAL_ID_TIPO_DOCUMENTO:  {}", PI_REP_LEGAL_ID_TIPO_DOCUMENTO);

			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_ACTUALIZAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_PERSONA).setParameter(2, PI_CELULAR).setParameter(3, PI_TELEFONO)
					.setParameter(4, PI_CORREO).setParameter(5, PI_DIRECCION).setParameter(6, PI_ID_DEPARTAMENTO)
					.setParameter(7, PI_ID_PROVINCIA).setParameter(8, PI_ID_DISTRITO).setParameter(9, PI_ID_COD_ARCHIVO);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			int PI_ID_USUARIO_EXTERNO = 0;
			String PI_MENSAJE = "";

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(TableST);

			logger.info("TableST:  {}", json);
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_ID_USUARIO_EXTERNO = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);
			}


			logger.info("PI_ID_USUARIO_EXTERNO:  {}", PI_ID_USUARIO_EXTERNO);
			if (PI_ID_USUARIO_EXTERNO != 0) {
				auditoria.Rechazar(PI_MENSAJE);
				// auditoria.ejecucion_procedimiento = false;
				// auditoria.mensaje_salida = PI_MENSAJE;
			} else {

				StoredProcedureQuery queryR = entityManager
						.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_R_ACTUALIZAR")
						.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
						.setParameter(1, PI_ID_PERSONA) // PI_ID_USUARIO_EXTERNO
						.setParameter(2, modelo.getRep_legal_dni()) // PI_DNI
						.setParameter(3, modelo.getRep_legal_nombres()) // PI_NOMBRES
						.setParameter(4, modelo.getRep_legal_ape_mat()) // PI_APELLIDO_MATERNO
						.setParameter(5, modelo.getRep_legal_ape_pat()) // PI_APELLIDO_PATERNO
						.setParameter(6, modelo.getRep_legal_celular()) // PI_CELULAR
						.setParameter(7, modelo.getRep_legal_correo()) // PI_CORREO
						.setParameter(8, PI_REP_LEGAL_ID_TIPO_DOCUMENTO) // PI_ID_TIPO_DOCUMENTO
						.setParameter(9, modelo.getRep_legal_direccion()); // PI_DIRECCION
				queryR.execute();
				query.unwrap(ProcedureOutputs.class).release();

				StoredProcedureQuery queryD = query = entityManager
						.createStoredProcedureQuery(pck_seg_persona + ".USP_PERSONA_D_ACTUALIZAR")
						.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
						.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter(10, Integer.class, ParameterMode.IN)
						.setParameter(1, PI_ID_PERSONA) // PI_ID_USUARIO_EXTERNO
						.setParameter(2, modelo.getDelegado_dni()) // PI_DNI
						.setParameter(3, modelo.getDelegado_nombres()) // PI_NOMBRES
						.setParameter(4, modelo.getDelegado_ape_mat()) // PI_APELLIDO_MATERNO
						.setParameter(5, modelo.getDelegado_ape_pat()) // PI_APELLIDO_PATERNO
						.setParameter(6, modelo.getDelegado_celular()) // PI_CELULAR
						.setParameter(7, modelo.getDelegado_correo()) // PI_CORREO
						.setParameter(8, modelo.getDelegado_direccion()) // PI_DIRECCION
						.setParameter(9, PI_DELEGADO_ID_TIPO_DOCUMENTO) // PI_ID_TIPO_DOCUMENTO
						.setParameter(10, 1); // PI_FLG_NOTIFICAR
				queryD.execute();
				query.unwrap(ProcedureOutputs.class).release();

			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
			logger.error("auditoria.error_log:  {}", ex);
		}
		return auditoria;
	}
}
