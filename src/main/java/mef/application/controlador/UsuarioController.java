
package mef.application.controlador;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mef.application.component.EmailComponent;
import mef.application.component.EmailUtil;
import mef.application.dto.Resource;
import mef.application.infrastructure.CommonHelpers;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.JwtResponse;
import mef.application.modelo.RecuperarClave;
import mef.application.modelo.Usuario;
import mef.application.modelo.UsuarioActivacion;
import mef.application.modelo.UsuarioEstado;
import mef.application.modelo.UsuarioInsertar;
import mef.application.modelo.UsuarioLogin;
import mef.application.modelo.UsuarioOlvidoClave;
import mef.application.modelo.UsuarioPersona;
import mef.application.security.JwtProvider;
import mef.application.service.FilesStorageService;
import mef.application.service.UsuarioService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UsuarioController {

	private EmailComponent emailComponent;

	private EmailUtil emailutil;

	@Value("${database.micorreo}")
	private String micorrreo;

	@Autowired
	JwtProvider jwtProvider;

	@Value("${file.fileserver}")
	private String fileServer;

	@Autowired
	UsuarioService userService;

	@Autowired
	FilesStorageService storageService;

	@Value("${google.recaptcha.key.secret}")
	private String secretKey;

	public UsuarioController(EmailComponent emailComponent) {
		this.emailComponent = emailComponent;
	}

	@PostMapping("/quieromiclave")
	public ResponseEntity<Auditoria> Usuario_RecuperarClave(@Valid @ModelAttribute RecuperarClave modelo,
			HttpServletRequest request) {
		Auditoria auditoria = new Auditoria();
		try {
			System.out.println("Recibo Captcha: " + modelo.getUCaptcha());
			System.out.println(request.getSession().getAttribute("info__captcha"));
			System.out.println(modelo.getUCaptcha());
				String	valor1=modelo.getUCaptcha();
				String valor2 =request.getSession().getAttribute("info__captcha").toString();
				System.out.println("valor 1: "+ valor1);
				System.out.println("valor 2: "+ valor2);
		//if (valor1.trim()==valor2.trim()) {
				if (valor1.equals(valor2)) {
				System.out.println("eentro condicional ");
				// if (CommonHelpers.Validar_Captcha(secretKey, modelo.getUCaptcha())) {
				auditoria.Limpiar();
				modelo.setUCodigo(UUID.randomUUID().toString());
				auditoria = userService.Usuario_RecuperarClave(modelo);
				if (auditoria.ejecucion_procedimiento) {
					if (!auditoria.rechazar) {
						try {
							Map<String, Object> params = new HashMap<>();
							System.out.println(auditoria.objeto.toString());
							params.put("nombre", "USUARIO");
							params.put("codigoclave", modelo.getUCodigo());
							params.put("imageResourceName", "logo.png");

							params.put("anio",  Calendar.getInstance().get(Calendar.YEAR));
							String Plantilla = "email/usuario_olvideclave";
							

							Resource resource = new Resource();
							Path path2 = Paths.get(fileServer, "logo.jpg");
							File file = path2.toFile();
							resource.setImageBytes(FileUtils.readFileToByteArray(file));

							// resource.setImageBytes(Files.readAllBytes(Paths.get(fileServer
							// ,"logo.jpg")));
							resource.setContentType("image/png");

							Map<String, Resource> resources = new HashMap<>();
							resources.put("logo.png", resource);

							String mensaje = emailComponent.getTemplate(Plantilla, params);

							emailutil = new EmailUtil();

							if (micorrreo.equals("L")) {
								emailComponent.sendHTML(modelo.getUCorreo(), "Recuperar Clave", "-", Plantilla, params,
										resources);
							} else {
								emailutil.sendHTML(modelo.getUCorreo(), "Recuperar Clave", "-", Plantilla, params,
										resources);

							}
						} catch (Exception e) {
							auditoria.Error(e);
							//e.printStackTrace();
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
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/verificamicodigo")
	public ResponseEntity<Auditoria> Usuario_VerificaCodigo(@Valid @ModelAttribute RecuperarClave modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = userService.Usuario_VerificaCodigo(modelo);

			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {
					String jwt = jwtProvider.generateJwtToken(((UsuarioPersona) auditoria.objeto));
					auditoria.objeto = new JwtResponse(jwt);
				}
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/registrausuariointerno")
	public ResponseEntity<Auditoria> Usuario_Insertar(@Valid @ModelAttribute UsuarioInsertar modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			modelo.setUsu_creacion(new UserIdentityHelper().get_CodigoUsuario());
			modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
			auditoria = userService.Usuario_Insertar(modelo);

			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {

					Map<String, Object> params = new HashMap<>();
					params.put("nombre",
							modelo.getNombre().toUpperCase() + " " + modelo.getApellido_paterno().toUpperCase() + " "
									+ modelo.getApellido_materno().toUpperCase());
					params.put("tipoper", "usuario de mesa de partes interno");
					params.put("codigousuario", modelo.getCod_usuario());
					params.put("codigoclave", auditoria.objeto.toString());
					params.put("imageResourceName", "logo.png");
					params.put("anio",  Calendar.getInstance().get(Calendar.YEAR));

					Resource resource = new Resource();

					Path path = Paths.get(fileServer, "logo.jpg");
					File file = path.toFile();
					resource.setImageBytes(FileUtils.readFileToByteArray(file));
					resource.setContentType("image/png");

					Map<String, Resource> resources = new HashMap<>();
					resources.put("logo.png", resource);
					emailutil = new EmailUtil();
					if (micorrreo.equals("L")) {
						emailComponent.sendHTML(modelo.getCorreo(), "Usuario registrado", "-",
								"email/usuariointerno_crear", params, resources);
					} else {
						emailutil.sendHTML(modelo.getCorreo(), "Usuario registrado", "-", "email/usuariointerno_crear",
								params, resources);
					}
					auditoria.Limpiar();
				}
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/actualizamiclave")
	public ResponseEntity<Auditoria> Usuario_ActualizaClave(@Valid @ModelAttribute UsuarioLogin modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			modelo.setId_usuario(new UserIdentityHelper().getUserId());
			auditoria = userService.Usuario_ActualizaClave(modelo);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/verificamiclave")
	public ResponseEntity<Auditoria> Usuario_VerificaModificaClave() {
		Auditoria auditoria = new Auditoria();
		try {
			UsuarioOlvidoClave modelo = new UsuarioOlvidoClave();
			modelo.setId_usuario(new UserIdentityHelper().getUserId());
			auditoria = userService.Usuario_VerificaModificaClave(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/mismodulos")
	public ResponseEntity<Auditoria> Usuario_Modulos() {
		Auditoria auditoria = new Auditoria();
		try {
			int PI_ID_USUARIO = new UserIdentityHelper().getUserId();
			auditoria = userService.Usuario_Modulos(PI_ID_USUARIO);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/estadousuario")
	public ResponseEntity<Auditoria> Usuario_Estado(@Valid @ModelAttribute UsuarioEstado modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			modelo.setUsu_modificacion(new UserIdentityHelper().get_CodigoUsuario());
			modelo.setIp_modificacion(UserIdentityHelper.getClientIpAddress());
			auditoria = userService.Usuario_Estado(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/eliminausuario")
	public ResponseEntity<Auditoria> Usuario_Eliminar(@Valid @ModelAttribute UsuarioEstado modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			modelo.setUsu_modificacion(new UserIdentityHelper().get_CodigoUsuario());
			modelo.setIp_modificacion(UserIdentityHelper.getClientIpAddress());
			auditoria = userService.Usuario_Eliminar(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/activausuario")
	public ResponseEntity<Auditoria> Usuario_Activar(@Valid @ModelAttribute UsuarioActivacion modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			// if(..valueOf(modelo.getFec_activacion()).)
			modelo.setUsu_modificacion(new UserIdentityHelper().get_CodigoUsuario());
			modelo.setIp_modificacion(UserIdentityHelper.getClientIpAddress());
			auditoria = userService.Usuario_Activar(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	
	
	@PostMapping("/count_alerta")
	public ResponseEntity<Auditoria> Usuario_Noti_Count() {
		Auditoria auditoria = new Auditoria();
		try {
			int PI_ID_USUARIO = new UserIdentityHelper().getUserId();
			auditoria = userService.Usuario_Noti_Count(PI_ID_USUARIO);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	
}
