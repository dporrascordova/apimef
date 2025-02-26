package mef.application.service.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
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
import org.springframework.stereotype.Service;

import mef.application.dao.PersonaDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Documento;
import mef.application.modelo.PersonaBusqueda;
import mef.application.modelo.PersonaCorreo;
import mef.application.modelo.PersonaJuridica;
import mef.application.modelo.PersonaNatural;
import mef.application.modelo.PersonaUsuario;
import mef.application.modelo.PersonaValida;
import mef.application.modelo.Usuario;
import mef.application.modelo.UsuarioNatural;
import mef.application.modelo.UsuarioPersona;
import mef.application.modelo.UsuarioPersonaGrilla;
import mef.application.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaDao personaDao;

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaNatural_Insertar(PersonaNatural modelo) {
		return personaDao.PersonaNatural_Insertar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaJuridica_Insertar(PersonaJuridica modelo) {
		return personaDao.PersonaJuridica_Insertar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonasSolicitudes_Listar(UsuarioPersonaGrilla modelo) {
		return personaDao.PersonasSolicitudes_Listar(modelo);
	}
	
	@Override
	public Auditoria PersonaUsuario_ListarGeneral(UsuarioPersonaGrilla modelo) {
		return personaDao.PersonaUsuario_ListarGeneral(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayInputStream exportUsuarios(UsuarioPersonaGrilla modelo) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			String[] columns = { "Tipo Usuario", "Persona", "Razon social", "Numero documento","Celular", "Telefono",
					"Correo", "Representante", "Delegado", "Fecha activacion", "Fecha desactivacion",
					"Usuario Aprobo", "Fecha Aprobacion", "Departamento", "Provincia", "Distrito" };

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

			Auditoria auditoria = this.PersonaUsuario_ListarGeneral(modelo);
			// System.out.println("ENTRO XD 2");
			if (auditoria.ejecucion_procedimiento) {
				// System.out.println("ENTRO XD 3");
				List<UsuarioPersonaGrilla> listusu = (List<UsuarioPersonaGrilla>) auditoria.objeto;
				int initRow = 1;
				// System.out.println("ENTRO XD 3.5:" + listDoc.size());
				for (UsuarioPersonaGrilla entidad : listusu) {
					row = sheet.createRow(initRow);
					row.createCell(0).setCellValue(entidad.getDesc_tipo_usuario());
					row.createCell(1).setCellValue(entidad.getDesc_tipo_persona());
					row.createCell(2).setCellValue(entidad.getNom_persona());				
					row.createCell(3).setCellValue(entidad.getNro_documento());
					row.createCell(4).setCellValue(entidad.getCelular());
					row.createCell(5).setCellValue(entidad.getTelefono());
					row.createCell(6).setCellValue(entidad.getCorreo());
					row.createCell(7).setCellValue(entidad.getRepresentante());
					row.createCell(8).setCellValue(entidad.getDelegado());
					row.createCell(9).setCellValue(entidad.getFec_activacion());
					row.createCell(10).setCellValue(entidad.getFec_desactivacion());
					row.createCell(11).setCellValue(entidad.getUsu_creacion());
					row.createCell(12).setCellValue(entidad.getFec_creacion());
					row.createCell(13).setCellValue(entidad.getDepartamento());
					row.createCell(14).setCellValue(entidad.getProvincia());
					row.createCell(15).setCellValue(entidad.getDistrito());
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

	
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaUsuario_Listar(UsuarioPersonaGrilla modelo) {
		return personaDao.PersonaUsuario_Listar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaNatural_ListarUno(PersonaNatural modelo,int flg_valido) {
		return personaDao.PersonaNatural_ListarUno(modelo, flg_valido);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaJuridica_ListarUno(int idpersona,int flg_valido) {
		return personaDao.PersonaJuridica_ListarUno(idpersona, flg_valido);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaInterno_ListarUno(long idpersona) {
		return personaDao.PersonaInterno_ListarUno(idpersona);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Persona_Usuario(PersonaUsuario modelo) {
		return personaDao.Persona_Usuario(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Persona_Validar(PersonaValida modelo) {
		return personaDao.Persona_Validar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria UsuarioPersona_Listar(long ID_USUARIO) {
		return personaDao.UsuarioPersona_Listar(ID_USUARIO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Persona_Correo(Long id_persona) {
		return personaDao.Persona_Correo(id_persona);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaNatural_Actualizar(PersonaNatural modelo) {
		return personaDao.PersonaNatural_Actualizar(modelo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria PersonaJuridica_Actualizar(PersonaJuridica modelo) {
		return personaDao.PersonaJuridica_Actualizar(modelo);
	}
	//public Auditoria PersonaNatural_Actualizar(PersonaNatural modelo)
	
}
