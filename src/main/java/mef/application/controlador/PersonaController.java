
package mef.application.controlador;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
/*
import java.util.ArrayList;
import java.util.List;*/
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;
/*
import org.json.JSONArray;
import org.json.JSONObject;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mef.application.component.EmailComponent;
import mef.application.component.EmailUtil;
import mef.application.dto.Resource;
import mef.application.infrastructure.CommonHelpers;
import mef.application.infrastructure.UbigeoHelpers;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.PersonaJuridica;
import mef.application.modelo.PersonaNatural;
import mef.application.modelo.PersonaUsuario;
import mef.application.modelo.PersonaValida;
import mef.application.modelo.UsuarioPersonaGrilla;
import mef.application.modelo.UsuarioPersonaGrillaLibre;
import mef.application.service.CasillaService;
import mef.application.service.FilesStorageService;
import mef.application.service.PersonaService;
import pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto;
import pe.gob.mef.std.bs.web.ws.VentanillastdProxy;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PersonaController {

	private EmailComponent emailComponent;

	private EmailUtil emailutil;

	@Value("${file.fileserver}")
	private String fileServer;

	@Value("${database.micorreo}")
	private String micorrreo;

	@Autowired
	PersonaService personaService;

	@Autowired
	FilesStorageService storageService;

	@Autowired
	CasillaService casillaService;

	@Value("${google.recaptcha.key.secret}")
	private String secretKey;

	private final VentanillastdProxy proxy;

	public PersonaController(EmailComponent emailComponent, VentanillastdProxy ventanillastdProxy) {
		this.emailComponent = emailComponent;
		this.proxy = ventanillastdProxy;
	}

	@PostMapping("/crearpersonanatural")
	@Consumes("multipart/form-data;charset=UTF-8")
	public ResponseEntity<Auditoria> PersonaNatural_Insertar(@Valid @ModelAttribute PersonaNatural persona,
			HttpServletRequest request) {
		Auditoria auditoria = new Auditoria();
		try {

			String capt = "";
			if (request.getSession().getAttribute("info__captcha") != null) {
				capt = request.getSession().getAttribute("info__captcha").toString();
			}

			if (persona.getCaptcha().equals(capt)) {

				if (persona.getTipodoc() != 1) {
					persona.setIddepartamento("");
					persona.setIdprovincia("");
					persona.setIddistrito("");
					persona.setCodigoubigeo("");
					persona.setCodigoverificadni("");
				}
				auditoria.Limpiar();

				String Nombre_Archivo = "-";
				if (!Objects.isNull(persona.getMifile()))
					Nombre_Archivo = CommonHelpers.Generar_Nombre_Archivo(persona.getMifile());

				persona.setCodigoarchivo(Nombre_Archivo);
				persona.setIp_creacion(UserIdentityHelper.getClientIpAddress());
				auditoria = personaService.PersonaNatural_Insertar(persona);

				if (auditoria.ejecucion_procedimiento) {
					if (!auditoria.rechazar) {
						int ID_PERSONA = (int) auditoria.objeto;
						PersonaNatural moll = new PersonaNatural();
						moll.setIdpersona(ID_PERSONA);
						Auditoria auditoriax = personaService.PersonaNatural_ListarUno(moll, 0);// rUno(ID_PERSONA,0);

						PersonaNatural entidad = (PersonaNatural) auditoriax.objeto;

						if (!Objects.isNull(persona.getMifile()))
							storageService.save(fileServer, persona.getMifile(), Nombre_Archivo);

						Map<String, Object> params = new HashMap<>();
						params.put("nombre",
								persona.getNombres().toUpperCase() + " " + persona.getApellidopaterno().toUpperCase()
										+ " " + persona.getApellidomaterno().toUpperCase());
						params.put("nro_documento", persona.getNrodocumento());
						params.put("tipoper", "natural");
						params.put("tipodoc", "DNI");
						// params.put("tipodoc", persona.getDesctipodoc());
						// params.put("tipoper", persona.getTipopersona());

						params.put("imageResourceName", "logo.png");
						params.put("anio", Calendar.getInstance().get(Calendar.YEAR));

						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						String mensaje = formatter.format(date);
						params.put("mensaje", entidad.getFec_creacion());

						Map<String, Resource> resources = new HashMap<>();
						Resource resource = new Resource();

						Path path = Paths.get(fileServer, "logo.jpg");
						File file = path.toFile();
						resource.setImageBytes(FileUtils.readFileToByteArray(file));
						resource.setContentType("image/png");
						resources.put("logo.png", resource);

						emailutil = new EmailUtil();
						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(persona.getCorreo(), "Persona registrada", "-",
									"email/persona_crear", params, resources);
						} else {
							emailutil.sendHTML(persona.getCorreo(), "Persona registrada", "-", "email/persona_crear",
									params, resources);
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

	@PostMapping("/crearpersonajuridica")
	@Consumes("multipart/form-data;charset=UTF-8")
	public ResponseEntity<Auditoria> PersonaJuridica_Insertar(@Valid @ModelAttribute PersonaJuridica persona,
			HttpServletRequest request) {
		Auditoria auditoria = new Auditoria();
		// System.out.println("id tipo ducmento repre controller: " +
		// persona.getRep_legal_id_tipo_documento());
		System.out.println("id tipo ducmento delegado controller: " + persona.getDelegado_id_tipo_documento());
		try {
			String capt = "";
			if (request.getSession().getAttribute("info__captcha") != null) {
				capt = request.getSession().getAttribute("info__captcha").toString();
			}
			System.out.println(request.getSession().getAttribute("info__captcha"));
			// System.out.println(request.getSession().getAttribute("info__captcha").toString());
			// System.out.println(persona.getCaptcha());

			if (persona.getCaptcha().equals(capt)) {
				// if
				// (persona.getCaptcha().equals(request.getSession().getAttribute("info__captcha").toString()))
				// {

				auditoria.Limpiar();
				String Nombre_Archivo = CommonHelpers.Generar_Nombre_Archivo(persona.getMifile());
				persona.setCodigoarchivo(Nombre_Archivo);
				persona.setIp_creacion(UserIdentityHelper.getClientIpAddress());
				auditoria = personaService.PersonaJuridica_Insertar(persona);

				if (auditoria.ejecucion_procedimiento) {
					if (!auditoria.rechazar) {
						int ID_PERSONA = (int) auditoria.objeto;

						Auditoria auditoriax = personaService.PersonaJuridica_ListarUno(ID_PERSONA, 0);
						PersonaJuridica entidad = (PersonaJuridica) auditoriax.objeto;
						System.out.println(ID_PERSONA);
						System.out.println(entidad.getFec_creacion());
						storageService.save(fileServer, persona.getMifile(), Nombre_Archivo);

						Map<String, Object> params = new HashMap<>();
						params.put("nombre", persona.getRazon_social());
						params.put("tipoper", "juridica");
						params.put("tipodoc", "RUC");
						params.put("nro_documento", persona.getRuc());
						params.put("imageResourceName", "logo.png");
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						String mensaje = formatter.format(date);
						params.put("mensaje", entidad.getFec_creacion());
						// params.put("mensaje", mensaje);
						params.put("anio", Calendar.getInstance().get(Calendar.YEAR));

						Resource resource = new Resource();

						Path path = Paths.get(fileServer, "logo.jpg");
						File file = path.toFile();
						resource.setImageBytes(FileUtils.readFileToByteArray(file));

						resource.setContentType("image/png");

						Map<String, Resource> resources = new HashMap<>();
						resources.put("logo.png", resource);

						emailutil = new EmailUtil();
						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(persona.getCorreo(), "Persona registrada",
									persona.getDelegado_correo(), "email/persona_crear", params, resources);
						} else {
							emailutil.sendHTML(persona.getCorreo(), "Persona registrada", persona.getDelegado_correo(),
									"email/persona_crear", params, resources);
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

	@PostMapping("/listarpersonas")
	public ResponseEntity<Auditoria> PersonasSolicitudes_Listar(@Valid @RequestBody UsuarioPersonaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = personaService.PersonasSolicitudes_Listar(modelo);
			if (auditoria.ejecucion_procedimiento) {

			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Persona_Listar : " + auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/listarusuariospersonas")
	public ResponseEntity<Auditoria> PersonaUsuario_Listar(@Valid @RequestBody UsuarioPersonaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = personaService.PersonaUsuario_Listar(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println("Persona_Listar : " + auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Error : Persona_Listar : " + auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/reporte_usuarios")
	public ResponseEntity<InputStreamResource> getExportarReporte_Usuarios(
			@Valid @RequestBody UsuarioPersonaGrilla modelo) {
		ByteArrayInputStream stream = null;
		HttpHeaders headers = new HttpHeaders();

		// headers.add("Content-Disposition", "attachment; filename=Res");
		headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		headers.add("Content-Disposition", "inline; filename=foo.xlsx");
		try {

			stream = personaService.exportUsuarios(modelo);

			stream.close();
			System.out.println("ENTRO XD 6");
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}

	@PostMapping("/listarusuariospersonaslibre")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> PersonaUsuario_ListarLibre(
			@Valid @ModelAttribute UsuarioPersonaGrillaLibre modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			System.out.println("Personalibre paso 1");
			String PI_NOMBRE_PERSONA = modelo.getNom_persona();
			System.out.println("Personalibre paso 2");
			String PI_NRO_PERSONA = modelo.getNro_documento();
			System.out.println("Personalibre paso 3");
			if (PI_NOMBRE_PERSONA == null || PI_NOMBRE_PERSONA == "") {
				PI_NOMBRE_PERSONA = "";
				System.out.println("Personalibre paso 4");
			}
			System.out.println("Personalibre paso 5");
			if (PI_NRO_PERSONA == null || PI_NRO_PERSONA == "") {
				PI_NRO_PERSONA = "";
				System.out.println("Personalibre paso 6");
			}

			/*
			 * int PI_ID_TIPO_USUARIO = Integer.valueOf(modelo.getId_tipo_usuario()); String
			 * PI_FLG_ESTADO = modelo.getFlg_estado(); String PI_NOM_PERSONA =
			 * modelo.getNom_persona(); String PI_NRO_DOCUMENTO = modelo.getNro_documento();
			 * String PI_CELULAR = modelo.getCelular(); String PI_TELEFONO =
			 * modelo.getTelefono(); String PI_CORREO = modelo.getCorreo();
			 */
			System.out.println("Personalibre paso 7");
			UsuarioPersonaGrilla busqueda = new UsuarioPersonaGrilla();
			System.out.println("Personalibre paso 8");
			busqueda.setNro_documento(PI_NRO_PERSONA);
			busqueda.setNom_persona(PI_NOMBRE_PERSONA);
			busqueda.setId_tipo_usuario("2");
			busqueda.setFlg_estado("1");
			busqueda.setCelular("");
			busqueda.setTelefono("");
			busqueda.setCorreo("");
			busqueda.setId_tipo_documenton("4"); // todos los documentos
			busqueda.setFec_inicio("");
			busqueda.setFec_fin("");
			busqueda.setNumpag(1);
			busqueda.setNumreg(9999999);

			System.out.println("Personalibre paso 9");
			auditoria = personaService.PersonaUsuario_Listar(busqueda);
			System.out.println("Personalibre paso 10");
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println("Personalibre paso 11");
				System.out.println("Persona_Listar : " + auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Error : Persona_Listar : " + auditoria.error_log);
		}
		System.out.println("Personalibre paso 12");
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listarunapersona/{id}/{tipopersona}/{valido}")
	public ResponseEntity<Auditoria> Persona_ListarUno(@PathVariable(value = "id") Long id,
			@PathVariable(value = "tipopersona") Integer tipopersona, @PathVariable(value = "valido") Integer valido) {
		Auditoria auditoria = new Auditoria();
		try {
			String iddepa = "", idprov = "", iddist = "";

			switch (tipopersona) {
				case 3:
					auditoria = personaService.PersonaInterno_ListarUno(id);
					break;
				default:
					// VentanillastdProxy proxy = new VentanillastdProxy();
					if (tipopersona.equals(1)) {
						PersonaNatural modelo = new PersonaNatural();
						modelo.setIdpersona(Integer.valueOf(id.toString()));

						auditoria = personaService.PersonaNatural_ListarUno(modelo, valido);
						PersonaNatural personaNatural = (PersonaNatural) auditoria.objeto;

						if (personaNatural != null) {
							if (personaNatural.getTipodoc() == 1) {
								AcMsUbigwsDto[] ubigeos = proxy.ubigeos();
								personaNatural.setDepartamento(
										UbigeoHelpers.getDepartamento(ubigeos, personaNatural.getIddepartamento()));
								personaNatural.setProvincia(UbigeoHelpers.getProvincia(ubigeos,
										personaNatural.getIddepartamento(), personaNatural.getIdprovincia()));
								personaNatural
										.setDistrito(UbigeoHelpers.getDistrito(ubigeos,
												personaNatural.getIddepartamento(),
												personaNatural.getIdprovincia(), personaNatural.getIddistrito()));
							}
						}

					} else {
						auditoria = personaService.PersonaJuridica_ListarUno(Integer.valueOf(id.toString()), valido);
						PersonaJuridica personaJuridica = (PersonaJuridica) auditoria.objeto;

						if (personaJuridica != null) {
							AcMsUbigwsDto[] ubigeos = proxy.ubigeos();
							personaJuridica.setDepartamento(
									UbigeoHelpers.getDepartamento(ubigeos, personaJuridica.getIddepartamento()));
							personaJuridica.setProvincia(UbigeoHelpers.getProvincia(ubigeos,
									personaJuridica.getIddepartamento(), personaJuridica.getIdprovincia()));
							personaJuridica
									.setDistrito(UbigeoHelpers.getDistrito(ubigeos, personaJuridica.getIddepartamento(),
											personaJuridica.getIdprovincia(), personaJuridica.getIddistrito()));
						}

						auditoria.objeto = personaJuridica;
					}

					break;
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/validapersona")
	public ResponseEntity<Auditoria> Persona_Validar(@Valid @ModelAttribute PersonaValida modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			String IP = UserIdentityHelper.getClientIpAddress();
			String USUARIO = UserIdentityHelper.get_CodigoUsuario();
			auditoria = personaService.Persona_Validar(modelo);
			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {
					try {
						if (modelo.getFlgvalida() == 1) {
							Integer P_ID_USUARIO = Integer.valueOf((String) auditoria.objeto);

							PersonaUsuario modelo_param = new PersonaUsuario();
							modelo_param.setFlgtipousuario(modelo.getIdtipousuario());
							modelo_param.setIdpersona(modelo.getIdpersona());
							auditoria = personaService.Persona_Usuario(modelo_param);

							if (auditoria.ejecucion_procedimiento) {
								if (!auditoria.rechazar) {
									PersonaUsuario entidad = (PersonaUsuario) auditoria.objeto;
									String Plantilla = "email/usuario_crear";
									Casilla casilla = new Casilla();
									casilla.setId_persona(Long.valueOf(modelo.getIdpersona() + ""));
									casilla.setId_usuario(P_ID_USUARIO);
									casilla.setId_documento(0);

									Map<String, Object> params = new HashMap<>();
									params.put("nombre", entidad.getNombre().toUpperCase());
									params.put("tipoper", entidad.getDesctipopersona());
									params.put("codigousuario", entidad.getNombreusuario());
									params.put("codigoclave", entidad.getClave());
									params.put("tipodoc", entidad.getDesctipodoc());
									params.put("nro_documento", entidad.getNrodocumento());
									params.put("imageResourceName", "logo.png");
									params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
									emailutil = new EmailUtil();
									String mensaje = emailutil.getTemplate(Plantilla, params);

									casilla.setIp_creacion(IP);
									casilla.setUsu_creacion(USUARIO);
									casilla.setObservacion("Creacion de usuario");
									casilla.setMensaje_html(mensaje);
									auditoria.Limpiar();// = casillaService.Casilla_Insertar(casilla);

									Resource resource = new Resource();
									Path path = Paths.get(fileServer, "logo.jpg");
									File file = path.toFile();
									resource.setImageBytes(FileUtils.readFileToByteArray(file));

									resource.setContentType("image/png");

									Map<String, Resource> resources = new HashMap<>();
									resources.put("logo.png", resource);

									if (micorrreo.equals("L")) {
										emailComponent.sendHTML(entidad.getCorreo(), "Usuario Creado",
												entidad.getCorreo_copia(), Plantilla, params, resources);
									} else {
										emailutil.sendHTML(entidad.getCorreo(), "Usuario Creado",
												entidad.getCorreo_copia(), Plantilla, params, resources);
									}

								}
							}
						} else {
							modelo.setObservacion(modelo.getObservacion().toUpperCase());
							String Plantilla = "email/usuario_rechazar";

							Map<String, Object> params = new HashMap<>();

							params.put("mensaje", modelo.getObservacion());
							params.put("imageResourceName", "logo.png");
							params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
							params.put("idpersona", modelo.getIdpersona());

							emailutil = new EmailUtil();
							// String mensaje = emailutil.getTemplate(Plantilla, params);

							Map<String, Resource> resources = new HashMap<>();
							Resource resource = new Resource();
							Path path = Paths.get(fileServer, "logo.jpg");
							File file = path.toFile();
							resource.setImageBytes(FileUtils.readFileToByteArray(file));
							resource.setContentType("image/png");
							resources.put("logo.png", resource);

							/*
							 * Resource resource = new Resource(); Path path = Paths.get(fileServer,
							 * "logo.jpg"); File file = path.toFile();
							 * resource.setImageBytes(FileUtils.readFileToByteArray(file));
							 * 
							 * resource.setContentType("image/png");
							 * 
							 * Map<String, Resource> resources = new HashMap<>(); resources.put("logo.png",
							 * resource);
							 */

							if (micorrreo.equals("L")) {
								emailComponent.sendHTML(modelo.getCorreo(), "Usuario Rechazado", "-", Plantilla, params,
										resources);
							} else {
								emailutil.sendHTML(modelo.getCorreo(), "Usuario Rechazado", "-", Plantilla, params,
										resources);
							}
						}
					} catch (Exception e) {
						auditoria.Error(e);
						// e.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/personaNactualiza")
	public ResponseEntity<Auditoria> PersonaNatural_Actualizar(@Valid @ModelAttribute PersonaNatural persona) {
		Auditoria auditoria = new Auditoria();
		try {
			persona.setCodigoarchivo("-");
			System.out.println("Tipo doc: " + persona.getTipodoc());

			if (persona.getMifile() != null) {
				if (!persona.getMifile().getOriginalFilename().equals("-")) {
					String Nombre_Archivo = CommonHelpers.Generar_Nombre_Archivo(persona.getMifile());
					persona.setCodigoarchivo(Nombre_Archivo);
					storageService.save(fileServer, persona.getMifile(), Nombre_Archivo);
				}

				/*
				 * if(persona.getTipodoc() == 2){
				 * persona.setIddepartamento("");
				 * persona.setIdprovincia("");
				 * persona.setIddistrito("");
				 * persona.setCodigoubigeo("");
				 * persona.setCodigoverificadni("");
				 * }
				 */
			}

			auditoria = personaService.PersonaNatural_Actualizar(persona);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/personaJactualiza")
	public ResponseEntity<Auditoria> PersonaJuridica_Actualizar(@Valid @ModelAttribute PersonaJuridica persona) {
		Auditoria auditoria = new Auditoria();
		try {
			persona.setCodigoarchivo("-");
			// modelo.setId_usuario(new UserIdentityHelper().getUserId());
			if (persona.getMifile() != null) {
				// System.out.println(persona.getMifile().getOriginalFilename());
				if (!persona.getMifile().getOriginalFilename().equals("-")) {
					String Nombre_Archivo = CommonHelpers.Generar_Nombre_Archivo(persona.getMifile());
					persona.setCodigoarchivo(Nombre_Archivo);
					storageService.save(fileServer, persona.getMifile(), Nombre_Archivo);
				}
			}

			auditoria = personaService.PersonaJuridica_Actualizar(persona);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
}
