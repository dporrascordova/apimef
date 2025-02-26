package mef.application.dao.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
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

import mef.application.dao.CasillaDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.CasillaArchivo;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.CasillaUno;

@Repository
public class CasillaDaoImpl implements CasillaDao {

	@PersistenceContext
	EntityManager entityManager;

	@Value("${database.seg_pck_consulta}")
	private String paquete_consulta;

	@Value("${database.package}")
	private String paquete_mantenimiento;

	@Value("${database.pck_ven_mantenimiento}")
	private String pck_mef_mantenimiento;
	
	@Override
	public Auditoria Casilla_Listar(CasillaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			long PI_ID_USUARIO = Long.valueOf(modelo.getId_usuario());
			String PI_ID_DOCUMENTO = modelo.getNro_solicitud();
			String PI_ID_TIPO_DOCUMENTO = modelo.getId_tipo_documento();
			String PI_ID_OFICINA = modelo.getId_oficina();
			String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			String PI_OBSERVACION = modelo.getObservacion();

			String PI_FEC_RECEPCION = modelo.getFec_creacion();
			String PI_ID_ESTADO = modelo.getId_estado_documento(); 
			int PI_FLG_TIPO_NOTI = modelo.getFlg_tipo_noti(); 
			
			System.out.println("-------USP_CASILLA_LISTAR-------");
			System.out.println(PI_ID_USUARIO);
			System.out.println(PI_ID_DOCUMENTO);
			System.out.println(PI_ID_TIPO_DOCUMENTO);
			System.out.println(PI_ID_OFICINA);
			System.out.println(PI_NRO_DOCUMENTO);
			System.out.println(PI_OBSERVACION);
			System.out.println("-------fin USP_CASILLA_LISTAR-------");			
			

			List<CasillaGrilla> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_consulta + ".USP_CASILLA_LISTAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(9, int.class, ParameterMode.IN)
					.registerStoredProcedureParameter(10, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, PI_ID_DOCUMENTO)
					.setParameter(3, PI_ID_TIPO_DOCUMENTO).setParameter(4, PI_ID_OFICINA)
					.setParameter(5, PI_NRO_DOCUMENTO).setParameter(6, PI_OBSERVACION).setParameter(7, PI_FEC_RECEPCION).setParameter(8, PI_ID_ESTADO)
					.setParameter(9, PI_FLG_TIPO_NOTI);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				CasillaGrilla entidad = new CasillaGrilla();

				entidad.setId_casilla(String.valueOf(row[0] + ""));
				entidad.setId_documento(String.valueOf(row[1] + ""));
				entidad.setObservacion(String.valueOf(row[2] + ""));
				entidad.setNro_documento(String.valueOf(row[3] + ""));
				entidad.setHoja_ruta(String.valueOf(row[4] + ""));
				entidad.setAsunto(String.valueOf(row[5] + ""));
				entidad.setDesc_oficina(String.valueOf(row[6] + ""));
				entidad.setDesc_tipo_documento(String.valueOf(row[8] + ""));

				entidad.setUsu_creacion(String.valueOf(row[9] + ""));
				entidad.setFec_creacion(String.valueOf(row[10] + ""));
				entidad.setIp_creacion("-");
				entidad.setFec_modificacion(String.valueOf(row[15] + "")); 
				
				entidad.setDesc_estado_documento(String.valueOf(row[17] + "")); 

				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); 
				entidad.setDfec_creacion(format.parse(entidad.getFec_creacion()));

