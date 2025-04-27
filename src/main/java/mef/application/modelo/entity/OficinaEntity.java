package mef.application.modelo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_VENL_OFICINA")
public class OficinaEntity {

    @Id
    @Column(name = "IDUNIDAD")
    private Long idUnidad;

    @Column(name = "ACRONIMO")
    private String acronimo;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "CONJEFE")
    private String conJefe;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "DESCRIPCIONCOMPLETA")
    private String descripcionCompleta;

    @Column(name = "FLAGOFGENERAL")
    private Integer flagOfGeneral;

    @Column(name = "JEFE")
    private String jefe;

    @Column(name = "FLG_ESTADO")
    private String flgEstado;

    @Column(name = "USU_CREACION")
    private String usuCreacion;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fecCreacion;

    @Column(name = "IP_CREACION")
    private String ipCreacion;

    @Column(name = "USU_MODIFICACION")
    private String usuModificacion;

    @Column(name = "FEC_MODIFICACION")
    private LocalDateTime fecModificacion;

    @Column(name = "IP_MODIFICACION")
    private String ipModificacion;

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getConJefe() {
        return conJefe;
    }

    public void setConJefe(String conJefe) {
        this.conJefe = conJefe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCompleta() {
        return descripcionCompleta;
    }

    public void setDescripcionCompleta(String descripcionCompleta) {
        this.descripcionCompleta = descripcionCompleta;
    }

    public Integer getFlagOfGeneral() {
        return flagOfGeneral;
    }

    public void setFlagOfGeneral(Integer flagOfGeneral) {
        this.flagOfGeneral = flagOfGeneral;
    }

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    public String getFlgEstado() {
        return flgEstado;
    }

    public void setFlgEstado(String flgEstado) {
        this.flgEstado = flgEstado;
    }

    public String getUsuCreacion() {
        return usuCreacion;
    }

    public void setUsuCreacion(String usuCreacion) {
        this.usuCreacion = usuCreacion;
    }

    public LocalDateTime getFecCreacion() {
        return fecCreacion;
    }

    public void setFecCreacion(LocalDateTime fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    public String getIpCreacion() {
        return ipCreacion;
    }

    public void setIpCreacion(String ipCreacion) {
        this.ipCreacion = ipCreacion;
    }

    public String getUsuModificacion() {
        return usuModificacion;
    }

    public void setUsuModificacion(String usuModificacion) {
        this.usuModificacion = usuModificacion;
    }

    public LocalDateTime getFecModificacion() {
        return fecModificacion;
    }

    public void setFecModificacion(LocalDateTime fecModificacion) {
        this.fecModificacion = fecModificacion;
    }

    public String getIpModificacion() {
        return ipModificacion;
    }

    public void setIpModificacion(String ipModificacion) {
        this.ipModificacion = ipModificacion;
    }
}
