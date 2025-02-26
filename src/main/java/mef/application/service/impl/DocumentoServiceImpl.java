package mef.application.service.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mef.application.dao.DocumentoDao;
import mef.application.infrastructure.CommonHelpers;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Documento;
import mef.application.modelo.DocumentoAnexo;
import mef.application.modelo.DocumentoEstado;
import mef.application.modelo.DocumentoFinalizar;
import mef.application.modelo.DocumentoGrilla;
import mef.application.modelo.DocumentoObservacion;
import mef.application.modelo.RespuestaMessage;
//import mef.application.repositorio.DocumentoRepositorio;
import mef.application.service.DocumentoService;

@Service
public class DocumentoServiceImpl implements DocumentoService {

	@Autowired
	private DocumentoDao documentoDao;

	@Override
	public Auditoria Documento_Listar(Documento doc) {
		return documentoDao.Documento_Listar(doc);
	}

	@Override
	public Auditoria Documento_Listar_Paginado(Documento doc) {
		return documentoDao.Documento_Listar_Paginado(doc);
	}

	@Override
	public Auditoria DocumentoSolicitud_Listar(DocumentoGrilla modelo) {
		return documentoDao.DocumentoSolicitud_Listar(modelo);
	}

	@Override
	public Auditoria Documento_ListarUno(Integer id_documento) {
		return documentoDao.Documento_ListarUno(id_documento);
	}

	@Override
	public Auditoria Documento_Insertar(Documento doc) {
		return documentoDao.Documento_Insertar(doc);
	}

	@Override
	public Auditoria Documento_FlgServicioError(int id_documento) {
		return documentoDao.Documento_FlgServicioError(id_documento);
	}

	@Override
	public Auditoria Documento_Actualizar(Documento doc) {
		return documentoDao.Documento_Actualizar(doc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayInputStream exportDocuments(Documento doc) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			String[] columns = { "Nro Solicitud", "Asunto", "Tipo Documento", "Nro Documento", "Remitente",
					"Estado Tramite", "Fecha Registro", "Fecha Recepcion", "Organo Destino", "Nro Folios", "Nro Anexos",
					"Hoja de Ruta Referencial", "Hoja de Ruta" };

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Reporte");
			Row row = sheet.createRow(0);
			// workbook.au.AllocatedRange.AutoFitRows();
//sheet.autof
			// org.apache.poi.ss.usermodel.Color color = new
			// org.apache.poi.ss.usermodel.Color() {

			// };

			XSSFCellStyle style = workbook.createCellStyle();

			Color c = new Color(179, 0, 17);
			XSSFColor xssfColor = new XSSFColor(c);

			style.setFillForegroundColor(xssfColor);
			// style.setf
			style.setBorderTop(BorderStyle.DOUBLE);
			style.setBorderBottom(BorderStyle.THIN);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setAlignment(HorizontalAlignment.CENTER);
			// style.setFillForegroundColor(
			// XSSFColor.toXSSFColor(color));
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Color c2 = new Color(255, 255, 255);
			XSSFColor xssfColor2 = new XSSFColor(c2);

			XSSFFont font = workbook.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 12);
			font.setBold(true);
			font.setColor(xssfColor2);
			// font.setColor(XSSFColor.toXSSFColor(color));//.XSSFColorPredefined.BLUE.getIndex());
			style.setFont(font);

			for (int i = 0; i < columns.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(style);
				sheet.autoSizeColumn(i);
				// cell.s
			}

			Auditoria auditoria = this.Documento_Listar(doc);
			// System.out.println("ENTRO XD 2");
			if (auditoria.ejecucion_procedimiento) {
				// System.out.println("ENTRO XD 3");
				List<Documento> listDoc = (List<Documento>) auditoria.objeto;
				int initRow = 1;
				// System.out.println("ENTRO XD 3.5:" + listDoc.size());
				for (Documento documento : listDoc) {
					row = sheet.createRow(initRow);
					row.createCell(0).setCellValue(documento.getId_documento());
					row.createCell(1).setCellValue(documento.getAsunto());
					row.createCell(2).setCellValue(documento.getDesc_tipo_documento());
					row.createCell(3).setCellValue(documento.getNro_documento());
					row.createCell(4).setCellValue(documento.getNombre_persona());
					row.createCell(5).setCellValue(documento.getDesc_estado_documento());
					row.createCell(6).setCellValue(documento.getFec_creacion());
					row.createCell(7).setCellValue(documento.getSrt_fecha_recibido());
					row.createCell(8).setCellValue(documento.getDesc_oficina());
					row.createCell(9).setCellValue(documento.getNro_folios());
					row.createCell(10).setCellValue(documento.getCuenta_anexos());
					row.createCell(11).setCellValue(documento.getHoja_ruta());
					row.createCell(12).setCellValue(
							documento.getNumero_sid() + "-" + (documento.getAnio() == 0 ? "" : documento.getAnio()));

					initRow++;
					// row.setHeight((short)-1);

					/*
					 * XSSFCellStyle styler = workbook.createCellStyle(); styler.setWrapText(true);
					 * row.setRowStyle(styler); row.getCell(0).setCellStyle(styler);
					 * row.getCell(1).setCellStyle(styler);
					 */

					// sheet.auto
				}
			}

			for (int i = 0; i < columns.length; i++) {
				// Cell cell = row.createCell(i);
				// cell.setCellValue(columns[i]);
				// cell.setCellStyle(style);
				sheet.autoSizeColumn(i);
				// cell.s
			}

			// System.out.println("ENTRO XD 4");
			workbook.write(stream);
			// System.out.println("ENTRO XD 5");

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			// e.printStackTrace();
		}

