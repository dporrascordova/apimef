package mef.application.component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import mef.application.dto.Resource;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.Documento;
import mef.application.modelo.DocumentoAnexo;
import mef.application.modelo.PersonaCorreo;
import mef.application.modelo.UsuarioPersona;
import mef.application.modelo.entity.DocumentoAnexoEntity;
import mef.application.modelo.entity.DocumentoAnexoPK;
import mef.application.modelo.entity.DocumentoEntity;
import mef.application.repositorio.DocumentoAnexoRepository;
import mef.application.repositorio.DocumentoRepository;
import mef.application.service.CasillaService;
import mef.application.service.DocumentoService;
import mef.application.service.EstadoService;
import mef.application.service.FilesStorageService;
import mef.application.service.MefService;
import mef.application.service.PersonaService;
import mef.application.service.impl.OficinaServiceImpl;
import pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto;
import pe.gob.mef.std.bs.web.ws.AnexoDto;
import pe.gob.mef.std.bs.web.ws.HrDto;
import pe.gob.mef.std.bs.web.ws.IdValorDto;
import pe.gob.mef.std.bs.web.ws.TdFlujoSDto;
import pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto;
import pe.gob.mef.std.bs.web.ws.VentanillastdProxy;

@Component

public class DocumentScheduler {

	private static final Logger logger = LoggerFactory.getLogger(DocumentScheduler.class);
	private final VentanillastdProxy ventanillastdProxy;
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
	private EmailComponent emailComponent;
	@Value("${database.micorreo}")
	private String micorrreo;
	@Value("${file.fileserver}")
	private String fileServer;
	private EmailUtil emailutil;
	@Autowired
	private OficinaServiceImpl oficinaService;
	@Autowired
	private DocumentoAnexoRepository documentoAnexoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;

	public DocumentScheduler(EmailComponent emailComponent, VentanillastdProxy ventanillastdProxy) {
		this.emailComponent = emailComponent;
		this.ventanillastdProxy = ventanillastdProxy;
	}

	// Job de prueba para recar un nuevo registro en SGDD
	public void TestSGDD() throws IOException {
		System.out.println("-----TestSGDD-------");
		System.out.println(
				"EJECUTANDOSE ENVIOS PROGRAMADOS DE DOCUMENTOS AL SGDD: " + (new Date(System.currentTimeMillis())));

		try {

			ArrayList<AnexoDto> anexoDto = new ArrayList<AnexoDto>();
			HrDto expediente = new HrDto();
			Path path;
			Integer totalFaileFiles = 0;
			String message = "";
			String USU = "lmauricio";
			String filename = "";
			String anexosHR = "";
			ArrayList<String> arrayAnexosHR = new ArrayList<>();

			// File principal
			filename = "b37c1de3-e1e6-4704-9e5e-1c89a0b2adec.pdf";
			path = Paths.get(fileServer, "89536", filename);
			byte[] fileByte = Files.readAllBytes(path);
			anexoDto.add(new AnexoDto(fileByte, fileByte.length, filename));

			System.out.println(fileByte.length);
			System.out.println(filename);

			AnexoDto[] anexoDto1 = new AnexoDto[1];
			anexoDto1[0] = new AnexoDto(fileByte, fileByte.length, "215478.PDF");

			// Oficinas
			TdFlujoSDto[] oficinas = new TdFlujoSDto[1];
			oficinas[0] = new TdFlujoSDto(Long.valueOf(1), Long.valueOf(13), "", true);
			System.out.println("Total Anexos: " + anexoDto.size());

			// VentanillastdProxy proxy = new VentanillastdProxy();

			// Crear expediente en el SGDD
			expediente = ventanillastdProxy.crearExpediente(USU, "617045", Long.valueOf(53), "1123", 3,
					"ASUNTO DE PRUEBA", "", "",
					"", "", "0", "MUNICIPALIDAD DISTRITAL DE SAN PABLO", "20552486219", "PLAZA DE ARMAS", "CUSCO",
					"CANCHIS", "SAN PABLO", "juliahuallpa@hotmail.com", anexoDto.toArray(new AnexoDto[anexoDto.size()]),
					"10.2.20.79", oficinas, null, null, new long[0], 0, anexosHR);

			System.out.println("PASO EL SERVICIO");
			if (expediente == null)
				System.out.println("ESTA VACIO");
			else {
				String hojaRuta = expediente.getNumeroSid() + "-" + expediente.getNumeroAnio();

				System.out.println("Hoja de ruta: " + hojaRuta);
			}

		} catch (Exception ex) {
			System.out.println("ERROR EN LA CREACION DE LA HOJA DE RUTA SGDS:");
			System.out.println(ex.toString());
		}
	}

