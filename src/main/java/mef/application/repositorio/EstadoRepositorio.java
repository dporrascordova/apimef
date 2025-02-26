package mef.application.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mef.application.modelo.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer>{

}
