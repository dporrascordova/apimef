package mef.application.controlador;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
//import mef.application.consumingws.UsuarioClient;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Auth;
import mef.application.modelo.JwtResponse;
//import mef.application.modelo.RespuestaMessage;
import mef.application.modelo.Usuario;
import mef.application.modelo.UsuarioLogin;
import mef.application.modelo.UsuarioPersona;
import mef.application.security.JwtProvider;
import mef.application.service.UsuarioService;
//import pe.gob.mef.std.bs.web.ws.*;
//import javax.validation.Valid;

//@CrossOrigin(origins = "", allowedHeaders = "")
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuService;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/login")
	public ResponseEntity<Auditoria> Usuario_Validar(@Valid @RequestBody Auth usuario, HttpServletRequest request) {
		Auditoria auditoria = new Auditoria();
		HttpHeaders headers = new HttpHeaders();

		try {
			System.out.println(request.getSession().getAttribute("info__captcha"));
			String capt = "";
			if (request.getSession().getAttribute("info__captcha") != null) {
				capt = request.getSession().getAttribute("info__captcha").toString();
			}
			if (usuario.getCaptcha().equals(capt)) {
				UsuarioLogin modelo = new UsuarioLogin();
				modelo.setCod_usuario(usuario.getUser());
				modelo.setClave_usuario(usuario.getPassword());
				auditoria = usuService.Usuario_Validar(modelo);

				if (auditoria.ejecucion_procedimiento) {
					if (!auditoria.rechazar) {
						if (((UsuarioPersona) auditoria.objeto).getId_usuario() != 0) {
							String jwt = jwtProvider.generateJwtToken(((UsuarioPersona) auditoria.objeto));
							Cookie cookie = new Cookie("token", jwt);
							cookie.setPath("/");
							cookie.setSecure(true);
							cookie.setHttpOnly(true);
							cookie.setMaxAge(10); // 1 dia
							headers.add("Set-Cookie", cookie.toString());
							headers.setAccessControlAllowCredentials(true);
							headers.setExpires(10);
							auditoria.objeto = ResponseEntity.ok(new JwtResponse(jwt));
							return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
						}
					}
				}
			} else {
				auditoria.ejecucion_procedimiento = false;
				auditoria.mensaje_salida = "El captcha es invalido";
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
		}
		return new ResponseEntity<Auditoria>(auditoria, headers, HttpStatus.OK);
	}

}