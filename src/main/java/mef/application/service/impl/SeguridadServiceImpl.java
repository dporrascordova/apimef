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

import mef.application.dao.SeguridadDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.SeguridadPerfil;
import mef.application.modelo.SeguridadPersonal;
import mef.application.modelo.UsuarioPersonaGrilla;
import mef.application.service.SeguridadService;

@Service
public class SeguridadServiceImpl implements SeguridadService {

	@Autowired
	private SeguridadDao seguridadDao;

	@Override
	public Auditoria Perfil_Listar() {
		return seguridadDao.Perfil_Listar();
	}

	@Override
	public Auditoria Personal_Listar() {
		return seguridadDao.Personal_Listar();
	}

}
