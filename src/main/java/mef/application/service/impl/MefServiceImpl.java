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

import mef.application.dao.MefDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Departamento;
import mef.application.modelo.Distrito;
import mef.application.modelo.OficinaEstado;
import mef.application.modelo.OficinaGrilla;
import mef.application.modelo.Oficinas;
import mef.application.modelo.Provincia;
import mef.application.modelo.TipoDocumento;
//import mef.application.modelo.RespuestaMessage;
//import mef.application.modelo.Usuario;
import mef.application.service.MefService;

@Service
public class MefServiceImpl implements MefService {

	@Autowired
	private MefDao mefDao;

	@Override
	public Auditoria Mef_Servicio(int ID_SERVICIO) {
		return mefDao.Mef_Servicio(ID_SERVICIO);
	}

	@Override
	public Auditoria Mef_Oficinas_Listar(OficinaGrilla modelo) {
		return mefDao.Mef_Oficinas_Listar(modelo);
	}

	@Override
	public Auditoria Mef_Tipo_Doc_Listar_Lx(TipoDocumento modelo) {
		return mefDao.Mef_Tipo_Doc_Listar_Lx(modelo);
	}
	
	@Override
	public Auditoria Mef_Tipo_Doc_Estado(TipoDocumento modelo) {
		return mefDao.Mef_Tipo_Doc_Estado(modelo);
	}

	//public Auditoria Mef_Tipo_Doc_Estado(TipoDocumento modelo);
	
//	public Auditoria Mef_Tipo_Doc_Listar_Lx(TipoDocumento modelo);
	@Override
	public Auditoria Mef_Oficinas_Listar_Externo(Oficinas modelo) {
		return mefDao.Mef_Oficinas_Listar_Externo(modelo);
	}

	@Override
	public Auditoria Mef_Oficinas_Listar_Interno(Oficinas modelo) {
		return mefDao.Mef_Oficinas_Listar_Interno(modelo);
	}

	@Override
	public Auditoria Mef_Tipo_Doc_Listar() {
		return mefDao.Mef_Tipo_Doc_Listar();
	}

	@Override
	public Auditoria Mef_Dep_Listar() {
		return mefDao.Mef_Dep_Listar();
	}

	@Override
	public Auditoria Mef_Prov_Listar(Provincia modelo) {
		return mefDao.Mef_Prov_Listar(modelo);
	}

	@Override
	public Auditoria Mef_Dist_Listar(Distrito modelo) {
		return mefDao.Mef_Dist_Listar(modelo);
	}

	@Override
	public Auditoria Mef_Oficinas_Actualizar(String CADENA) {
		return mefDao.Mef_Oficinas_Actualizar(CADENA);
	}

	@Override
	public Auditoria Mef_Oficinas_Estado_Externo(OficinaEstado oficina) {
		return mefDao.Mef_Oficinas_Estado_Externo(oficina);
	}

	@Override
	public Auditoria Mef_TipoDoc_Actualizar(String CADENA) {
		return mefDao.Mef_TipoDoc_Actualizar(CADENA);
	}

	@Override
	public Auditoria Mef_Dep_Actualizar(String CADENA) {
		return mefDao.Mef_Dep_Actualizar(CADENA);
	}

	@Override
	public Auditoria Mef_Prov_Actualizar(String CADENA) {
		return mefDao.Mef_Prov_Actualizar(CADENA);
	}

	@Override
	public Auditoria Mef_Dist_Actualizar(String CADENA) {
		return mefDao.Mef_Dist_Actualizar(CADENA);
	}
	
}
