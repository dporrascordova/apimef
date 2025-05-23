package mef.application.modelo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_VUDM_DOCUMENTO", schema = "SISVENVI")
public class DocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento_seq")
    @SequenceGenerator(name = "documento_seq", sequenceName = "SISVENVI.SEC_ID_DOCUMENTO", allocationSize = 1)
    @Column(name = "ID_DOCUMENTO", nullable = false)
    private Long idDocumento;

    @Column(name = "ASUNTO", length = 4000)
    private String asunto;

    @Column(name = "CODIGO_ARCHIVO", length = 255)
    private String codigoArchivo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_CREACION")
    private Date fecCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_MODIFICACION")
    private Date fecModificacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_RECIBIDO")
    private Date fecRecibido;

    @Column(name = "FLG_ESTADO", length = 255)
    private String flgEstado;

    @Column(name = "ID_ESTADO_DOCUMENTO", nullable = false)
    private Long idEstadoDocumento;

    @Column(name = "ID_OFICINA", nullable = false)
    private Long idOficina;

    @Column(name = "ID_TIPO_DOCUMENTO", length = 255)
    private String idTipoDocumento;

    @Column(name = "ID_TIPO_USUARIO", length = 255)
    private String idTipoUsuario;

    @Column(name = "ID_USUARIO", length = 255)
    private String idUsuario;

    @Column(name = "IP_CREACION", length = 255)
    private String ipCreacion;

    @Column(name = "IP_MODIFICACION", length = 255)
    private String ipModificacion;

    @Column(name = "NRO_DOCUMENTO", length = 255)
    private String nroDocumento;

    @Column(name = "NRO_FOLIOS", nullable = false)
    private Long nroFolios;

    @Column(name = "USU_CREACION", length = 255)
    private String usuCreacion;

    @Column(name = "USU_MODIFICACION", length = 255)
    private String usuModificacion;

    @Column(name = "NUMERO_SID", length = 100)
    private String numeroSid;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "HOJA_RUTA_ANEXO", length = 100)
    private String hojaRutaAnexo;

    @Column(name = "HOJA_RUTA", length = 255)
    private String hojaRuta;

    @Column(name = "DESC_OFICINA", length = 255)
    private String descOficina;

    @Column(name = "DESC_TIPO_DOCUMENTO", length = 255)
    private String descTipoDocumento;

    @Column(name = "OBSERVACION", length = 255)
    private String observacion;

    @Column(name = "ID_PRIORIDAD")
    private Long idPrioridad;

    @Column(name = "CONGRESISTA", length = 300)
    private String congresista;

    @Column(name = "USU_ASIGNADO", length = 255)
    private String usuAsignado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_RECIBIDO_SGDD")
    private Date fecRecibidoSgdd;

    @Column(name = "FLG_ERRORSERVICIO")
    private Long flgErrorServicio;

    @Column(name = "DESC_ERRORSERVICIO", length = 500)
    private String descErrorServicio;

    @Column(name = "OBS_SGDD", length = 200)
    private String obsSgdd;

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCodigoArchivo() {
        return codigoArchivo;
    }

    public void setCodigoArchivo(String codigoArchivo) {
        this.codigoArchivo = codigoArchivo;
    }

    public Date getFecCreacion() {
        return fecCreacion;
    }

    public void setFecCreacion(Date fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    public Date getFecModificacion() {
        return fecModificacion;
    }

    public void setFecModificacion(Date fecModificacion) {
        this.fecModificacion = fecModificacion;
    }

    public Date getFecRecibido() {
        return fecRecibido;
    }

    public void setFecRecibido(Date fecRecibido) {
        this.fecRecibido = fecRecibido;
    }

    public String getFlgEstado() {
        return flgEstado;
    }

    public void setFlgEstado(String flgEstado) {
        this.flgEstado = flgEstado;
    }

    public Long getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    public void setIdEstadoDocumento(Long idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    public Long getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(Long idOficina) {
        this.idOficina = idOficina;
    }

    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(String idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIpCreacion() {
        return ipCreacion;
    }

    public void setIpCreacion(String ipCreacion) {
        this.ipCreacion = ipCreacion;
    }

    public String getIpModificacion() {
        return ipModificacion;
    }

    public void setIpModificacion(String ipModificacion) {
        this.ipModificacion = ipModificacion;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Long getNroFolios() {
        return nroFolios;
    }

    public void setNroFolios(Long nroFolios) {
        this.nroFolios = nroFolios;
    }

    public String getUsuCreacion() {
        return usuCreacion;
    }

    public void setUsuCreacion(String usuCreacion) {
        this.usuCreacion = usuCreacion;
    }

    public String getUsuModificacion() {
        return usuModificacion;
    }

    public void setUsuModificacion(String usuModificacion) {
        this.usuModificacion = usuModificacion;
    }

    public String getNumeroSid() {
        return numeroSid;
    }

    public void setNumeroSid(String numeroSid) {
        this.numeroSid = numeroSid;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getHojaRutaAnexo() {
        return hojaRutaAnexo;
    }

    public void setHojaRutaAnexo(String hojaRutaAnexo) {
        this.hojaRutaAnexo = hojaRutaAnexo;
    }

    public String getHojaRuta() {
        return hojaRuta;
    }

    public void setHojaRuta(String hojaRuta) {
        this.hojaRuta = hojaRuta;
    }

    public String getDescOficina() {
        return descOficina;
    }

    public void setDescOficina(String descOficina) {
        this.descOficina = descOficina;
    }

    public String getDescTipoDocumento() {
        return descTipoDocumento;
    }

    public void setDescTipoDocumento(String descTipoDocumento) {
        this.descTipoDocumento = descTipoDocumento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getCongresista() {
        return congresista;
    }

    public void setCongresista(String congresista) {
        this.congresista = congresista;
    }

    public String getUsuAsignado() {
        return usuAsignado;
    }

    public void setUsuAsignado(String usuAsignado) {
        this.usuAsignado = usuAsignado;
    }

    public Date getFecRecibidoSgdd() {
        return fecRecibidoSgdd;
    }

    public void setFecRecibidoSgdd(Date fecRecibidoSgdd) {
        this.fecRecibidoSgdd = fecRecibidoSgdd;
    }

    public Long getFlgErrorServicio() {
        return flgErrorServicio;
    }

    public void setFlgErrorServicio(Long flgErrorServicio) {
        this.flgErrorServicio = flgErrorServicio;
    }

    public String getDescErrorServicio() {
        return descErrorServicio;
    }

    public void setDescErrorServicio(String descErrorServicio) {
        this.descErrorServicio = descErrorServicio;
    }

    public String getObsSgdd() {
        return obsSgdd;
    }

    public void setObsSgdd(String obsSgdd) {
        this.obsSgdd = obsSgdd;
    }

}
