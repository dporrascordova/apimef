package mef.application.modelo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DocumentoAnexoPK implements Serializable {
  @Column(name = "ID_DOCUMENTO")
  private Long idDocumento;

  @Column(name = "ORDEN")
  private Long orden;

  // Getters, setters, equals y hashCode
  public Long getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  public Long getOrden() {
    return orden;
  }

  public void setOrden(Long orden) {
    this.orden = orden;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DocumentoAnexoPK that = (DocumentoAnexoPK) o;
    return Objects.equals(idDocumento, that.idDocumento) &&
        Objects.equals(orden, that.orden);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDocumento, orden);
  }
}
