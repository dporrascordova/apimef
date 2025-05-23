package mef.application.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;*/
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

//@Entity
//@Table(name = "T_VUDM_DOCUMENTO")
public class Documento   {
	private int id_documento;

	private String id_tipo_documento;
	private String desc_tipo_documento;
	private int id_estado_documento;
	private String desc_estado_documento;
	private int id_oficina;
	private String desc_oficina;
	private String id_usuario;
	private String id_tipo_usuario;
	private String nro_documento;
	private int nro_folios;
	private String asunto;
	private Date fec_recibido;
	private String codigo_archivo;
	private String flg_estado;
	private int anio;
	private String numero_sid;
	private String hoja_ruta;
	private String observacion;
	private String usu_creacion;
	private String fec_creacion;
	private String ip_creacion;
	private String usu_modificacion;
	private Date fec_modificacion;
	private String ip_modificacion;
	private String orden;
	private int numpag;
	private int numreg;	
	private int totalreg;
	private String fecha_anulacion;
	private String fecha_observacion;
	private String fecha_subsanacion;
	private String usu_asignacion;	
	private String nomb_usu_asignacion;	
	private boolean asignado;	
	private String id_prioridad;
	private String id_comision;
	private String id_congresista;
	private String id_persona;
	private String nombre_persona; 
	private String desc_congresista;
	private String razon_social;
	private String destino;
	private String id_clasificacion;
	private int tipopersona;	
	private String nrodocumento_persona; 
	private String str_fec_modificacion;
	private String obsSgdd;

	public String getObsSgdd() {
		return obsSgdd;
	}

	public void setObsSgdd(String obsSgdd) {
		this.obsSgdd = obsSgdd;
	}

	public String getStr_fec_modificacion() {
		return str_fec_modificacion;
	}

	public void setStr_fec_modificacion(String str_fec_modificacion) {
		this.str_fec_modificacion = str_fec_modificacion;
	}

	public int getTipopersona() {
		return tipopersona;
	}

	public void setTipopersona(int tipopersona) {
		this.tipopersona = tipopersona;
	}



