package mef.application.service;

import java.io.ByteArrayInputStream;

import mef.application.modelo.Auditoria;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.NotificacionGrilla;

public interface   NotificacionService {

	public Auditoria Notificacion_Enviar_Listar(NotificacionGrilla modelo);
	
	public Auditoria Notificacion_Enviado_Listar(NotificacionGrilla modelo);
	
	public Auditoria Notificacion_Enviado_Paginado(NotificacionGrilla modelo);

	public ByteArrayInputStream Notificacion_Exportar(NotificacionGrilla modelo);
	
}
