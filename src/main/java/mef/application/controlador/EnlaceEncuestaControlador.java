package mef.application.controlador;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.EnlaceEncuesta;
import mef.application.service.DocumentoService;
import mef.application.service.EnlaceEncuestaService;

@RestController
@RequestMapping("/api/encuesta")
public class EnlaceEncuestaControlador {

	@Autowired
	EnlaceEncuestaService enlaceEncuestaService;

	@PostMapping("/crear")
	@Produces("application/json")
	public ResponseEntity<Auditoria> crearMensajeEncuesta(@Valid @RequestBody EnlaceEncuesta encuesta) {
		Auditoria auditoria = new Auditoria();

		try {
			encuesta.setIp_creacion(UserIdentityHelper.getClientIpAddress());
			encuesta.setUsu_creacion(UserIdentityHelper.get_CodigoUsuario());
			auditoria = enlaceEncuestaService.crearMensajeEncuesta(encuesta);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
	
	@GetMapping("/encuesta-activa")
	@Produces("application/json")
	public ResponseEntity<Auditoria> obtenerEncuestaActiva() {
		Auditoria auditoria = new Auditoria();

		try {
			auditoria = enlaceEncuestaService.obtenerEncuestaActiva();

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
}
