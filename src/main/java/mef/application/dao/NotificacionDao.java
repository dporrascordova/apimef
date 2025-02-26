package mef.application.dao;

import mef.application.modelo.Auditoria;
import mef.application.modelo.NotificacionGrilla;

public interface NotificacionDao {

	public Auditoria Notificacion_Enviar_Listar(NotificacionGrilla modelo);
	
	public Auditoria Notificacion_Enviado_Listar(NotificacionGrilla modelo);
	
	public Auditoria Notificacion_Enviado_Paginado(NotificacionGrilla modelo);
	
}
