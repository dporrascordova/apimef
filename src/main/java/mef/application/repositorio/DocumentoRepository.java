package mef.application.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import mef.application.modelo.entity.DocumentoEntity;

public interface DocumentoRepository extends JpaRepository<DocumentoEntity, Long> {

}