		return new ByteArrayInputStream(stream.toByteArray());
	}

	@Override
	public RespuestaMessage crearAnexo(DocumentoAnexo anexo) {
		return documentoDao.crearAnexo(anexo);
	}

	@Override
	public List<DocumentoAnexo> getAnexosDocumentoById(Integer id_documento) {
		return documentoDao.getAnexosDocumentoById(id_documento);
	}

	@Override
	public RespuestaMessage anularAnexos(Integer documentoId, String codigos, String usuarioModifica) {
		return documentoDao.anularAnexos(documentoId, codigos, usuarioModifica);
	}

	@Override
	public Auditoria Documento_Recepcionar(Integer documentoId, Integer anio, String numeroSid, int nro_folios,
			String asunto, String usuarioModifica, String ipmodifica, int ID_OFICINA, String ID_TIPO_DOCUMENTO,
			String HojaRuta, String Nro_Documento, String Desc_congresista, int id_prioridad) {
		return documentoDao.Documento_Recepcionar(documentoId, anio, numeroSid, nro_folios, asunto, usuarioModifica,
				ipmodifica, ID_OFICINA, ID_TIPO_DOCUMENTO, HojaRuta, Nro_Documento, Desc_congresista, id_prioridad);
	}

	@Override
	public Auditoria Documento_Observar(DocumentoObservacion obs) {
		return documentoDao.Documento_Observar(obs);
	}

	@Override
	public Auditoria Documento_Actualizar_Estado(DocumentoObservacion obs) {
		return documentoDao.Documento_Actualizar_Estado(obs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Documento_Observacion_Listar(Integer documentoId) {
		return documentoDao.Documento_Observacion_Listar(documentoId);
	}

	@Override
	public Auditoria Documento_Estado(DocumentoEstado modelo) {
		return documentoDao.Documento_Estado(modelo);
	}

	public Auditoria Documento_Atender(DocumentoFinalizar doc) {
		return documentoDao.Documento_Atender(doc);
	}

	public Auditoria Documento_HojaRuta(DocumentoFinalizar doc) {
		return documentoDao.Documento_HojaRuta(doc);
	}

	@Override
	public Auditoria Documento_Asignar(Integer id_documento, String username) {
		return documentoDao.Documento_Asignar(id_documento, username);
	}

	@Override
	public Auditoria Documento_Validar_Recep_Observar(Integer id_documento, String username) {
		return documentoDao.Documento_Validar_Recep_Observar(id_documento, username);
	}

	@Override
	public RespuestaMessage anularAnexo(Integer documentoId, String codigoArchivo, String usuarioModifica) {
		return documentoDao.anularAnexo(documentoId, codigoArchivo, usuarioModifica);
	}

	@Override
	public Auditoria Validar_Hoja_Ruta_Anexo(Integer documentoId, String hojaRuta) {
		return documentoDao.Validar_Hoja_Ruta_Anexo(documentoId, hojaRuta);
	}

	@Override
	public Auditoria Documento_Desasignar(Integer id_documento) {
		return documentoDao.Documento_Desasignar(id_documento);
	}

	@Override
	public Auditoria Documento_NoPresentar(Integer documentoId) {
		return documentoDao.Documento_NoPresentar(documentoId);
	}

	@Override
	public Auditoria DocumentoActualizar_Estado(long ID_DOCUMENTO, long ID_ESTADO) {
		return documentoDao.DocumentoActualizar_Estado(ID_DOCUMENTO, ID_ESTADO);
	}

	@Override
	public Auditoria Documento_Agregar_HojaRuta(Integer documentoId, Integer anio, String numeroSid, String usuario) {
		return documentoDao.Documento_Agregar_HojaRuta(documentoId, anio, numeroSid, usuario);
	}

	@Override
	public Auditoria Documento_Recepcionar(Integer documentoId, Date fechaRecepcion, String usuarioModifica) {
		return documentoDao.Documento_Recepcionar(documentoId, fechaRecepcion, usuarioModifica);
	}

	@Override
	public Auditoria Documento_Listar_PorEstado(Integer estadoId) {
		return documentoDao.Documento_Listar_PorEstado(estadoId);
	}

	@Override
	public Auditoria Documento_Listar_PorEstado2(Integer estadoId) {
		return documentoDao.Documento_Listar_PorEstado2(estadoId);
	}


	@Override
	public Auditoria Actualizar_Estado(long ID_DOCUMENTO , long ID_ESTADO, String des_error ) {
		return documentoDao.Actualizar_Estado(ID_DOCUMENTO, ID_ESTADO, des_error);
	}


	@Override
	public Auditoria Documento_Listar_Pendiente_Bandeja(String tab) {
		return documentoDao.Documento_Listar_Pendiente_Bandeja(tab);
	}
}
