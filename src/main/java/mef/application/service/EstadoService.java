package mef.application.service;

import java.util.List;

import mef.application.modelo.Estado;

public interface EstadoService {
	
	public List<Estado> listEstados();
	public Estado getEstado(int id);

}
