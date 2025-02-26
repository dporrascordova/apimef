package mef.application.controlador;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mef.application.component.EmailComponent;
import mef.application.component.EmailUtil;
import mef.application.dto.Resource;
import mef.application.infrastructure.CommonHelpers;
import mef.application.infrastructure.DateHelper;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.Documento;
import mef.application.modelo.DocumentoObservacion;
import mef.application.modelo.DocumentoRecepcionar;
import mef.application.modelo.EnlaceEncuesta;
import mef.application.modelo.PersonaCorreo;
import mef.application.service.CasillaService;
import mef.application.service.DocumentoService;
import mef.application.service.EnlaceEncuestaService;
import mef.application.service.EstadoService;
import mef.application.service.FilesStorageService;
import mef.application.service.MefService;
import mef.application.service.PersonaService;

@RestController
@RequestMapping("/api/sgdd")
public class SGDDControlador {

	private EmailComponent emailComponent;

	@Value("${database.micorreo}")
	private String micorrreo;

	@Value("${app.url}")
	private String url_servelet;

	@Value("${file.fileserver}")
	private String fileServer;

	@Value("${document.months_per_report}")
	private Integer monthsPerReport;

	@Autowired
	FilesStorageService storageService;

	@Autowired
	DocumentoService docService;

	@Autowired
	PersonaService personaService;

	@Autowired
	EstadoService estadoService;

	@Autowired
	MefService mefService;

	@Autowired
	CasillaService casillaService;
	
	@Autowired
	EnlaceEncuestaService enlaceEncuestaService;

	private EmailUtil emailutil;

	public SGDDControlador(EmailComponent emailComponent) {
		super();
		this.emailComponent = emailComponent;
	}

