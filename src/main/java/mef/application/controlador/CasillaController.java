package mef.application.controlador;
import java.util.Objects;
import java.io.ByteArrayInputStream;
import java.io.File;
//import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import mef.application.component.EmailComponent;
import mef.application.component.EmailUtil;
import mef.application.dto.Resource;
import mef.application.infrastructure.CommonHelpers;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.CasillaArchivo;
import mef.application.modelo.CasillaGrilla;
import mef.application.modelo.CasillaUno;
import mef.application.modelo.Documento;
import mef.application.modelo.PersonaCorreo;
//import mef.application.modelo.PersonaUsuario;
//import mef.application.modelo.UsuarioPersonaGrilla;
import mef.application.service.CasillaService;
import mef.application.service.DocumentoService;
import mef.application.service.FilesStorageService;
import mef.application.service.PersonaService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CasillaController {

	private EmailUtil emailutil;

	private EmailComponent emailComponent;

	@Value("${database.micorreo}")
	private String micorrreo;

	@Autowired
	FilesStorageService storageService;

	@Value("${file.fileserver}")
	private String fileServer;

	@Autowired
	CasillaService casillaService;

	@Autowired
	PersonaService personaService;

	@Autowired
	DocumentoService docService;

	public CasillaController(EmailComponent emailComponent) {
		this.emailComponent = emailComponent;
	}

	@PostMapping("/micasilla")
	public ResponseEntity<Auditoria> Casilla_Listar(@Valid @ModelAttribute CasillaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			modelo.setId_usuario(new UserIdentityHelper().getUserId() + "");
			auditoria = casillaService.Casilla_Listar(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/micasillauno")
	public ResponseEntity<Auditoria> Casilla_Listar_Uno(@Valid @ModelAttribute CasillaUno modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			if (modelo.getTipo_modulo().equals("1"))
				modelo.setId_usuario(new UserIdentityHelper().getUserId() + "");
			else {
				modelo.setId_usuario("0");
			}
			auditoria = casillaService.Casilla_Listar_Uno(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/insertaenmicasilla")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Casilla_Insertar(@Valid @ModelAttribute Casilla lmodelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String IP = UserIdentityHelper.getClientIpAddress();
			String USUARIO = UserIdentityHelper.get_CodigoUsuario();

			if (!lmodelo.getNotificar().equals(null)) {
				for (String cadena : lmodelo.getNotificar()) {
				
					
					Casilla modelo = new Casilla();
					modelo.setId_persona(Long.valueOf(cadena.split(" ")[0]));
					modelo.setId_usuario(Integer.valueOf(cadena.split(" ")[1]));
					modelo.setId_documento(Long.valueOf(cadena.split(" ")[2]));

					System.out.println("entro casilla  id_persona" +  modelo.getId_persona());
					
					auditoria = docService.Documento_ListarUno(Integer.valueOf(cadena.split(" ")[2]));
					Documento documento = (Documento) auditoria.objeto;

					auditoria = personaService.Persona_Correo(modelo.getId_persona());
					PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;
					Map<String, Object> params = new HashMap<>();
					params.put("nombre", entidad.getNombrepersona());
					params.put("tipoper", entidad.getDesctipopersona());
					params.put("tipodoc", entidad.getDesctipodocumento());
					params.put("documento", entidad.getNrodocumento());
					params.put("mensaje", lmodelo.getObservacion());
					params.put("imageResourceName", "logo.png");
					params.put("nro_solicitud", documento.getId_documento());
					params.put("nro_documento", documento.getNro_documento());
					params.put("asunto", documento.getAsunto());
					params.put("tipo_de_doc", documento.getDesc_tipo_documento());
					params.put("anio",  Calendar.getInstance().get(Calendar.YEAR));
					
					System.out.println("entro casilla 2 "  + entidad.getNombrepersona() ) ; 

					String hoja_ruta = "";
					if (documento.getNumero_sid() != "") {
						hoja_ruta = documento.getNumero_sid() + "-" + (documento.getAnio() + "");
					} else {
						hoja_ruta = documento.getHoja_ruta();
					}
					// String hoja_ruta = documento.getAnio() != null ? documento.getNumero_sid() +
					// "-" + (documento.getAnio() + "") : documento.getHoja_ruta();

					params.put("hojaruta", hoja_ruta);

					String Plantilla = "email/documentoFile";
					params.put("imageResourceName", "logo.png");
					String mensaje = emailComponent.getTemplate(Plantilla, params);

					modelo.setIp_creacion(IP);
					modelo.setUsu_creacion(USUARIO);
					modelo.setObservacion(lmodelo.getObservacion());
					modelo.setMensaje_html(mensaje);
					auditoria.Limpiar();
					//auditoria = casillaService.Casilla_Insertar(modelo);
					 
					Path path = Paths.get(fileServer);

					if (auditoria.ejecucion_procedimiento) {
						if (!auditoria.rechazar) {
							//Long id_casilla = (Long) auditoria.objeto;
							Map<String, String> paramsFile = new HashMap<>();
							if (!Objects.isNull(lmodelo.getArchivos())) {
								for (MultipartFile file : lmodelo.getArchivos()) {
									CasillaArchivo miarchivo = new CasillaArchivo();
									String filename = UUID.randomUUID().toString();
									filename = CommonHelpers.Generar_Nombre_Archivo(file, filename);
									//miarchivo.setId_casilla(id_casilla);
									miarchivo.setNombre_archivo(file.getOriginalFilename());
									miarchivo.setCodigo_archivo(filename);
									miarchivo.setTamanio_archivo(file.getBytes().length);
									miarchivo.setExtension_archivo(
											FilenameUtils.getExtension(file.getOriginalFilename()));
									miarchivo.setUsu_creacion(USUARIO);
									miarchivo.setIp_creacion(IP);

									auditoria.Limpiar(); // = casillaService.CasillaArchivo_Insertar(miarchivo);

									paramsFile.put(file.getOriginalFilename(), path.toString() + "/" + filename);
									if (auditoria.ejecucion_procedimiento)
										if (!auditoria.rechazar)
											storageService.save(path.toString(), file, filename);
										else
											break;
									else
										break;
								}
							}
							Resource resource = new Resource();
							Path path2 = Paths.get(fileServer, "logo.jpg");
							File file = path2.toFile();
							resource.setImageBytes(FileUtils.readFileToByteArray(file));

							resource.setContentType("image/png");

							Map<String, Resource> resources = new HashMap<>();
							resources.put("logo.png", resource);

							emailutil = new EmailUtil(); 
							if (micorrreo.equals("L")) {
								emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), Plantilla, params, resources);
							} else {
								emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), Plantilla, params, resources);
							}
						}
					} else {
						break;
					}
				}
			} else {
				auditoria.Rechazar("No se ha seleccionado ningun registro");
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	
	
	
	@PostMapping("/casilla_reenviar_Email")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Casilla_ReenviarEmail(@Valid @ModelAttribute Casilla lmodelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String IP = UserIdentityHelper.getClientIpAddress();
			String USUARIO = UserIdentityHelper.get_CodigoUsuario();

			if (!lmodelo.getId_casilla().equals(0)) {
				
					Casilla modelo = new Casilla();
					CasillaUno modeloC = new CasillaUno(); 
					
					Resource resource = new Resource();
					Path path2 = Paths.get(fileServer, "logo.jpg");
					File file = path2.toFile();
					resource.setImageBytes(FileUtils.readFileToByteArray(file));

					resource.setContentType("image/png");
					Map<String, Resource> resources = new HashMap<>();
					resources.put("logo.png", resource);

						
					modeloC.setId_casilla(String.valueOf(lmodelo.getId_casilla())); 
					modeloC.setId_usuario(String.valueOf(lmodelo.getId_usuario())); 
					
			
					auditoria = personaService.Persona_Correo(lmodelo.getId_persona());
					PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;
					if(auditoria.ejecucion_procedimiento) {
						auditoria = casillaService.Casilla_Listar_Uno(modeloC);
						CasillaUno entidadC = (CasillaUno) auditoria.objeto;

							emailutil = new EmailUtil(); 			
								emailutil.sendEmail(entidad.getCorreo(), "Reenviado - Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), entidadC.getMensaje_html(), resources);
							
							
							
		     }
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	@PostMapping("/casilla_enviar")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Casilla_Notificar(@Valid @ModelAttribute Casilla lmodelo) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String IP = UserIdentityHelper.getClientIpAddress();
			String USUARIO = UserIdentityHelper.get_CodigoUsuario();

			if (!lmodelo.getNotificar().equals(null)) {
				for (String cadena : lmodelo.getNotificar()) {

					Casilla modelo = new Casilla();
					modelo.setId_persona(Long.valueOf(cadena.split(" ")[0]));
					modelo.setId_usuario(Integer.valueOf(cadena.split(" ")[1]));
					modelo.setId_documento(0);
					auditoria = personaService.Persona_Correo(modelo.getId_persona());
					PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;
					Map<String, Object> params = new HashMap<>();
					params.put("nombre", entidad.getNombrepersona());
					params.put("tipoper", entidad.getDesctipopersona());
					params.put("tipodoc", entidad.getDesctipodocumento());
					params.put("nro_documento", entidad.getNrodocumento());
					params.put("mensaje", lmodelo.getObservacion());
					params.put("imageResourceName", "logo.png");
					params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
					String mensaje = emailComponent.getTemplate("email/notificar", params);

					modelo.setIp_creacion(IP);
					modelo.setUsu_creacion(USUARIO);
					modelo.setObservacion(lmodelo.getObservacion());
					modelo.setMensaje_html(mensaje);
					auditoria.Limpiar();
					auditoria = casillaService.Casilla_Insertar(modelo);

					Path path = Paths.get(fileServer);
					System.out.println("paso 1 : " + auditoria.error_log);

					if (auditoria.ejecucion_procedimiento) {
						System.out.println("pas 2 : " + auditoria.error_log);  

						if (!auditoria.rechazar) {
							Long id_casilla = (Long) auditoria.objeto;
							System.out.println("pas 3 : " + auditoria.error_log);
							Map<String, String> paramsFile = new HashMap<>();
							System.out.println("pas 4 : " + auditoria.error_log);
							System.out.println("pas 5 archivos : " + lmodelo.getArchivos() );
							if (!Objects.isNull(lmodelo.getArchivos())) {
								System.out.println("pas 5 : " + auditoria.error_log);
								for (MultipartFile file : lmodelo.getArchivos()) {
									System.out.println("pas 6 : " + auditoria.error_log);
									CasillaArchivo miarchivo = new CasillaArchivo();
									System.out.println("pas 7 : " + auditoria.error_log);
									String filename = UUID.randomUUID().toString();
									System.out.println("pas 8 : " + auditoria.error_log);
									filename = CommonHelpers.Generar_Nombre_Archivo(file, filename);
									System.out.println("pas 9 : " + auditoria.error_log);
									//miarchivo.setId_casilla(id_casilla);
									miarchivo.setNombre_archivo(file.getOriginalFilename());
									System.out.println("pas 10 : " + auditoria.error_log);
									miarchivo.setCodigo_archivo(filename);
									System.out.println("pas 11 : " + auditoria.error_log);
									miarchivo.setTamanio_archivo(file.getBytes().length);
									System.out.println("pas 12 : " + auditoria.error_log);
									miarchivo.setExtension_archivo(
											FilenameUtils.getExtension(file.getOriginalFilename()));
									System.out.println("pas 13 : " + auditoria.error_log);
									miarchivo.setUsu_creacion(USUARIO);
									miarchivo.setIp_creacion(IP);
									miarchivo.setId_casilla(id_casilla);
									System.out.println("pas 14 : " + auditoria.error_log);
									auditoria.Limpiar();// = 
									casillaService.CasillaArchivo_Insertar(miarchivo);
									System.out.println("pas 15 : " + auditoria.error_log);
									paramsFile.put(file.getOriginalFilename(), path.toString() + "/" + filename);
									System.out.println("pas 16 : " + auditoria.error_log);
									if (auditoria.ejecucion_procedimiento)
										if (!auditoria.rechazar)
											storageService.save(path.toString(), file, filename);
										else
											break;
									else
										break;
								}
							}
							System.out.println("pas 17 : " + auditoria.error_log);
							Resource resource = new Resource();
							Path path2 = Paths.get(fileServer, "logo.jpg");
							File file = path2.toFile();
							resource.setImageBytes(FileUtils.readFileToByteArray(file));

							// resource.setImageBytes(Files.readAllBytes(Paths.get(fileServer ,
							// "logo.jpg")));
							resource.setContentType("image/png");
							System.out.println("pas 17 : " + auditoria.error_log);
							Map<String, Resource> resources = new HashMap<>();
							resources.put("logo.png", resource);
							emailutil = new EmailUtil();
							if (micorrreo.equals("L")) {
								emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), "email/notificar", params, resources);
							} else {
								emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), "email/notificar", params, resources);
							}

						}
					} else {
						break;
					}
				}
			} else {
				auditoria.Rechazar("No se ha seleccionado ningun registro");
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/vimicasilla")
	public ResponseEntity<Auditoria> Casilla_Estado(@Valid @RequestBody Casilla modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			Long id_casilla = modelo.getId_casilla();
			auditoria = casillaService.Casilla_Estado(id_casilla);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println("Persona_Listar : " + auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println("Error : Persona_Listar : " + auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/reportecasilla")
	public ResponseEntity<InputStreamResource> Casilla_Exportar(@Valid @ModelAttribute CasillaGrilla modelo) {
		ByteArrayInputStream stream = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Reportes.xlsx");
		headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		try {
			modelo.setId_usuario(new UserIdentityHelper().getUserId() + "");
			/*
			 * int TipoUsuario = UserIdentityHelper.getTipoUsuario(); if (TipoUsuario == 2)
			 * doc.setId_usuario(UserIdentityHelper.getUserId() + ""); else
			 * doc.setId_usuario("");
			 */
			stream = casillaService.Casilla_Exportar(modelo);
			stream.close();
			System.out.println("ENTRO XD 6");
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
}
