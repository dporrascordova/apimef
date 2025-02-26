package mef.application.service.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.io.IOUtils;
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
import org.springframework.stereotype.Service;

import mef.application.dao.NotificacionDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.NotificacionGrilla;
import mef.application.service.NotificacionService;

@Service
public class NotificacionServiceImpl implements NotificacionService {

	@Autowired
	private NotificacionDao notificacionDao;

	@Override
	public Auditoria Notificacion_Enviar_Listar(NotificacionGrilla modelo) {
		return notificacionDao.Notificacion_Enviar_Listar(modelo);
	}

	@Override
	public Auditoria Notificacion_Enviado_Listar(NotificacionGrilla modelo) {
		return notificacionDao.Notificacion_Enviado_Listar(modelo);
	}
	
	@Override
	public Auditoria Notificacion_Enviado_Paginado(NotificacionGrilla modelo) {
		return notificacionDao.Notificacion_Enviado_Paginado(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayInputStream Notificacion_Exportar(NotificacionGrilla modelo) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			String[] columns = { "Fecha Notificacion", "Tipo Documento", "Destinatario", "Nro Documento", "Hoja Ruta",
					"Hoja Envio", "Correo Electronico","organo Emisor",  "Estado","Fecha Lectura","Usuario Creacion","Fecha Creacion" };

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
			
			

			Auditoria auditoria = this.Notificacion_Enviado_Listar(modelo);
			// System.out.println("ENTRO XD 2");
			if (auditoria.ejecucion_procedimiento) {
				// System.out.println("ENTRO XD 3");
				List<NotificacionGrilla> listDoc = (List<NotificacionGrilla>) auditoria.objeto;
				int initRow = 1;
				System.out.println("ENTRO XD 3.5:" + listDoc.size());
				for (NotificacionGrilla documento : listDoc) {
					row = sheet.createRow(initRow);

					//String[] columns = { "Fecha Notificacion", "Tipo Documento", "Destinatario", "Nro Documento", "Hoja Ruta",
							//"Hoja Envio", "Correo Electronico","�rgano Emisor",  "Estado","Fecha Lectura","Usuario Creacion","Fecha Creacion" };

					row.createCell(0).setCellValue(documento.getFec_notificacion());
					row.createCell(1).setCellValue(documento.getDesc_tipo_documento()); 
					row.createCell(2).setCellValue(documento.getRemitente());
					row.createCell(3).setCellValue(documento.getNro_documento());
					row.createCell(4).setCellValue(documento.getHoja_ruta());
					row.createCell(5).setCellValue(documento.getHoja_envio());
					row.createCell(6).setCellValue(documento.getCorreo());
					row.createCell(7).setCellValue(documento.getDesc_oficina());
					row.createCell(8).setCellValue(documento.getDesc_estado());
					row.createCell(9).setCellValue(documento.getFec_modificacion());
					row.createCell(10).setCellValue(documento.getUsu_creacion());
					row.createCell(11).setCellValue(documento.getFec_creacion());
					
					 
					
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

			System.out.println("ENTRO XD 4");
			workbook.write(stream);
			System.out.println("ENTRO XD 5");

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			// e.printStackTrace();
		}

		return new ByteArrayInputStream(stream.toByteArray());
	}

	// public ByteArrayInputStream Notificacion_Exportar(NotificacionGrilla modelo);
}