				lista.add(entidad);
			}
			auditoria.objeto = lista;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	private String readAsAscii(InputStream is) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] buffer = new byte[1024];
        int read;
        try {
          while ((read = is.read(buffer, 0, buffer.length)) > 0) {
            for (int i = 0; i < read; i++) {
              out.append(String.format("%x", buffer[i]));
            }
          }
        } finally {
          is.close();
        }
        return out.toString();
      }
	
	@Override
	public Auditoria Casilla_Listar_Uno(CasillaUno modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			long PI_ID_USUARIO = Long.valueOf(modelo.getId_usuario());
			long PI_ID_CASILLA = Long.valueOf(modelo.getId_casilla());

			List<CasillaArchivo> lista = new ArrayList<>();
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(paquete_consulta + ".USP_CASILLA_LISTAR_UNO")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_USUARIO).setParameter(2, PI_ID_CASILLA);

			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			CasillaUno entidad = new CasillaUno();
			// entidad.setArchivos(
			// entidad.setArchivos( List<CasillaArchivo>());
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);

				if (i == 0) {
					entidad.setId_casilla(String.valueOf(row[0] + ""));
					Clob clobObject = (Clob) (row[2]);

					String theString ="";//readAsAscii(esun.getAsciiStream());
					//Clob clobObject = (Clob)rs.getObject("name");                                       
		            final StringBuilder sb = new StringBuilder();
		            try
		                {
		                    final Reader reader = clobObject.getCharacterStream();
		                    final BufferedReader br = new BufferedReader(reader);
		                    int b;
		                    while(-1 != (b = br.read()))
		                    {
		                        sb.append((char)b);
		                    }
		                    br.close();
		                      theString = sb.toString();}
		            catch (Exception ex)
		            {

		            }
		            
					//StringBuilder buffer = new StringBuilder();
					
					/*InputStream stream = lobHandler.getClobAsAsciiStream(rs, 1);
		            try {
		              return readAsAscii(esun.getAsciiStream());
		            } catch (IOException ex) {
		              // 
		            }*/
		            
					
					//InputStreamReader st = new InputStreamReader(esun.getAsciiStream(),Charset.forName("UTF-8"));
					
					//StringWriter writer = new StringWriter();
					//IOUtils.copy(st, writer);
					//String theString = writer.toString();
					//InputStreamReader st  = new InputStreamReader(esun.getCharacterStream(),Charset.forName("UTF-8"));
					/*Reader r = esun.getCharacterStream();
					int n = 0;
					CharBuffer cb = CharBuffer.allocate((int) esun.length());
					while(r.read(cb) != -1)
						if (n > 0) {
							buffer.append(cb, 0, n);
			            }
						;
					 String sd = buffer.toString();*/
					
					/*Clob esun = (Clob) (row[2]);
					InputStream in = esun.getAsciiStream();
					StringWriter w = new StringWriter();
					IOUtils.copy(in, w);*/
					
					/*StringBuilder buffer = new StringBuilder();
					int intValueOfChar;
		             while ((intValueOfChar = st.read()) != -1) {                       
		                  buffer.append((char) intValueOfChar);                  
		              }*/

		             // wr = new FileWriter("C:/test/file.xml");
		              //wr.write(buffer.toString());    
		              
					 //Writer fstream = new OutputStreamWriter(new FileOutputStream(mergedFile), StandardCharsets.UTF_8);
					//String clobAsString = w.toString();
					entidad.setMensaje_html(theString);
					//entidad.setMensaje_html(clobAsString);
					entidad.setId_casilla(String.valueOf(row[0] + ""));
				
					entidad.setUsu_creacion(String.valueOf(row[3] + ""));
					entidad.setFec_creacion(String.valueOf(row[4] + ""));
					entidad.setIp_creacion("-");
					entidad.setEstado(String.valueOf(row[12] + ""));
					entidad.setId_persona(String.valueOf(row[13] + ""));
					entidad.setId_usuario(String.valueOf(row[14] + ""));
					
				}
				CasillaArchivo archivo = new CasillaArchivo();
				archivo.setNombre_archivo(Objects.toString(row[6], ""));
				if (!archivo.getNombre_archivo().equals("")) {
					archivo.setCodigo_archivo(String.valueOf(row[7] + ""));
					archivo.setExtension_archivo(String.valueOf(row[8] + ""));
					archivo.setUsu_creacion(String.valueOf(row[9] + ""));
					archivo.setFec_creacion(String.valueOf(row[10] + ""));
					
					archivo.setIp_creacion("-");
				
					
					lista.add(archivo);
				}
			}
			entidad.setArchivos(lista);
			auditoria.objeto = entidad;
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Casilla_Insertar(Casilla modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			int PI_ID_USUARIO = modelo.getId_usuario();
			Long PI_ID_DOCUMENTO = modelo.getId_documento();
			String PI_OBSERVACION = modelo.getObservacion();
			String PI_HTML = modelo.getMensaje_html();
			String PI_USU_CREACION = modelo.getUsu_creacion();
			String PI_IP_CREACION = modelo.getIp_creacion();

			String PI_HOJA_ENVIO = modelo.getHoja_envio();
			String PI_HE_NRO_DOCUMENTO = modelo.getNro_doc();
			String PI_HE_TIPO_DOC = modelo.getTipo_doc();
			String PI_HE_HOJA_RUTA = modelo.getHoja_ruta();
			String PI_HE_OFICINA = modelo.getOficina();
			int PI_FLG_NOTIFICACION = modelo.getFlg_notificacion();
			if (PI_HOJA_ENVIO == null  ) {
				PI_HOJA_ENVIO = "-";
			}
			if (PI_HE_NRO_DOCUMENTO == null  ) {
				PI_HE_NRO_DOCUMENTO = "-";
			}
			if (PI_HE_TIPO_DOC == null  ) {
				PI_HE_TIPO_DOC = "-";
			}
			if (PI_HE_HOJA_RUTA == null  ) {
				PI_HE_HOJA_RUTA = "-";
			}
			if (PI_HE_OFICINA == null  ) {
				PI_HE_OFICINA = "-";
			}
			if( PI_FLG_NOTIFICACION == 0) {
				PI_FLG_NOTIFICACION = 1;
			}
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_mef_mantenimiento + ".USP_CASILLA_INSERTAR")
					.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
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
					.registerStoredProcedureParameter(13, Long.class, ParameterMode.OUT)
					.setParameter(1, PI_ID_USUARIO)
					.setParameter(2, PI_ID_DOCUMENTO)
					.setParameter(3, PI_OBSERVACION)
					.setParameter(4, PI_HOJA_ENVIO)
					.setParameter(5, PI_HE_NRO_DOCUMENTO)
					.setParameter(6, PI_HE_TIPO_DOC)
					.setParameter(7, PI_HE_HOJA_RUTA)
					.setParameter(8, PI_HE_OFICINA)
					.setParameter(9, PI_HTML)
					.setParameter(10, PI_USU_CREACION)
					.setParameter(11, PI_IP_CREACION)
					.setParameter(12, PI_FLG_NOTIFICACION);

			query.execute();
			auditoria.objeto = (Long) query.getOutputParameterValue(13);
			query.unwrap(ProcedureOutputs.class).release();

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria CasillaArchivo_Insertar(CasillaArchivo modelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			Long PI_ID_CASILLA = modelo.getId_casilla();
			String PI_NOMBRE_ARCHIVO = modelo.getNombre_archivo();
			String PI_CODIGO_ARCHIVO = modelo.getCodigo_archivo();
			String PI_EXTENSION_ARCHIVO = modelo.getExtension_archivo();
			String PI_USU_CREACION = modelo.getUsu_creacion();
			String PI_IP_CREACION = modelo.getIp_creacion();

			System.out.println("pas 00001 : "  );
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_mef_mantenimiento + ".USP_CASILLA_ARCHIVO_INSERTAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT).setParameter(1, PI_ID_CASILLA)
					.setParameter(2, PI_NOMBRE_ARCHIVO).setParameter(3, PI_CODIGO_ARCHIVO)
					.setParameter(4, PI_EXTENSION_ARCHIVO).setParameter(5, PI_USU_CREACION)
					.setParameter(6, PI_IP_CREACION);

			System.out.println("pas 00002 : "  );
			query.execute();
			System.out.println("pas 00003 : "  );
			Integer cuenta = (Integer) query.getOutputParameterValue(7);
			System.out.println("pas 00004 : "  );
			String mensaje = (String) query.getOutputParameterValue(8);
			System.out.println("pas 00005 : "  );
			query.unwrap(ProcedureOutputs.class).release();
			if (cuenta == 0)
				auditoria.Rechazar(mensaje);

			System.out.println("pas 00006 : "  );
			System.out.println("pas 00007 : " + mensaje );
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return auditoria;
	}

	@Override
	public Auditoria Casilla_Estado(Long id_casilla) {	
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		
		try {
			Long PI_ID_CASILLA = id_casilla;
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_mef_mantenimiento + ".USP_CASILLA_ESTADO")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_CASILLA);
			
			
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
	public Auditoria Casilla_Actualizar(Casilla modelo) {
		
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		
		try {
			Long PI_ID_CASILLA = modelo.getId_casilla();
			String PI_HTML = modelo.getMensaje_html();
			
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery(pck_mef_mantenimiento + ".USP_CASILLA_ACTUALIZAR")
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, PI_ID_CASILLA)
		         	.setParameter(2, PI_HTML);
			
			/*System.out.println("ACTUALIZAR 1 ");
			System.out.println("ID CASILLA " + PI_ID_CASILLA); */
			
			List<Object[]> TableST = query.getResultList();
			query.unwrap(ProcedureOutputs.class).release();
			// System.out.println("ACTUALIZAR 2 ");
			
			int PI_CUENTA = 0;
			String PI_MENSAJE = "";
			for (int i = 0; i < TableST.size(); i++) {
				Object[] row = TableST.get(i);
				PI_CUENTA = Integer.valueOf(row[0] + "");
				PI_MENSAJE = String.valueOf(row[1]);			
			}

			if (PI_CUENTA == 0) {
				auditoria.Rechazar(PI_MENSAJE);	
			}else {				
				auditoria.objeto = PI_ID_CASILLA; 
			}
			
			
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}		
		
		return auditoria;	
	}
	
}