	// Job que realiza un query by estado 8 and flag 1 de error de servicio SGDD
	// para obtener HR nuevamente
	@Scheduled(fixedRate = 300000)
	public void ScheduletDocumentSGDD() throws IOException {
		logger.info("-----ScheduletDocumentSGDD------- estado 8 flag 1");
		logger.info(
				"EJECUTANDOSE ENVIOS PROGRAMADOS DE DOCUMENTOS AL SGDD : " + (new Date(System.currentTimeMillis())));

		Integer totalFaileFiles = 1;
		Integer totalFaileFilesUploaded = 1;

		Auditoria documentosPorRecibir = docService.Documento_Listar_PorEstado(8);
		if (documentosPorRecibir.ejecucion_procedimiento && !documentosPorRecibir.rechazar) {
			List<Documento> lista = (List<Documento>) documentosPorRecibir.objeto;
			logger.info("Lista de :" + lista.size());
			for (Documento documento : lista) {
				try {
					logger.info("Solicitud a reparar estado 8:" + documento.getId_documento());

					ArrayList<AnexoDto> anexoDtoPrincipal = new ArrayList<AnexoDto>();
					HrDto expediente = new HrDto();
					Path path;

					String USU = "lmauricio";
					String IP = documento.getIp_creacion();
					String filename = "";
					String anexosHR = "";
					ArrayList<String> arrayAnexosHR = new ArrayList<>();

					// File principal
					filename = documento.getCodigo_archivo() + ".pdf";
					path = Paths.get(fileServer, documento.getId_documento() + "", filename);
					byte[] fileByte = Files.readAllBytes(path);
					anexoDtoPrincipal.add(new AnexoDto(fileByte, fileByte.length, filename.replaceAll("\u0002", "")));

					logger.info("File principal: " + "" + fileByte.length);
					logger.info("File principal: " + filename);

					// Oficinas
					TdFlujoSDto[] oficinas = new TdFlujoSDto[1];
					oficinas[0] = new TdFlujoSDto(Long.valueOf(1), Long.valueOf(documento.getId_oficina()), "", true);
					// System.out.println("Total Anexos: " + anexoDto.size());

					// Datos de la persona
					Auditoria auditoriaPersona = personaService
							.UsuarioPersona_Listar(Long.valueOf(documento.getId_usuario()));
					UsuarioPersona mipersona = new UsuarioPersona();

					if (auditoriaPersona.ejecucion_procedimiento)
						mipersona = (UsuarioPersona) auditoriaPersona.objeto;

					// VentanillastdProxy proxy = new VentanillastdProxy();

					String nombre_persona = "";
					String nombre = mipersona.getNombre_usuario();
					String apellido_paterno = mipersona.getApellido_paterno();
					String apellido_materno = mipersona.getApellido_materno();
					String dni = mipersona.getNro_documento();
					String ruc = mipersona.getNro_documento();
					String razon_social = mipersona.getNombre_usuario();

					if (apellido_paterno.equals("") || apellido_paterno.equals(null)
							|| apellido_paterno.equals("null")) {
						nombre = "";
						dni = "";
						apellido_paterno = "";
						apellido_materno = "";
						nombre_persona = razon_social;
					} else {
						nombre_persona = nombre + " " + apellido_paterno + " " + apellido_materno;
						ruc = "";
						razon_social = "";
					}
					String direcc = mipersona.getDireccion();
					if (!direcc.equals("") || !direcc.equals(null)) {
						if (direcc.length() < 4) {
							mipersona.setDireccion("Dir:" + direcc);
							mipersona.setDireccion(direcc);
						}
					}

					logger.info("Solicitud: " + documento.getId_documento());
					logger.info("Asunto:" + documento.getAsunto());
					expediente = ventanillastdProxy.crearExpediente(USU, documento.getId_documento() + "",
							Long.valueOf(documento.getId_tipo_documento()), documento.getNro_documento(),
							documento.getNro_folios(), documento.getAsunto().replaceAll("\u0002", ""), apellido_paterno,
							apellido_materno, nombre, dni, mipersona.getTelefono(), razon_social, ruc,
							mipersona.getDireccion(), mipersona.getDesc_departamento(), mipersona.getDesc_provincia(),
							mipersona.getDesc_distrito(), mipersona.getCorreo(),
							anexoDtoPrincipal.toArray(new AnexoDto[anexoDtoPrincipal.size()]), IP, oficinas, null, null,
							new long[0], 0, anexosHR);
					if (expediente != null) {

						logger.info("Datos de retorno: ");
						logger.info("getNumeroDoc:" + expediente.getNumeroDoc());
						logger.info("getNumeroSid:" + expediente.getNumeroSid());
						logger.info("getIddoc:" + expediente.getIddoc());
						logger.info("getFechaCompleto:" + expediente.getFechaCompleto());

					} else {

						Optional<DocumentoEntity> optionalDocumento = documentoRepository
								.findById(Long.valueOf(documento.getId_documento()));
						if (optionalDocumento.isPresent()) {
							DocumentoEntity documentoenti = optionalDocumento.get();
							documentoenti.setObsSgdd("No se genero la HR");
							documentoRepository.save(documentoenti);

						}

						throw new Exception("El expediente es nulo, error al crear expediente.");

					}
					String hojaRuta = expediente.getNumeroSid() + "-" + expediente.getNumeroAnio().toString();
					System.out.println("Hoja de ruta: " + hojaRuta);

					List<DocumentoAnexo> anexos = docService.getAnexosDocumentoById(documento.getId_documento());
					totalFaileFiles += anexos.size();
					System.out.println("total files totalFaileFiles");

					for (DocumentoAnexo itemAnexo : anexos) {
						switch (itemAnexo.getFlg_link()) {
							case "1":

								filename = itemAnexo.getCodigo_archivo();
								path = Paths.get(fileServer, documento.getId_documento() + "", filename);
								fileByte = Files.readAllBytes(path);

								System.out.println("Files Anexo: " + fileByte.length);

								DocumentoAnexoPK id = new DocumentoAnexoPK();
								id.setIdDocumento(Long.valueOf(itemAnexo.getId_documento()));
								id.setOrden((long) itemAnexo.getOrden());

								Optional<DocumentoAnexoEntity> anexoItem = documentoAnexoRepository
										.findById(id);
								DocumentoAnexoEntity documentoItem;
								if (anexoItem.isPresent()) {
									documentoItem = anexoItem.get();

									// Paso 1: Llamar al servicio ventanillastdProxy y obtener el objeto "AxDto"
									HrDto anexo = null;
									try {
										anexo = ventanillastdProxy.agregarAExpediente(
												USU,
												expediente.getNumeroSid(),
												expediente.getNumeroAnio(),
												new AnexoDto(
														fileByte,
														fileByte.length,
														itemAnexo.getNombre_archivo().replaceAll("\u0002", "")),
												IP);
										// Aquí sabes que sería un "HTTP 200 OK"
										System.out.println("Anexo agregado correctamente: " + anexo);

										documentoItem.setEstadoAnexo(1);
										documentoItem.setIdAnexo(anexo.getIdanexo().toString());

										documentoAnexoRepository.save(documentoItem);

										totalFaileFilesUploaded += 1;
									} catch (Exception e) {
										// Otro error inesperado
										e.printStackTrace();
										System.err.println("Error inesperado: " + e.getMessage());
										documentoItem.setEstadoAnexo(0);
										documentoItem.setIdAnexo(null);

										documentoAnexoRepository.save(documentoItem);

										Optional<DocumentoEntity> optionalDocumento = documentoRepository
												.findById(Long.valueOf(documento.getId_documento()));
										if (optionalDocumento.isPresent()) {
											DocumentoEntity documentoenti = optionalDocumento.get();
											documentoenti.setObsSgdd("No se mando los anexos completos");
											documentoRepository.save(documentoenti);

										}
									}

									if (anexo == null) {

										System.err.println("Anexo null: ");
										documentoItem.setEstadoAnexo(0);
										documentoItem.setIdAnexo(null);

										documentoAnexoRepository.save(documentoItem);
									} else if (anexo.getIdanexo() == null) {
										System.err.println("anexo.getIdanexo() is null ");
										documentoItem.setEstadoAnexo(0);
										documentoItem.setIdAnexo(null);

										documentoAnexoRepository.save(documentoItem);
									}
								}
								break;
							case "2":
								arrayAnexosHR.add(itemAnexo.getNombre_archivo());
								break;
						}
					}
					System.out.println("totalFaileFilesUploaded:" + totalFaileFilesUploaded);
					System.out.println("totalFaileFiles:" + totalFaileFiles);

					if (totalFaileFilesUploaded != totalFaileFiles) {
						Optional<DocumentoEntity> optionalDocumento = documentoRepository
								.findById(Long.valueOf(documento.getId_documento()));
						if (optionalDocumento.isPresent()) {
							DocumentoEntity documentoenti = optionalDocumento.get();
							documentoenti.setObsSgdd("No se mando los anexos completos");
							documentoRepository.save(documentoenti);

						}
					}

					docService.Documento_Agregar_HojaRuta(documento.getId_documento(), expediente.getNumeroAnio(),
							expediente.getNumeroSid(), USU);

					// Notificar por correo.
					Casilla modelo = new Casilla();
					modelo.setId_documento(documento.getId_documento());
					modelo.setId_persona(Long.valueOf(documento.getId_persona()));
					modelo.setId_usuario(Integer.valueOf(documento.getId_usuario()));

					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

					Auditoria personaRespose = personaService.Persona_Correo(modelo.getId_persona());
					PersonaCorreo entidad = (PersonaCorreo) personaRespose.objeto;

					String mimensaje = formatter.format(date);
					mimensaje = mimensaje.toUpperCase();
					Map<String, Object> params = new HashMap<>();
					params.put("idsolicitud", documento.getId_documento() + "-" + formattery.format(date));
					params.put("hoja_ruta", hojaRuta);
					params.put("fec_registro", documento.getFec_creacion());
					params.put("nombre", entidad.getNombrepersona());
					params.put("mensaje", documento.getFec_creacion());
					params.put("imageResourceName", "logo.png");
					params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
					String Plantilla = "email/SGDD_documento_crear";
					String mensaje = emailComponent.getTemplate(Plantilla, params);

					modelo.setIp_creacion(String.valueOf(InetAddress.getLocalHost()));
					modelo.setUsu_creacion(documento.getId_usuario());
					modelo.setObservacion(documento.getAsunto());
					modelo.setMensaje_html(mensaje);
					modelo.setFlg_notificacion(1);
					Auditoria casillaResponse = casillaService.Casilla_Insertar(modelo);

					Map<String, Resource> resources = new HashMap<>();
					Resource resource = new Resource();
					Path path2 = Paths.get(fileServer, "logo.jpg");
					File file = path2.toFile();
					resource.setImageBytes(FileUtils.readFileToByteArray(file));
					resource.setContentType("image/png");
					resources.put("logo.png", resource);

					emailutil = new EmailUtil();

					if (micorrreo.equals("L")) {
						emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
								entidad.getCorreo_copia(), Plantilla, params, resources);
					} else {
						emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
								entidad.getCorreo_copia(), Plantilla, params, resources);
					}
				} catch (Exception ex) {
					docService.Documento_FlgServicioError(documento.getId_documento());
					logger.error("ERROR EN LA CREACIoN DE LA HOJA DE RUTA SGDD:");
					logger.error(ex.toString());
					ex.printStackTrace();
				}
			}
		}
	}

	// Job para mantener actualizadas las tablas ubigeo, y otras listas
	@Scheduled(cron = "0 0 3,23 * * *") // se ejecuta a las 3AM y 11PM
	public void ScheduletDocumentSGDD2() throws IOException {
		System.out.println("-----ScheduletDocumentSGDD2-------");
		System.out.println("EJECUTANDOSE SINCRONIZACION DE LISTAS: " + (new Date(System.currentTimeMillis())));

		Auditoria auditoria = new Auditoria();
		try {

			VentanillastdProxy proxy = new VentanillastdProxy();
			UnidadesOrganicasTreeDto[] mioficinas = proxy.unidadesOrganicas();
			oficinaService.actualizarOficinasDesdeServicio(mioficinas);

			// VentanillastdProxy proxy = new VentanillastdProxy();
			AcMsUbigwsDto[] ubigeos = ventanillastdProxy.ubigeos();

			String cadena = "";
			int cuenta = 0;
			for (int i = 0; i < ubigeos.length; i++) {
				AcMsUbigwsDto ubi = ubigeos[i];
				if (ubi.getCodprov().equals(0) && ubi.getCoddist().equals(0)) {
					if (cuenta != 0) {
						cadena += "=";
					}
					int id_dep = ubi.getCoddpto();
					String Descripcion = ubi.getNombre().isEmpty() ? "-" : ubi.getNombre();

					cadena += id_dep + "|" + Descripcion;
					cuenta++;

					if (cadena.length() > 3500) {
						auditoria = mefService.Mef_Dep_Actualizar(cadena);
						cadena = "";
						cuenta = 0;
					}
				}
			}
			if (cadena != "") {
				auditoria = mefService.Mef_Dep_Actualizar(cadena);
				cadena = "";
				cuenta = 0;
			}

			cadena = "";
			cuenta = 0;
			for (int i = 0; i < ubigeos.length; i++) {
				AcMsUbigwsDto ubi = ubigeos[i];
				if (!ubi.getCodprov().equals(0) && ubi.getCoddist().equals(0)) {
					if (cuenta != 0) {
						cadena += "=";
					}
					int id_prov = ubi.getCodprov();
					int id_dep = ubi.getCoddpto();
					String Descripcion = ubi.getNombre().isEmpty() ? "-" : ubi.getNombre();

					cadena += id_prov + "|" + id_dep + "|" + Descripcion;
					cuenta++;

					if (cadena.length() > 3500) {
						auditoria = mefService.Mef_Prov_Actualizar(cadena);
						cadena = "";
						cuenta = 0;
					}
				}
			}
			if (cadena != "") {
				auditoria = mefService.Mef_Prov_Actualizar(cadena);
				cadena = "";
				cuenta = 0;
			}
			// ubigeos = null;

			cadena = "";
			cuenta = 0;
			for (int i = 0; i < ubigeos.length; i++) {
				AcMsUbigwsDto ubi = ubigeos[i];
				if (!ubi.getCoddist().equals(0)) {
					if (cuenta != 0) {
						cadena += "=";
					}
					int id_dist = ubi.getCoddist();
					int id_prov = ubi.getCodprov();
					int id_dep = ubi.getCoddpto();
					String Descripcion = ubi.getNombre().isEmpty() ? "-" : ubi.getNombre();
					cadena += id_dist + "|" + id_prov + "|" + id_dep + "|" + Descripcion;
					cuenta++;

					if (cadena.length() > 3500) {

						auditoria = mefService.Mef_Dist_Actualizar(cadena);
						cadena = "";
						cuenta = 0;
					}
				}
			}
			if (cadena != "") {
				auditoria = mefService.Mef_Dist_Actualizar(cadena);
				cadena = "";
				cuenta = 0;
			}
			ubigeos = null;

			System.out.println("FIN DE SINCRONIZACION: " + (new Date(System.currentTimeMillis())));
		} catch (Exception ex) {
			// docService.Documento_FlgServicioError(documento.getId_documento());
			System.out.println("ERROR EN LA CREACIoN DE LA HOJA DE RUTA SGDD:");
			System.out.println(ex.getMessage());
		}

	}

	@Scheduled(cron = "0 10 8,12,13,19,22 * * *")
	public void ScheduletDocumentSGDD5() throws IOException {

		System.out.println("-----ScheduletDocumentSGDD5-------");

		System.out.println("ACTUALIZACION DE ESTADOS PARA EL TAB_OBSERVADO "
				+ (new Date(System.currentTimeMillis())));

		Auditoria documentosPorRecibir = docService.Documento_Listar_Pendiente_Bandeja("TAB_OBSERVADO");
		// System.out.println("entro aqui 1");
		if (documentosPorRecibir.ejecucion_procedimiento && !documentosPorRecibir.rechazar) {
			List<Documento> lista = (List<Documento>) documentosPorRecibir.objeto;

			// lista = lista.stream().filter( n-> n.getId_documento() ==
			// 347421).collect(Collectors.toList());

			// System.out.println("entro aqui 1");
			System.out.println("Lista de :" + lista.size());
			for (Documento documento : lista) {
				try {
					System.out.println("Solicitud a reparar:" + documento.getId_documento());
					ArrayList<AnexoDto> anexoDto = new ArrayList<AnexoDto>();
					ArrayList<AnexoDto> anexoDtoPrincipal = new ArrayList<AnexoDto>();

					IdValorDto idValorDto = new IdValorDto();
					System.out.println("Datos del documento: id:" + documento.getId_documento());
					System.out.println("sid:" + documento.getNumero_sid());
					System.out.println("anio:" + documento.getAnio());

					// VentanillastdProxy proxy = new VentanillastdProxy();
					String USU = "lmauricio";
					String IP = "10.10.10.10";
					// System.out.println("RUC: " + ruc);
					// System.out.println("IP: " + IP);

					idValorDto = ventanillastdProxy.estadoDeExpediente(USU, documento.getNumero_sid(),
							documento.getAnio(), IP);

					ObjectMapper mapper = new ObjectMapper();
					System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(idValorDto));

					if (idValorDto != null) {

						if (idValorDto.getValor().toUpperCase().equals("PROCESO") && idValorDto.getId().equals(1L)) { // POR
							// RECIBIR

							docService.Actualizar_Estado(documento.getId_documento(), 7, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("PROCESO") && idValorDto.getId().equals(2L)) { // RECIBIDO

							docService.Actualizar_Estado(documento.getId_documento(), 3, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("EN PROCESO") && idValorDto.getId().equals(3L)) { // RECIBIDO

							docService.Actualizar_Estado(documento.getId_documento(), 3, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("OBSERVADO") && idValorDto.getId().equals(1L)) { // OBSERVADO

							docService.Actualizar_Estado(documento.getId_documento(), 2, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("NO PRESENTADO")
								&& (idValorDto.getId().equals(6L) || idValorDto.getId().equals(7L))) { // ANULADO

							docService.Actualizar_Estado(documento.getId_documento(), 6, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("FINALIZADO") && idValorDto.getId().equals(4L)) { // FINALIZADO

							docService.Actualizar_Estado(documento.getId_documento(), 5, "");
						}

					}

					// System.out.println("RUC: " + ruc);

				} catch (Exception ex) {
					// docService.Documento_FlgServicioError(documento.getId_documento());
					System.out.println(ex.getMessage());
					docService.Actualizar_Estado(documento.getId_documento(), 100, ex.toString());
					logger.error("ERROR EN EL SERVICIO DE ESTADO SGDD: " + documento.getId_documento());
					// System.out.println(ex.toString());
				}
			}
		}
	}

	@Scheduled(cron = "0 0 1,5 * * *") // 1 am 5 am
	public void ScheduletDocumentSGDD6() throws IOException {

		System.out.println("-----ScheduletDocumentSGDD6-------");

		System.out.println("ACTUALIZACION DE ESTADOS PARA EL TAB TAB_PENDIENTE: "
				+ (new Date(System.currentTimeMillis())));

		Auditoria documentosPorRecibir = docService.Documento_Listar_Pendiente_Bandeja("TAB_PENDIENTE");
		// System.out.println("entro aqui 1");
		if (documentosPorRecibir.ejecucion_procedimiento && !documentosPorRecibir.rechazar) {
			List<Documento> lista = (List<Documento>) documentosPorRecibir.objeto;
			// System.out.println("entro aqui 1");
			System.out.println("Lista de :" + lista.size());
			for (Documento documento : lista) {
				try {
					System.out.println("Solicitud a reparar:" + documento.getId_documento());
					ArrayList<AnexoDto> anexoDto = new ArrayList<AnexoDto>();
					ArrayList<AnexoDto> anexoDtoPrincipal = new ArrayList<AnexoDto>();

					IdValorDto idValorDto = new IdValorDto();
					System.out.println("Datos del documento: id:" + documento.getId_documento());
					System.out.println("sid:" + documento.getNumero_sid());
					System.out.println("anio:" + documento.getAnio());

					// VentanillastdProxy proxy = new VentanillastdProxy();
					String USU = "lmauricio";
					String IP = "10.10.10.10";
					// System.out.println("RUC: " + ruc);
					// System.out.println("IP: " + IP);

					idValorDto = ventanillastdProxy.estadoDeExpediente(USU, documento.getNumero_sid(),
							documento.getAnio(), IP);

					ObjectMapper mapper = new ObjectMapper();
					System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(idValorDto));

					if (idValorDto != null) {

						if (idValorDto.getValor().toUpperCase().equals("PROCESO") && idValorDto.getId().equals(1L)) { // POR
							// RECIBIR

							docService.Actualizar_Estado(documento.getId_documento(), 7, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("PROCESO") && idValorDto.getId().equals(2L)) { // RECIBIDO

							docService.Actualizar_Estado(documento.getId_documento(), 3, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("EN PROCESO") && idValorDto.getId().equals(3L)) { // RECIBIDO

							docService.Actualizar_Estado(documento.getId_documento(), 3, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("OBSERVADO") && idValorDto.getId().equals(1L)) { // OBSERVADO

							docService.Actualizar_Estado(documento.getId_documento(), 2, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("NO PRESENTADO")
								&& (idValorDto.getId().equals(6L) || idValorDto.getId().equals(7L))) { // ANULADO

							docService.Actualizar_Estado(documento.getId_documento(), 6, "");
						}
						if (idValorDto.getValor().toUpperCase().equals("FINALIZADO") && idValorDto.getId().equals(4L)) { // FINALIZADO

							docService.Actualizar_Estado(documento.getId_documento(), 5, "");
						}

					}

					// System.out.println("RUC: " + ruc);

				} catch (Exception ex) {
					// docService.Documento_FlgServicioError(documento.getId_documento());
					System.out.println(ex.getMessage());
					docService.Actualizar_Estado(documento.getId_documento(), 100, ex.toString());
					logger.error("ACTUALIZACION DE ESTADOS PARA EL TAB OBSERVADOS: " + documento.getId_documento());
					// System.out.println(ex.toString());
				}
			}
		}
	}

}
