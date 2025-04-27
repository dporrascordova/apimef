package mef.application.repositorio;

import java.util.Optional;

import mef.application.modelo.entity.DocumentoAnexoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mef.application.modelo.entity.DocumentoAnexoEntity;

public interface DocumentoAnexoRepository extends JpaRepository<DocumentoAnexoEntity, Long> {

  Optional<DocumentoAnexoEntity> findById(DocumentoAnexoPK id);
}
