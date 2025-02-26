package mef.application.controlador;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import mef.application.modelo.Auditoria; 
import mef.application.service.SeguridadService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SeguridadController {

	@Autowired
	SeguridadService seguridadService;
	
	@PostMapping("/seg_perfil")
	public ResponseEntity<Auditoria> Perfil_Listar() {
		Auditoria auditoria = new Auditoria();
		try { 
			auditoria = seguridadService.Perfil_Listar();

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
	
	@PostMapping("/seg_personal")
	public ResponseEntity<Auditoria> Personal_Listar() {
		Auditoria auditoria = new Auditoria();
		try { 
			auditoria = seguridadService.Personal_Listar();

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

}
