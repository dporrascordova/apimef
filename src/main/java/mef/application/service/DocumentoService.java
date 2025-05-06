package mef.application.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import mef.application.modelo.Auditoria;
import mef.application.modelo.Documento;
import mef.application.modelo.DocumentoAnexo;
import mef.application.modelo.DocumentoEstado;
import mef.application.modelo.DocumentoFinalizar;
import mef.application.modelo.DocumentoGrilla;
import mef.application.modelo.DocumentoObservacion;
import mef.application.modelo.RespuestaMessage;

public interface DocumentoService {

	// public List<Documento> getDocumentos(Documento doc);

	public Auditoria DocumentoSolicitud_Listar(DocumentoGrilla modelo);

	public Auditoria Documento_Listar(Documento doc);

	public Auditoria Documento_Listar_Paginado(Documento doc);

	public Auditoria Documento_ListarUno(Integer id_documento);

	// public List<Documento> listDocumento();

	// public Documento saveOrUpdate(Documento doc);

	public Auditoria Documento_Insertar(Documento doc);

	public Auditoria Documento_FlgServicioError(int id_documento);

	public Auditoria Documento_Actualizar(Documento doc);

	public ByteArrayInputStream exportDocuments(Documento doc);

	public RespuestaMessage crearAnexo(DocumentoAnexo anexo);

	public List<DocumentoAnexo> getAnexosDocumentoById(Integer id_documento);

	public RespuestaMessage anularAnexos(Integer documentoId, String codigos, String usuarioModifica);

	public Auditoria Documento_Recepcionar(Integer documentoId, Integer anio, String numeroSid, int nro_folios,
			String asunto, String usuarioModifica, String ipmodifica, int ID_OFICINA, String ID_TIPO_DOCUMENTO,
			String HojaRuta, String Nro_Documento, String Desc_congresista, int id_prioridad);

	public Auditoria Documento_Observar(DocumentoObservacion obs);

	public Auditoria Documento_Observacion_Listar(Integer documentoId);

	public Auditoria Documento_Estado(DocumentoEstado modelo);

	public Auditoria Documento_Atender(DocumentoFinalizar doc);

	public Auditoria Documento_HojaRuta(DocumentoFinalizar doc);

	public Auditoria Documento_Actualizar_Estado(DocumentoObservacion obs);

	public Auditoria DocumentoActualizar_Estado(long ID_DOCUMENTO, long ID_ESTADO);

	public Auditoria Documento_Asignar(Integer id_documento, String username);

	public Auditoria Documento_Validar_Recep_Observar(Integer id_documento, String username);

	public RespuestaMessage anularAnexo(Integer documentoId, String codigoArchivo, String usuarioModifica);

	public Auditoria Validar_Hoja_Ruta_Anexo(Integer documentoId, String hojaRuta);

	public Auditoria Documento_Desasignar(Integer id_documento);

	public Auditoria Documento_NoPresentar(Integer documentoId);

	public Auditoria Documento_Agregar_HojaRuta(Integer documentoId, Integer anio, String numeroSid, String usuario);

	public Auditoria Documento_Recepcionar(Integer documentoId, Date fechaRecepcion, String usuarioModifica);

	public Auditoria Documento_Listar_PorEstado(Integer estadoId);

	public Auditoria Documento_Listar_PorEstado2(Integer estadoId);

	public Auditoria Actualizar_Estado(long ID_DOCUMENTO, long ID_ESTADO, String des_error);

	public Auditoria Documento_Listar_Pendiente_Bandeja(String tab);

	Auditoria updateAnexo(long idDocumento, String idAnexo );
}
