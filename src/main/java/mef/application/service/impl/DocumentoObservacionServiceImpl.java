package mef.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mef.application.dao.DocumentoObservacionDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.DocumentoObservacion;
import mef.application.service.DocumentoObservacionService;

@Service
public class DocumentoObservacionServiceImpl implements DocumentoObservacionService {

	
	@Autowired
	private DocumentoObservacionDao documentoObservacionDao;

	@Override
	public Auditoria DocumentoObservacion_Listar(DocumentoObservacion modelo) {
		return documentoObservacionDao.DocumentoObservacion_Listar(modelo);
	}

}
