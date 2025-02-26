package mef.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mef.application.dao.EnlaceEncuestaDao;
import mef.application.modelo.Auditoria;
import mef.application.modelo.EnlaceEncuesta;
import mef.application.service.EnlaceEncuestaService;

@Service
public class EnlaceEncuestaServiceImpl implements EnlaceEncuestaService {

	@Autowired
	private EnlaceEncuestaDao enlaceEncuestaDao; 
	
	@Override
	public Auditoria crearMensajeEncuesta(EnlaceEncuesta modelo) {
		return enlaceEncuestaDao.crearMensajeEncuesta(modelo);
	}

	@Override
	public Auditoria obtenerEncuestaActiva() {
		return enlaceEncuestaDao.obtenerEncuestaActiva();
	}

}
