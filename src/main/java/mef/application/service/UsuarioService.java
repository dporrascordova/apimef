package mef.application.service;

import mef.application.modelo.Auditoria;
import mef.application.modelo.RecuperarClave; 
import mef.application.modelo.UsuarioActivacion;
import mef.application.modelo.UsuarioEstado;
import mef.application.modelo.UsuarioInsertar;
import mef.application.modelo.UsuarioLogin;
import mef.application.modelo.UsuarioOlvidoClave;

public interface UsuarioService {

	public Auditoria Usuario_Insertar(UsuarioInsertar modelo);

	public Auditoria Usuario_Validar(UsuarioLogin modelo);

	public Auditoria Usuario_RecuperarClave(RecuperarClave modelo);

	public Auditoria Usuario_VerificaCodigo(RecuperarClave modelo);

	public Auditoria Usuario_ActualizaClave(UsuarioLogin modelo);

	public Auditoria Usuario_VerificaModificaClave(UsuarioOlvidoClave modelo);

	public Auditoria Usuario_Modulos(int PI_ID_USUARIO);

	public Auditoria Usuario_Estado(UsuarioEstado modelo);

	public Auditoria Usuario_Eliminar(UsuarioEstado modelo);
	
	public Auditoria Usuario_Activar(UsuarioActivacion modelo);
	
	public Auditoria Usuario_Noti_Count(int PI_ID_USUARIO);
	
}
