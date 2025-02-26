package mef.application.component;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import antlr.collections.List;

public class DiasHabiles {
	
/*	public long getDiasHabilesTranscurridos(GregorianCalendar calIni, GregorianCalendar calFin, int estado) {

		 

        if (calFin == null) {

                        if (estado == 6 || estado == 7) {

                                        return 0;

                        } else {

                                        calFin = new GregorianCalendar();

                        }

        }



        GregorianCalendar calTemp = new GregorianCalendar();

        calTemp.set(calFin.get(Calendar.YEAR), calFin.get(Calendar.MONTH), calFin.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        calTemp.add(Calendar.DAY_OF_MONTH, -1);



        if (calTemp.equals(calIni)) {

                        return 0;

        }



        long unDia = 1000 * 60 * 60 * 24;

        long ldiafin = calFin.getTimeInMillis();

        long ldiaini = calIni.getTimeInMillis();

        long restaEnDias = ((long) ((ldiafin - ldiaini) / unDia));



        long diasDeSamana = (long) Math.floor(restaEnDias / 7);

        long diasNoIncluidos = restaEnDias - (diasDeSamana * 7);

        long diastranscurridos = restaEnDias - (diasDeSamana * 2);



        for (int j = 0; j < (int) diasNoIncluidos; j++) {

                        calTemp.set(calFin.get(Calendar.YEAR), calFin.get(Calendar.MONTH), calFin.get(Calendar.DAY_OF_MONTH), 0, 0,

                                                        0);

                        calTemp.add(Calendar.DAY_OF_MONTH, (-1 * j));

                        if (calTemp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY

                                                        || calTemp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

                                        diastranscurridos--;

                        }

        }



        List<Timestamp> diasFeriados = getDiaHabilfin();

        if (diasFeriados != null) {

                        for (int i = 0; i < diasFeriados.size(); i++) {

                                        Timestamp diaferiado = (Timestamp) diasFeriados.get(i);

                                        calTemp.setTimeInMillis(diaferiado.getTime());

                                        calTemp.set(calTemp.get(Calendar.YEAR), calTemp.get(Calendar.MONTH), calTemp.get(Calendar.DAY_OF_MONTH),

                                                                       0, 0, 0);

                                        if ((calTemp.after(calIni) && calTemp.before(calFin)) || calTemp.equals(calIni)

                                                                       || calTemp.equals(calFin)) {

                                                        if (calTemp.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY

                                                                                       && calTemp.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {

                                                                       diastranscurridos--;

                                                        }

                                        }

                        }

        }



        return diastranscurridos;

}



public Timestamp getDiaHabilfin(Timestamp diaini, long dias, Timestamp supdiaini, Timestamp supdiafin, int estado) {

        long unDia = 1000 * 60 * 60 * 24;

        Timestamp diaFin = new Timestamp(diaini.getTime() + (unDia * dias));

        long diahastaahora = getDiasHabilesTranscurridos(diaini, diaFin, supdiaini, supdiafin, estado);

        if (diahastaahora < dias) {

                        while (diahastaahora < dias) {

                                        diaFin = new Timestamp(diaFin.getTime() + ((dias - diahastaahora) * unDia));

                                        diahastaahora = getDiasHabilesTranscurridos(diaini, diaFin, supdiaini, supdiafin, estado);

                        }

                        // CORECCION A 1

                        if (diahastaahora > dias) {

                                        diaFin = new Timestamp(diaFin.getTime() - unDia);

                                        diahastaahora = getDiasHabilesTranscurridos(diaini, diaFin, supdiaini, supdiafin, estado);

                        }

        }

        return diaFin;

}*/


}
