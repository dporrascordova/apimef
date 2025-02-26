package mef.application.service;

import java.io.ByteArrayInputStream;

import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.CasillaArchivo;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.CasillaUno;
import mef.application.modelo.Documento; 

public interface CasillaService {

	public Auditoria Casilla_Listar(CasillaGrilla modelo);

	public Auditoria Casilla_Listar_Uno(CasillaUno modelo);
	
	public Auditoria Casilla_Insertar(Casilla modelo);

	public Auditoria CasillaArchivo_Insertar(CasillaArchivo modelo);
	
	public Auditoria Casilla_Estado(Long id_casilla);
	
	public Auditoria Casilla_Actualizar(Casilla modelo);
	
	public ByteArrayInputStream Casilla_Exportar(CasillaGrilla modelo);
	
	
	
}
