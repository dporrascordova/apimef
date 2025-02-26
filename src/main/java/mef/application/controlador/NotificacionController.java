package mef.application.controlador;

import java.io.ByteArrayInputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.NotificacionGrilla; 
import mef.application.service.NotificacionService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class NotificacionController {
	

	@Autowired
	NotificacionService notificacionService;
	
	@PostMapping("/not_porenviar")
	public ResponseEntity<Auditoria> Notificacion_Enviar_Listar(@Valid @RequestBody NotificacionGrilla modelo) {
		Auditoria auditoria = new Auditoria(); 
		try {
			auditoria = notificacionService.Notificacion_Enviar_Listar(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
	
	@PostMapping("/not_enviado")
	public ResponseEntity<Auditoria> Notificacion_Enviado_Listar(@Valid @RequestBody NotificacionGrilla modelo) {
		Auditoria auditoria = new Auditoria(); 
		try {
			auditoria = notificacionService.Notificacion_Enviado_Paginado(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
	  
	@PostMapping("/reportenotificaciones")
	public ResponseEntity<InputStreamResource> Notificacion_Exportar(@Valid @ModelAttribute NotificacionGrilla modelo) {
		ByteArrayInputStream stream = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Reportes.xlsx");
		headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		try {
			stream = notificacionService.Notificacion_Exportar(modelo);
			stream.close();
			System.out.println("ENTRO XD 6");
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
}
