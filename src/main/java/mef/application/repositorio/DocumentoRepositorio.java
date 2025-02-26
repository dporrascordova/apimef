/*package mef.application.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mef.application.modelo.Documento;

@Repository
public interface DocumentoRepositorio extends JpaRepository<Documento, Integer>{
	
	@Query(value="select * from T_VUDM_DOCUMENTO d where d.id_estado_documento like %?1% and d.codigo_archivo like %?2% "
			+ " and d.id_oficina like %?3% and d.id_tipo_documento like %?4% and d.nro_documento like %?5% and (d.fec_recibido between ?6 and ?7 ) ", nativeQuery=true)
	public List<Documento> getDocumentos(@Param("id_estado_documento") String id_estado_documento, @Param("codigo_archivo") String codigo_archivo, @Param("id_oficina") String id_oficina,
			@Param("id_tipo_documento") String id_tipo_documento, @Param("nro_documento") String nro_documento, @Param("fec_recibido") Date fec_recibido, @Param("fec_recibido_fin") Date fec_recibido_fin);
	
}
*/