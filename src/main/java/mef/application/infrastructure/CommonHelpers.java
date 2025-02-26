package mef.application.infrastructure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class CommonHelpers {

	/**
	 * Retorna una cadena con la fecha formatea a formato espanol.
	 * 
	 * @param fecha
	 * @return
	 */
	public static String formatoDateToString(Date fecha) {
		if (fecha == null)
			return "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String strDate = dateFormat.format(fecha);
		return strDate;
	}

	/**
	 * Permite convertir un String en fecha (Date).
	 * 
	 * @param fecha Cadena de fecha dd/MM/yyyy hh:mm:s
	 * @return Objeto Date
	 */
	public static Date formatoStringToDate(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fecha);
		} catch (ParseException ex) {
			System.out.println(ex);
		}
		return fechaDate;
	}

	public static String Generar_Nombre_Archivo(MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
		// String fileName = "n-ddv" + usuario.getUCelular() + "." + extension;
		return uuid.toString() + "." + extension;
	}

	public static String Generar_Nombre_Archivo(MultipartFile file, String newFilename) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
		return newFilename + "." + extension;
	}
	
	public static boolean Validar_Captcha(String secretKey, String response) {
		try {
			String url = "https://www.google.com/recaptcha/api/siteverify?" + "secret=" + secretKey + "&response="
					+ response;

			System.out.println("Url Captcha: " + url);
			InputStream res = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			String jsonText = sb.toString();
			res.close();

			JSONObject json = new JSONObject(jsonText);
			return json.getBoolean("success");
		} catch (Exception e) {
			return false;
		}
	}

}