	private pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] oficinas;


	
	public String getNrodocumento_persona() {
		return nrodocumento_persona;
	}

	public void setNrodocumento_persona(String nrodocumento_persona) {
		this.nrodocumento_persona = nrodocumento_persona;
	}


	
	@Transient
	private Date fec_recibido_fin;

	@Transient
	private String fec_ini;

	@Transient
	private String fec_fin;

	@Transient
	private String srt_fecha_creacion;

	@Transient
	private String srt_fecha_recibido;

	@Transient
	private MultipartFile file;

	@Transient
	private MultipartFile[] filesAnexos;

	@Transient
	private String pathFile;

	@Transient
	private List<DocumentoAnexo> anexos;
	
	private List<String> anexoslink;
	

	public List<String> getAnexoslink() {
		return anexoslink;
	}

	public void setAnexoslink(List<String> anexoslink) {
		this.anexoslink = anexoslink;
	}

	@Transient
	private int cuenta_anexos;

	@Transient
	private String grupo_estados;

	@Transient
	private String[] codigo_archivo_anexos;

	@Transient
	private long[] codigo_oficinas;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	
	public String getDesc_congresista() {
		return desc_congresista;
	}

	public void setDesc_congresista(String desc_congresista) {
		this.desc_congresista = desc_congresista;
	}
	
	
	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}
	
	
	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	
	
	public String getId_clasificacion() {
		return id_clasificacion;
	}

	public void setId_clasificacion(String id_clasificacion) {
		this.id_clasificacion = id_clasificacion;
	}
	
	public String getId_comision() {
		return id_comision;
	}

	public void setId_comision(String id_comision) {
		this.id_comision = id_comision;
	}
	
	public String getId_congresista() {
		return id_congresista;
	}

	public void setId_congresista(String id_congresista) {
		this.id_congresista = id_congresista;
	}
	
	public String getId_prioridad() {
		return id_prioridad;
	}

	public void setId_prioridad(String id_prioridad) {
		this.id_prioridad = id_prioridad;
	}
	
	
	public String getSrt_fecha_recibido() {
		return srt_fecha_recibido;
	}

	public void setSrt_fecha_recibido(String srt_fecha_recibido) {
		this.srt_fecha_recibido = srt_fecha_recibido;
	}

	public String getSrt_fecha_creacion() {
		return srt_fecha_creacion;
	}

	public void setSrt_fecha_creacion(String srt_fecha_creacion) {
		this.srt_fecha_creacion = srt_fecha_creacion;
	}

	public Date getFec_recibido_fin() {
		return fec_recibido_fin;
	}

	public void setFec_recibido_fin(Date fec_recibido_fin) {
		this.fec_recibido_fin = fec_recibido_fin;
	}

	public int getId_documento() {
		return id_documento;
	}

	public void setId_documento(int id_documento) {
		this.id_documento = id_documento;
	}

	public String getId_tipo_documento() {
		return id_tipo_documento;
	}

	public void setId_tipo_documento(String id_tipo_documento) {
		this.id_tipo_documento = id_tipo_documento;
	}

	public int getId_estado_documento() {
		return id_estado_documento;
	}

	public void setId_estado_documento(int id_estado_documento) {
		this.id_estado_documento = id_estado_documento;
	}

	public int getId_oficina() {
		return id_oficina;
	}

	public void setId_oficina(int id_oficina) {
		this.id_oficina = id_oficina;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getId_tipo_usuario() {
		return id_tipo_usuario;
	}

	public void setId_tipo_usuario(String id_tipo_usuario) {
		this.id_tipo_usuario = id_tipo_usuario;
	}

	public String getNro_documento() {
		return nro_documento;
	}

	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}

	public int getNro_folios() {
		return nro_folios;
	}

	public void setNro_folios(int nro_folios) {
		this.nro_folios = nro_folios;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Date getFec_recibido() {
		return fec_recibido;
	}

	public void setFec_recibido(Date fec_recibido) {
		this.fec_recibido = fec_recibido;
	}

	public String getCodigo_archivo() {
		return codigo_archivo;
	}

	public void setCodigo_archivo(String codigo_archivo) {
		this.codigo_archivo = codigo_archivo;
	}

	public String getFlg_estado() {
		return flg_estado;
	}

	public void setFlg_estado(String flg_estado) {
		this.flg_estado = flg_estado;
	}

	public String getUsu_creacion() {
		return usu_creacion;
	}

	public void setUsu_creacion(String usu_creacion) {
		this.usu_creacion = usu_creacion;
	}

	public String getFec_creacion() {
		return fec_creacion;
	}

	public void setFec_creacion(String fec_creacion) {
		this.fec_creacion = fec_creacion;
	}

	public String getIp_creacion() {
		return ip_creacion;
	}

	public void setIp_creacion(String ip_creacion) {
		this.ip_creacion = ip_creacion;
	}

	public String getUsu_modificacion() {
		return usu_modificacion;
	}

	public void setUsu_modificacion(String usu_modificacion) {
		this.usu_modificacion = usu_modificacion;
	}

	public Date getFec_modificacion() {
		return fec_modificacion;
	}

	public void setFec_modificacion(Date fec_modificacion) {
		this.fec_modificacion = fec_modificacion;
	}

	public String getIp_modificacion() {
		return ip_modificacion;
	}

	public void setIp_modificacion(String ip_modificacion) {
		this.ip_modificacion = ip_modificacion;
	}

	public String getFec_ini() {
		return fec_ini;
	}

	public void setFec_ini(String fec_ini) {
		this.fec_ini = fec_ini;
	}

	public String getFec_fin() {
		return fec_fin;
	}

	public void setFec_fin(String fec_fin) {
		this.fec_fin = fec_fin;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public MultipartFile[] getFilesAnexos() {
		return filesAnexos;
	}

	public void setFilesAnexos(MultipartFile[] filesAnexos) {
		this.filesAnexos = filesAnexos;
	}

	public List<DocumentoAnexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<DocumentoAnexo> anexos) {
		this.anexos = anexos;
	}

	public int getCuenta_anexos() {
		return cuenta_anexos;
	}

	public void setCuenta_anexos(int cuenta_anexos) {
		this.cuenta_anexos = cuenta_anexos;
	}

	public String getGrupo_estados() {
		return grupo_estados;
	}

	public void setGrupo_estados(String grupo_estados) {
		this.grupo_estados = grupo_estados;
	}

	public String[] getCodigo_archivo_anexos() {
		return codigo_archivo_anexos;
	}

	public void setCodigo_archivo_anexos(String[] codigo_archivo_anexos) {
		this.codigo_archivo_anexos = codigo_archivo_anexos;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getNumero_sid() {
		return numero_sid;
	}

	public void setNumero_sid(String numero_sid) {
		this.numero_sid = numero_sid;
	}

	public long[] getCodigo_oficinas() {
		return codigo_oficinas;
	}

	public void setCodigo_oficinas(long[] codigo_oficinas) {
		this.codigo_oficinas = codigo_oficinas;
	}

	public String getHoja_ruta() {
		return hoja_ruta;
	}

	public void setHoja_ruta(String hoja_ruta) {
		this.hoja_ruta = hoja_ruta;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getDesc_tipo_documento() {
		return desc_tipo_documento;
	}

	public void setDesc_tipo_documento(String desc_tipo_documento) {
		this.desc_tipo_documento = desc_tipo_documento;
	}

	public String getDesc_oficina() {
		return desc_oficina;
	}

	public void setDesc_oficina(String desc_oficina) {
		this.desc_oficina = desc_oficina;
	}

	public pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] getOficinas() {
		return oficinas;
	}
	

	public void setOficinas(pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] oficinas) {
		this.oficinas = oficinas;
	}

	public String getNombre_persona() {
		return nombre_persona;
	}

	public void setNombre_persona(String nombre_persona) {
		this.nombre_persona = nombre_persona;
	}
/*
	public int getTipo_consulta() {
		return tipo_consulta;
	}

	public void setTipo_consulta(int tipo_consulta) {
		this.tipo_consulta = tipo_consulta;
	}
*/

	public String getId_persona() {
		return id_persona;
	}

	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}

	public String getDesc_estado_documento() {
		return desc_estado_documento;
	}

	public void setDesc_estado_documento(String desc_estado_documento) {
		this.desc_estado_documento = desc_estado_documento;
	}

	public int getNumpag() {
		return numpag;
	}

	public void setNumpag(int numpag) {
		this.numpag = numpag;
	}

	public int getNumreg() {
		return numreg;
	}

	public void setNumreg(int numreg) {
		this.numreg = numreg;
	}

	public int getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(int totalreg) {
		this.totalreg = totalreg;
	} 
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getFecha_anulacion() {
		return fecha_anulacion;
	}

	public void setFecha_anulacion(String fecha_anulacion) {
		this.fecha_anulacion = fecha_anulacion;
	}

	public String getFecha_observacion() {
		return fecha_observacion;
	}

	public void setFecha_observacion(String fecha_observacion) {
		this.fecha_observacion = fecha_observacion;
	}

	public String getFecha_subsanacion() {
		return fecha_subsanacion;
	}

	public void setFecha_subsanacion(String fecha_subsanacion) {
		this.fecha_subsanacion = fecha_subsanacion;
	}

	public String getUsu_asignacion() {
		return usu_asignacion;
	}

	public void setUsu_asignacion(String usu_asignacion) {
		this.usu_asignacion = usu_asignacion;
	}

	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public String getNomb_usu_asignacion() {
		return nomb_usu_asignacion;
	}

	public void setNomb_usu_asignacion(String nomb_usu_asignacion) {
		this.nomb_usu_asignacion = nomb_usu_asignacion;
	}
	
}
