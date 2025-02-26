package mef.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mef.application.dao.UsuarioDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Modulos;
import mef.application.modelo.RecuperarClave;
import mef.application.modelo.Usuario;
import mef.application.modelo.UsuarioActivacion;
import mef.application.modelo.UsuarioEstado;
import mef.application.modelo.UsuarioInsertar;
import mef.application.modelo.UsuarioLogin;
import mef.application.modelo.UsuarioOlvidoClave;
import mef.application.modelo.UsuarioPersona;
import mef.application.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_Insertar(UsuarioInsertar modelo) {
		System.out.println("llego  asadsaxd");
		return usuarioDao.Usuario_Insertar(modelo);
	}

	public Auditoria Usuario_Validar(UsuarioLogin modelo) {
		return usuarioDao.Usuario_Validar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_RecuperarClave(RecuperarClave modelo) {
		return usuarioDao.Usuario_RecuperarClave(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_VerificaCodigo(RecuperarClave modelo) {
		return usuarioDao.Usuario_VerificaCodigo(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_ActualizaClave(UsuarioLogin modelo) {
		return usuarioDao.Usuario_ActualizaClave(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_VerificaModificaClave(UsuarioOlvidoClave modelo) {
		return usuarioDao.Usuario_VerificaModificaClave(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_Modulos(int PI_ID_USUARIO) {
		return usuarioDao.Usuario_Modulos(PI_ID_USUARIO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_Estado(UsuarioEstado modelo) {
		return usuarioDao.Usuario_Estado(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_Eliminar(UsuarioEstado modelo) {
		return usuarioDao.Usuario_Eliminar(modelo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_Activar(UsuarioActivacion modelo) {
		return usuarioDao.Usuario_Activar(modelo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Auditoria Usuario_Noti_Count(int PI_ID_USUARIO) {
		return usuarioDao.Usuario_Noti_Count(PI_ID_USUARIO);
	}

	

}
