package mef.application.modelo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "T_VUDM_DOCUMENTO_ANEXO")
public class DocumentoAnexoEntity {

  @EmbeddedId
  private DocumentoAnexoPK id;

  @Column(name = "CODIGO_ARCHIVO", length = 255)
  private String codigoArchivo;

  @Column(name = "NOMBRE_ARCHIVO", length = 255)
  private String nombreArchivo;

  @Column(name = "EXTENSION_ARCHIVO", length = 255)
  private String extensionArchivo;

  @Column(name = "TAMANIO_ARCHIVO")
  private Long tamanioArchivo;

  @Column(name = "FEC_CREACION")
  private Timestamp fechaCreacion;

  @Column(name = "FEC_MODIFICACION")
  private Timestamp fechaModificacion;

  @Column(name = "USU_CREACION", length = 255)
  private String usuarioCreacion;

  @Column(name = "USU_MODIFICACION", length = 255)
  private String usuarioModificacion;

  @Column(name = "ESTADO", length = 255)
  private String estado;

  @Column(name = "MIMETYPE_ARCHIVO", length = 255)
  private String mimeTypeArchivo;

  @Column(name = "FLG_LINK", length = 255)
  private String flagLink;

  @Column(name = "FLG_CREA_MPI", length = 255)
  private String flagCreaMPI;

  @Column(name = "ESTADO_ANEXO")
  private Integer estadoAnexo;

  @Column(name = "ID_ANEXO", length = 255)
  private String idAnexo;

  @PrePersist
  protected void onCreate() {
    Timestamp now = Timestamp.from(java.time.Instant.now());
    this.fechaCreacion = now;

  }

  @PreUpdate
  protected void onUpdate() {
    this.fechaModificacion = Timestamp.from(java.time.Instant.now());

  }

  public String getCodigoArchivo() {
    return codigoArchivo;
  }

  public void setCodigoArchivo(String codigoArchivo) {
    this.codigoArchivo = codigoArchivo;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getExtensionArchivo() {
    return extensionArchivo;
  }

  public void setExtensionArchivo(String extensionArchivo) {
    this.extensionArchivo = extensionArchivo;
  }

  public Long getTamanioArchivo() {
    return tamanioArchivo;
  }

  public void setTamanioArchivo(Long tamanioArchivo) {
    this.tamanioArchivo = tamanioArchivo;
  }

  public Timestamp getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Timestamp fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Timestamp getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Timestamp fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getMimeTypeArchivo() {
    return mimeTypeArchivo;
  }

  public void setMimeTypeArchivo(String mimeTypeArchivo) {
    this.mimeTypeArchivo = mimeTypeArchivo;
  }

  public String getFlagLink() {
    return flagLink;
  }

  public void setFlagLink(String flagLink) {
    this.flagLink = flagLink;
  }

  public String getFlagCreaMPI() {
    return flagCreaMPI;
  }

  public void setFlagCreaMPI(String flagCreaMPI) {
    this.flagCreaMPI = flagCreaMPI;
  }

  public Integer getEstadoAnexo() {
    return estadoAnexo;
  }

  public void setEstadoAnexo(Integer estadoAnexo) {
    this.estadoAnexo = estadoAnexo;
  }

  public String getIdAnexo() {
    return idAnexo;
  }

  public void setIdAnexo(String idAnexo) {
    this.idAnexo = idAnexo;
  }

  public DocumentoAnexoPK getId() {
    return id;
  }

  public void setId(DocumentoAnexoPK id) {
    this.id = id;
  }
}
