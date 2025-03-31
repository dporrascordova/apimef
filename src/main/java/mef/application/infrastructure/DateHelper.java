package mef.application.infrastructure;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.gob.mef.std.bs.web.ws.ErrorInfo;
import pe.gob.mef.std.bs.web.ws.TaFeriadosDto;
import pe.gob.mef.std.bs.web.ws.VentanillastdProxy;

public class DateHelper {

	public static String CalcularFechaAnulacion() throws ErrorInfo, RemoteException, ParseException {
		VentanillastdProxy proxy = new VentanillastdProxy();
		SimpleDateFormat fromaterF = new SimpleDateFormat("dd/MM/yyyy");
		Date date_hoy = new Date();

		int Dias = (1 * 24);
		Calendar calendar_hoy = Calendar.getInstance();
		calendar_hoy.setTime(date_hoy);
		calendar_hoy.add(Calendar.HOUR, Dias);

		// Agregamos el 1er dia
		Date fecha_masuno = calendar_hoy.getTime();
		String Fecha_Anu = "";
		TaFeriadosDto[] ListaFeriados;
		try {
			ListaFeriados = proxy.listaDiasFeriados();

		} catch (Exception ex) {
			// auditoria.Error(ex);
			System.out.println("Feriados:" + ex.toString());
			ListaFeriados = null;
		}

		boolean valido_rutina = false;
		int agregar_dia = 1;
		while (valido_rutina == false) {
			// agregar_dia++;
			calendar_hoy.add(Calendar.HOUR, agregar_dia * 24);
			fecha_masuno = calendar_hoy.getTime();
			valido_rutina = true;

			if (ListaFeriados != null) {
				valido_rutina = Loop_Feriados(fecha_masuno, ListaFeriados);
			}
		}

		// Agregamos el 2do dia
		calendar_hoy.add(Calendar.HOUR, Dias);
		fecha_masuno = calendar_hoy.getTime();
		valido_rutina = false;

		// agregar_dia = 0;
		while (valido_rutina == false) {
			// agregar_dia++;

			fecha_masuno = calendar_hoy.getTime();
			valido_rutina = true;

			if (ListaFeriados != null)
				valido_rutina = Loop_Feriados(fecha_masuno, ListaFeriados);

			if (!valido_rutina)
				calendar_hoy.add(Calendar.HOUR, agregar_dia * 24);
		}

		Fecha_Anu = fromaterF.format(fecha_masuno);

		return Fecha_Anu;
	}

	static boolean Loop_Feriados(Date fecha, TaFeriadosDto[] ListaFeriados)
			throws ErrorInfo, RemoteException, ParseException {
		boolean valido = true;
		SimpleDateFormat fromaterF = new SimpleDateFormat("dd/MM/yyyy");

		for (TaFeriadosDto diasf : ListaFeriados) {

			String Fecha_Feriado = fromaterF.format(diasf.getFeFecha().getTime());
			if (Fecha_Feriado.equals(fromaterF.format(fecha))) {
				valido = false;
				break;
			}
			if (fecha.getDay() == 6 || fecha.getDay() == 0) {
				valido = false;
				break;
			}

		}
		return valido;
	}

}
