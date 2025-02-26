package mef.application.dao;

import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.CasillaArchivo;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.CasillaUno;

public interface CasillaDao {

	public Auditoria Casilla_Listar(CasillaGrilla modelo);

	public Auditoria Casilla_Listar_Uno(CasillaUno modelo);
	
	public Auditoria Casilla_Insertar(Casilla modelo);

	public Auditoria CasillaArchivo_Insertar(CasillaArchivo modelo);
	
	public Auditoria Casilla_Estado(Long id_casilla);
	
	public Auditoria Casilla_Actualizar(Casilla modelo);
	
}
