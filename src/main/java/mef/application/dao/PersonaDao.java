package mef.application.dao;

import mef.application.modelo.Auditoria;
import mef.application.modelo.PersonaJuridica;
import mef.application.modelo.PersonaNatural;
import mef.application.modelo.PersonaUsuario;
import mef.application.modelo.PersonaValida;
import mef.application.modelo.UsuarioPersonaGrilla;

public interface PersonaDao {

	public Auditoria PersonaNatural_Insertar(PersonaNatural modelo);
	
	public Auditoria PersonaJuridica_Insertar(PersonaJuridica modelo);
	
	//public Auditoria Persona_Insertar(UsuarioNatural usuario);
	
	public Auditoria PersonasSolicitudes_Listar(UsuarioPersonaGrilla modelo);

	public Auditoria PersonaUsuario_Listar(UsuarioPersonaGrilla modelo);
	
	public Auditoria PersonaUsuario_ListarGeneral(UsuarioPersonaGrilla modelo);
	
	public Auditoria PersonaNatural_ListarUno(PersonaNatural modelo,int flg_valido);

	public Auditoria PersonaJuridica_ListarUno(int idpersona,int flg_valido);

	public Auditoria PersonaInterno_ListarUno(long idpersona);
	
	public Auditoria Persona_Usuario(PersonaUsuario modelo);
	
	public Auditoria Persona_Validar(PersonaValida modelo);

	public Auditoria UsuarioPersona_Listar(long ID_USUARIO) ;
	 
	public Auditoria Persona_Correo(Long id_persona);
	
	public Auditoria PersonaNatural_Actualizar(PersonaNatural modelo);
	
	public Auditoria PersonaJuridica_Actualizar(PersonaJuridica modelo);
	
}
