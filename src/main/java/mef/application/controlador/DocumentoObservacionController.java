package mef.application.controlador;
 
import javax.validation.Valid; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
 
import mef.application.modelo.Auditoria; 
import mef.application.modelo.DocumentoObservacion; 
import mef.application.service.DocumentoObservacionService; 
 
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DocumentoObservacionController { 
	
	@Autowired
	DocumentoObservacionService documentoObservacionService; 
	
	@PostMapping("/documentoobs")
	public ResponseEntity<Auditoria> DocumentoObservacion_Listar(@Valid @ModelAttribute DocumentoObservacion modelo) {
		Auditoria auditoria = new Auditoria();
		try { 
			auditoria = documentoObservacionService.DocumentoObservacion_Listar(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
	
	 

}