	@PostMapping("/observar")
	@Produces("application/json")
	public ResponseEntity<Auditoria> ObservarSolicitud(@Valid @RequestBody DocumentoObservacion obs) {
		Auditoria auditoria = new Auditoria();

		try {
			System.out.println("Metodo observar:");
			System.out.println(obs.getId_documento());
			System.out.println(obs.getFecha_anulacion());
			System.out.println(obs.getUsu_creacion());
			System.out.println(obs.getObservacion());
			//String fecha_anulacion = DateHelper.CalcularFechaAnulacion();
			//obs.setFecha_anulacion(fecha_anulacion);
			
			auditoria = docService.Documento_Observar(obs);

			if (auditoria.ejecucion_procedimiento && !auditoria.rechazar) {
				Auditoria auditoriaDoc = docService.Documento_ListarUno(Integer.valueOf(obs.getId_documento()));
				Documento documento = (Documento) auditoriaDoc.objeto;
				Casilla modelo = new Casilla();

				Auditoria auditoriaPersona = personaService.Persona_Correo(Long.valueOf(documento.getId_persona()));
				PersonaCorreo entidad = (PersonaCorreo) auditoriaPersona.objeto;

				Date date = new Date();
				SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

				Map<String, Object> params = new HashMap<>();
				params.put("nombre", entidad.getNombrepersona());
				params.put("idsolicitud", obs.getId_documento() + "-" + formattery.format(date));
				params.put("hoja_ruta", documento.getNumero_sid() + "-" + documento.getAnio());
				params.put("asunto", documento.getAsunto());
				params.put("fec_registro", documento.getFec_creacion());
				params.put("observacion", obs.getObservacion().toUpperCase());
				params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
				String Plantilla = "email/SGDD_documento_observar";
				params.put("imageResourceName", "logo.png");

				emailutil = new EmailUtil();
				String mensaje = emailutil.getTemplate(Plantilla, params);

				modelo.setId_documento(Integer.valueOf(obs.getId_documento()));
				modelo.setId_usuario(Integer.valueOf(documento.getId_usuario()));
				modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
				// modelo.setUsu_creacion(new UserIdentityHelper().get_CodigoUsuario());
				modelo.setUsu_creacion(obs.getUsu_creacion());
				modelo.setObservacion(obs.getObservacion());
				modelo.setMensaje_html(mensaje);
				modelo.setFlg_notificacion(1);

				Auditoria auditoriaCasilla = casillaService.Casilla_Insertar(modelo);

				if (auditoriaCasilla.ejecucion_procedimiento) {
					if (!auditoriaCasilla.rechazar) {

						Resource resource = new Resource();
						Path path = Paths.get(fileServer, "logo.jpg");
						File file = path.toFile();
						resource.setImageBytes(FileUtils.readFileToByteArray(file));
						resource.setContentType("image/png");
						Map<String, Resource> resources = new HashMap<>();
						resources.put("logo.png", resource);

						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), Plantilla, params, resources);
						} else {

							emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), Plantilla, params, resources);
						}

					}
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/nopresentar")
	@Produces("application/json")
	public ResponseEntity<Auditoria> SolicitudNoPresentar(@Valid @RequestBody DocumentoObservacion doc) {
		Auditoria auditoria = new Auditoria();

		try {
			System.out.println("Metodo nopresentar:");
			System.out.println(doc.getId_documento()); 
			auditoria = docService.Documento_NoPresentar(Integer.valueOf(doc.getId_documento()));
			System.out.println(auditoria.mensaje_salida);
			if (auditoria.ejecucion_procedimiento && !auditoria.rechazar) {
				auditoria = docService.Documento_ListarUno(Integer.valueOf(doc.getId_documento()));
				Documento documento = (Documento) auditoria.objeto;

				Casilla modelo = new Casilla();

				auditoria = personaService.Persona_Correo(Long.valueOf(documento.getId_persona()));
				PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;

				Date date = new Date();
				SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

				Map<String, Object> params = new HashMap<>();
				params.put("nombre", entidad.getNombrepersona());
				params.put("idsolicitud", doc.getId_documento() + "-" + formattery.format(date));
				params.put("hoja_ruta", documento.getNumero_sid() + "-" + documento.getAnio());
				params.put("fec_registro", documento.getFec_creacion());
				params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
				String Plantilla = "email/SGDD_documento_nopresentado";
				params.put("imageResourceName", "logo.png");

				emailutil = new EmailUtil();
				String mensaje = emailutil.getTemplate(Plantilla, params);

				modelo.setId_documento(Integer.valueOf(doc.getId_documento()));
				modelo.setId_usuario(Integer.valueOf(documento.getId_usuario()));
				modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
				modelo.setUsu_creacion(doc.getUsu_creacion());
				modelo.setObservacion("DOCUMENTO NO PRESENTADO");
				modelo.setMensaje_html(mensaje);
				modelo.setFlg_notificacion(1);

				auditoria = casillaService.Casilla_Insertar(modelo);

				if (auditoria.ejecucion_procedimiento) {
					if (!auditoria.rechazar) {

						Resource resource = new Resource();
						Path path = Paths.get(fileServer, "logo.jpg");
						File file = path.toFile();
						resource.setImageBytes(FileUtils.readFileToByteArray(file));
						resource.setContentType("image/png");
						Map<String, Resource> resources = new HashMap<>();
						resources.put("logo.png", resource);

						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), Plantilla, params, resources);
						} else {

							emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), Plantilla, params, resources);
						}

					}
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/recepcionar")
	@Produces("application/json")
	public ResponseEntity<Auditoria> RecepcionarSolicitud(@Valid @RequestBody DocumentoRecepcionar recepcionar) {
		Auditoria auditoria = new Auditoria();

		try {

			System.out.println("Metodo recepcionar:");
			System.out.println(recepcionar.getId_documento()); 
			System.out.println(recepcionar.getFecha_recepcion()); 
			System.out.println(recepcionar.getUsu_creacion()); 
			Date fechaRecepcion = CommonHelpers.formatoStringToDate(recepcionar.getFecha_recepcion());
			System.out.println(fechaRecepcion); 
 
			
			auditoria = docService.Documento_Recepcionar(recepcionar.getId_documento(), fechaRecepcion,
					recepcionar.getUsu_creacion());

			if (auditoria.ejecucion_procedimiento && !auditoria.rechazar) {
				Auditoria auditoriaDoc = docService.Documento_ListarUno(Integer.valueOf(recepcionar.getId_documento()));
				Documento documento = (Documento) auditoriaDoc.objeto;
				Casilla modelo = new Casilla();

				Auditoria auditoriaPersona = personaService.Persona_Correo(Long.valueOf(documento.getId_persona()));
				PersonaCorreo entidad = (PersonaCorreo) auditoriaPersona.objeto;
				
				Auditoria auditoriaEncuesta = enlaceEncuestaService.obtenerEncuestaActiva();
				EnlaceEncuesta encuesta = (EnlaceEncuesta) auditoriaEncuesta.objeto;

				Date date = new Date();
				SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

				Map<String, Object> params = new HashMap<>();
				params.put("nombre", entidad.getNombrepersona());
				params.put("idsolicitud", recepcionar.getId_documento() + "-" + formattery.format(date));
				params.put("hoja_ruta", documento.getNumero_sid() + "-" + documento.getAnio());				
				params.put("fec_recepcion", recepcionar.getFecha_recepcion());
				params.put("mensaje_encuesta", encuesta.getMensaje_html());
				params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
				String Plantilla = "email/documento_recepcionar";
				params.put("imageResourceName", "logo.png");

				emailutil = new EmailUtil();
				String mensaje = emailutil.getTemplate(Plantilla, params);

				modelo.setId_documento(recepcionar.getId_documento());
				modelo.setId_usuario(Integer.valueOf(documento.getId_usuario()));
				modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
				modelo.setUsu_creacion(recepcionar.getUsu_creacion());
				modelo.setObservacion("DOCUMENTO RECEPCIONADO");
				modelo.setMensaje_html(mensaje);
				modelo.setFlg_notificacion(1);

				Auditoria auditoriaCasilla = casillaService.Casilla_Insertar(modelo);

				if (auditoriaCasilla.ejecucion_procedimiento) {
					if (!auditoriaCasilla.rechazar) {

						Resource resource = new Resource();
						Path path = Paths.get(fileServer, "logo.jpg");
						File file = path.toFile();
						resource.setImageBytes(FileUtils.readFileToByteArray(file));
						resource.setContentType("image/png");
						Map<String, Resource> resources = new HashMap<>();
						resources.put("logo.png", resource);

						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), Plantilla, params, resources);
						} else {

							emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), Plantilla, params, resources);
						}

					}
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

}
