package mef.application.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mef.application.modelo.entity.OficinaEntity;
import mef.application.repositorio.OficinaRepository;
import pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto;

@Service
public class OficinaServiceImpl {

    @Autowired
    private OficinaRepository oficinaRepository;

    @Transactional
    public void actualizarOficinasDesdeServicio(UnidadesOrganicasTreeDto[] oficinasDelServicio)
            throws UnknownHostException {
        // Parámetros de auditoría comunes
        String ipLocal = InetAddress.getLocalHost().toString();
        String usuario = "JOB SGDD";
        LocalDateTime ahora = LocalDateTime.now();

        // Paso 1: Desactivar todas las oficinas (se mantiene en la misma transacción)
        oficinaRepository.marcarTodosComoInactivos();

        // Paso 2: Consolidar posibles duplicados (por si el servicio devuelve registros
        // repetidos)
        Map<Long, UnidadesOrganicasTreeDto> oficinasUnicas = new HashMap<>();
        for (UnidadesOrganicasTreeDto dto : oficinasDelServicio) {
            oficinasUnicas.put(dto.getIdunidad(), dto);
        }

        // Paso 3: Insertar o actualizar cada oficina consolidada
        for (UnidadesOrganicasTreeDto dto : oficinasUnicas.values()) {
            // Se busca la entidad existente
            OficinaEntity oficina = oficinaRepository.findById(dto.getIdunidad())
                    .orElse(new OficinaEntity());

            // Determinar si es entidad nueva (sin auditoría de creación)
            boolean esNuevo = oficina.getUsuCreacion() == null;
            boolean hasChanged = false; // Flag que indica si se ha modificado alguno de los campos

            // Actualizar los campos solo si cambian
            // ACRONIMO
            String newAcronimo = valorOrDefault(dto.getAcronimo());
            if (!Objects.equals(newAcronimo, oficina.getAcronimo())) {
                oficina.setAcronimo(newAcronimo);
                hasChanged = true;
            }

            // CODIGO
            String newCodigo = valorOrDefault(dto.getCodigo());
            if (!Objects.equals(newCodigo, oficina.getCodigo())) {
                oficina.setCodigo(newCodigo);
                hasChanged = true;
            }

            // CONJEFE (se transforma a "1" o "0")
            String newConJefe = dto.isConjefe() ? "1" : "0";
            if (!Objects.equals(newConJefe, oficina.getConJefe())) {
                oficina.setConJefe(newConJefe);
                hasChanged = true;
            }

            // DESCRIPCION
            String newDescripcion = valorOrDefault(dto.getDescripcion());
            if (!Objects.equals(newDescripcion, oficina.getDescripcion())) {
                oficina.setDescripcion(newDescripcion);
                hasChanged = true;
            }

            // DESCRIPCIONCOMPLETA
            String newDescripcionCompleta = valorOrDefault(dto.getDescripcionCompleta());
            if (!Objects.equals(newDescripcionCompleta, oficina.getDescripcionCompleta())) {
                oficina.setDescripcionCompleta(newDescripcionCompleta);
                hasChanged = true;
            }

            // FLAGOFGENERAL
            Integer newFlagOfGeneral = dto.getFlagofgeneral() != null ? dto.getFlagofgeneral() : 0;
            if (!Objects.equals(newFlagOfGeneral, oficina.getFlagOfGeneral())) {
                oficina.setFlagOfGeneral(newFlagOfGeneral);
                hasChanged = true;
            }

            // JEFE
            String newJefe = valorOrDefault(dto.getJefe());
            if (!Objects.equals(newJefe, oficina.getJefe())) {
                oficina.setJefe(newJefe);
                hasChanged = true;
            }

            // FLG_ESTADO lo seteamos siempre a "1" para los registros que vienen del
            // servicio
            // if (!"1".equals(oficina.getFlgEstado())) {
            // oficina.setFlgEstado("1");
            // hasChanged = true;
            // }
            oficina.setFlgEstado("1");

            // Si es entidad nueva, se establece su identificador y los campos de creación
            if (esNuevo) {
                oficina.setIdUnidad(dto.getIdunidad());
                oficina.setUsuCreacion(usuario);
                oficina.setFecCreacion(ahora);
                oficina.setIpCreacion(ipLocal);
                // Además, se establecen los campos de modificación
                oficina.setUsuModificacion(usuario);
                oficina.setFecModificacion(ahora);
                oficina.setIpModificacion(ipLocal);
            } else if (hasChanged) {
                // Si la entidad existe y hubo algún cambio, se actualizan los campos de
                // modificación
                oficina.setUsuModificacion(usuario);
                oficina.setFecModificacion(ahora);
                oficina.setIpModificacion(ipLocal);
            }
            // Si no hubo cambios, se deja la fecha de modificación intacta

            oficinaRepository.save(oficina);
        }
    }

    /**
     * Retorna "-" si el valor es nulo o vacío, de lo contrario retorna el mismo
     * valor.
     */
    private String valorOrDefault(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? "-" : valor;
    }
}
