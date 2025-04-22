package mef.application.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import mef.application.modelo.OficinaEntity;

public interface OficinaRepository extends JpaRepository<OficinaEntity, Long> {
    @Modifying
    @Query("UPDATE OficinaEntity o SET o.flgEstado = '0'")
    void marcarTodosComoInactivos();
}
