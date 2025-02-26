package mef.application.service;

import mef.application.modelo.Auditoria;
import mef.application.modelo.Distrito;
import mef.application.modelo.OficinaEstado;
import mef.application.modelo.OficinaGrilla;
import mef.application.modelo.Oficinas;
import mef.application.modelo.Provincia;
import mef.application.modelo.TipoDocumento;

public interface MefService {
	
	public Auditoria Mef_Servicio(int ID_SERVICIO);

	public Auditoria Mef_Oficinas_Listar(OficinaGrilla modelo);

	public Auditoria Mef_Tipo_Doc_Listar_Lx(TipoDocumento modelo);
	
	public Auditoria Mef_Tipo_Doc_Estado(TipoDocumento modelo);

	public Auditoria Mef_Oficinas_Listar_Externo(Oficinas modelo);
	
	public Auditoria Mef_Oficinas_Listar_Interno(Oficinas modelo);

	public Auditoria Mef_Tipo_Doc_Listar();
	
	public Auditoria Mef_Dep_Listar();
	
	public Auditoria Mef_Prov_Listar(Provincia modelo);
	
	public Auditoria Mef_Dist_Listar(Distrito modelo);
	
	public Auditoria Mef_Oficinas_Actualizar(String CADENA);
	 
	public Auditoria Mef_Oficinas_Estado_Externo(OficinaEstado oficina);

	public Auditoria Mef_TipoDoc_Actualizar(String CADENA);

	public Auditoria Mef_Dep_Actualizar(String CADENA);
	
	public Auditoria Mef_Prov_Actualizar(String CADENA);
	
	public Auditoria Mef_Dist_Actualizar(String CADENA);
}
