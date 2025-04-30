package mef.application.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mef.application.modelo.entity.DocumentoAnexoEntity;
import mef.application.modelo.entity.DocumentoAnexoPK;

public interface DocumentoAnexoRepository extends JpaRepository<DocumentoAnexoEntity, DocumentoAnexoPK> {

  Optional<DocumentoAnexoEntity> findById(DocumentoAnexoPK id);

  @Query(value = "SELECT COALESCE(MAX(da.id.orden), 0) + 1 " +
      "FROM DocumentoAnexoEntity da " +
      "WHERE da.id.idDocumento = :idDocumento")
  Long obtenerSiguienteOrden(@Param("idDocumento") Long idDocumento);
}
