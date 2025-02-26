package mef.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mef.application.exception.ResourceNotFoundException;
import mef.application.modelo.Estado;
import mef.application.repositorio.EstadoRepositorio;
import mef.application.service.EstadoService;

@Service
public class EstadoServiceImpl implements EstadoService {
	
	@Autowired
	private EstadoRepositorio estadoRepositorio;

	@Override
	public List<Estado> listEstados() {
		return estadoRepositorio.findAll();
	}

	@Override
	public Estado getEstado(int id) {
		return estadoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estado", "id", id));
	}

}
