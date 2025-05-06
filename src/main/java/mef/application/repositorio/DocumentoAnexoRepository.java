package mef.application.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mef.application.modelo.entity.DocumentoAnexoEntity;
import mef.application.modelo.entity.DocumentoAnexoPK;
import org.springframework.transaction.annotation.Transactional;

public interface DocumentoAnexoRepository extends JpaRepository<DocumentoAnexoEntity, DocumentoAnexoPK> {

  Optional<DocumentoAnexoEntity> findById(DocumentoAnexoPK id);

  @Query(value = "SELECT COALESCE(MAX(da.id.orden), 0) + 1 " +
      "FROM DocumentoAnexoEntity da " +
      "WHERE da.id.idDocumento = :idDocumento")
  Long obtenerSiguienteOrden(@Param("idDocumento") Long idDocumento);


  // Versión alternativa con SQL nativo
  @Modifying
  @Transactional
  @Query(value = "UPDATE T_VUDM_DOCUMENTO_ANEXO SET ID_ANEXO = :idAnexo WHERE ID_DOCUMENTO = :idDocumento", nativeQuery = true)
  int actualizarIdAnexoNative(
          @Param("idDocumento") Long idDocumento,
          @Param("idAnexo") String idAnexo
  );
}
