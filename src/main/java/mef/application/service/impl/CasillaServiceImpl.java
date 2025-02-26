package mef.application.service.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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

import mef.application.dao.CasillaDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.CasillaArchivo;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.CasillaUno;
import mef.application.modelo.Documento;
import mef.application.service.CasillaService;

@Service
public class CasillaServiceImpl implements CasillaService {

	@Autowired
	private CasillaDao casillaDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Casilla_Listar(CasillaGrilla modelo) {
		return casillaDao.Casilla_Listar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Casilla_Listar_Uno(CasillaUno modelo) {
		return casillaDao.Casilla_Listar_Uno(modelo);
	}

	@Override
	public Auditoria Casilla_Insertar(Casilla modelo) {
		return casillaDao.Casilla_Insertar(modelo);
	}

	@Override
	public Auditoria CasillaArchivo_Insertar(CasillaArchivo modelo) {
		return casillaDao.CasillaArchivo_Insertar(modelo);
	}

	@Override
	public Auditoria Casilla_Estado(Long id_casilla) {
		return casillaDao.Casilla_Estado(id_casilla);
	}
	
	@Override
	public Auditoria Casilla_Actualizar(Casilla modelo) {
		return casillaDao.Casilla_Actualizar(modelo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayInputStream Casilla_Exportar(CasillaGrilla modelo) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			String[] columns = { "Hoja de ruta", "Fecha Notificacion", "Tipo Documento", "Nro Documento", "Organo Emisor",
					"Asunto", "Estado" };

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Reporte");
			Row row = sheet.createRow(0);
			//workbook.au.AllocatedRange.AutoFitRows();
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

			Auditoria auditoria = this.Casilla_Listar(modelo);
			// System.out.println("ENTRO XD 2");
			if (auditoria.ejecucion_procedimiento) {
				// System.out.println("ENTRO XD 3");
				List<CasillaGrilla> listDoc = (List<CasillaGrilla>) auditoria.objeto;
				int initRow = 1;
				System.out.println("ENTRO XD 3.5:" + listDoc.size());
				for (CasillaGrilla documento : listDoc) {
					row = sheet.createRow(initRow);

					row.createCell(0).setCellValue(documento.getHoja_ruta());
					row.createCell(1).setCellValue(documento.getFec_creacion());
					row.createCell(2).setCellValue(documento.getDesc_tipo_documento());
					row.createCell(3).setCellValue(documento.getNro_documento());
					row.createCell(4).setCellValue(documento.getDesc_oficina());
					row.createCell(5).setCellValue(documento.getObservacion());
					row.createCell(6).setCellValue(documento.getDesc_estado_documento());
					//row.createCell(7).setCellValue(documento.getUsu_creacion());
					
					initRow++;
					//row.setHeight((short)-1);
					
					/*XSSFCellStyle styler = workbook.createCellStyle();
					styler.setWrapText(true);
					row.setRowStyle(styler);
					row.getCell(0).setCellStyle(styler);
					row.getCell(1).setCellStyle(styler);*/
					 
					//sheet.auto
				}
			}
			
			for (int i = 0; i < columns.length; i++) {
				//Cell cell = row.createCell(i);
				//cell.setCellValue(columns[i]);
				//cell.setCellStyle(style);
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
}
