package mef.application.controlador;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
//import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import mef.application.component.EmailComponent;
import mef.application.component.EmailUtil;
import mef.application.dto.Resource;
import mef.application.infrastructure.CommonHelpers;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Casilla;
import mef.application.modelo.CasillaArchivo;
import mef.application.modelo.CasillaUno;
import mef.application.modelo.Documento;
import mef.application.modelo.DocumentoAnexo;
import mef.application.modelo.DocumentoEstado;
import mef.application.modelo.DocumentoFinalizar;
import mef.application.modelo.DocumentoGrilla;
import mef.application.modelo.DocumentoObservacion;
import mef.application.modelo.EnlaceEncuesta;
import mef.application.modelo.PersonaCorreo;
import mef.application.modelo.RespuestaMessage;
import mef.application.modelo.UsuarioPersona;
import mef.application.modelo.entity.DocumentoAnexoEntity;
import mef.application.modelo.entity.DocumentoAnexoPK;
import mef.application.modelo.entity.DocumentoEntity;
import mef.application.repositorio.DocumentoAnexoRepository;
import mef.application.repositorio.DocumentoRepository;
import mef.application.service.CasillaService;
import mef.application.service.DocumentoService;
import mef.application.service.EnlaceEncuestaService;
import mef.application.service.EstadoService;
import mef.application.service.FilesStorageService;
import mef.application.service.MefService;
import mef.application.service.PersonaService;
import pe.gob.mef.std.bs.web.ws.AnexoDto;
import pe.gob.mef.std.bs.web.ws.ErrorInfo;
import pe.gob.mef.std.bs.web.ws.HrDto;
import pe.gob.mef.std.bs.web.ws.IdValorDto;
import pe.gob.mef.std.bs.web.ws.TaFeriadosDto;
import pe.gob.mef.std.bs.web.ws.TdFlujoSDto;
import pe.gob.mef.std.bs.web.ws.VentanillastdProxy;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DocumentoControlador {
	private static final Logger logger = LoggerFactory.getLogger(DocumentoControlador.class);

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

	@Autowired
	private DocumentoAnexoRepository documentoAnexoRepository;

	@Autowired
	private DocumentoRepository documentoRepository;

	private EmailUtil emailutil;
	private final VentanillastdProxy ventanillastdProxy;

	public DocumentoControlador(EmailComponent emailComponent, VentanillastdProxy ventanillastdProxy) {
		this.emailComponent = emailComponent;
		this.ventanillastdProxy = ventanillastdProxy;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/listamisdocumentos")
	public ResponseEntity<Auditoria> DocumentoSolicitud_Listar(@Valid @RequestBody DocumentoGrilla doc) {
		Auditoria auditoria = new Auditoria();
		try {
			doc.setId_usuario(new UserIdentityHelper().getUserId() + "");
			auditoria = docService.DocumentoSolicitud_Listar(doc);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/listdoc3")
	public ResponseEntity<List> Documento_Listar(@Valid @RequestBody Documento doc) {
		Auditoria auditoria = new Auditoria();
		try {
			int TipoUsuario = new UserIdentityHelper().getTipoUsuario();
			if (TipoUsuario == 2)
				doc.setId_usuario(new UserIdentityHelper().getUserId() + "");
			else
				doc.setId_usuario("");

			auditoria = docService.Documento_Listar(doc);
			JSONArray jsonArray = new JSONArray();
			if (auditoria.ejecucion_procedimiento) {
				List<Documento> lista = (List<Documento>) auditoria.objeto;
				for (Documento documento : lista) {

					JSONObject jsonObject = new JSONObject();

					jsonObject.put("id_documento", documento.getId_documento());
					jsonObject.put("codigo_archivo", documento.getCodigo_archivo());
					jsonObject.put("asunto", documento.getAsunto());
					jsonObject.put("flg_estado", documento.getFlg_estado());
					jsonObject.put("id_estado_documento", documento.getId_estado_documento());
					jsonObject.put("estado_documento", documento.getDesc_estado_documento());
					// estadoService.getEstado(documento.getId_estado_documento()).getDesc_estado());

					jsonObject.put("desc_oficina", documento.getDesc_oficina());
					jsonObject.put("desc_tipo_documento", documento.getDesc_tipo_documento());
					jsonObject.put("nro_documento", documento.getNro_documento());
					jsonObject.put("nro_folios", documento.getNro_folios());
					jsonObject.put("nro_anexos", documento.getCuenta_anexos());
					jsonObject.put("srt_fecha_creacion", documento.getFec_creacion());
					jsonObject.put("srt_fecha_recibido", documento.getSrt_fecha_recibido());
					jsonObject.put("anio", documento.getAnio());
					jsonObject.put("numero_sid", documento.getNumero_sid());
					jsonObject.put("nombre_persona", documento.getNombre_persona());
					jsonObject.put("ip_creacion", documento.getIp_creacion());

					jsonArray.put(jsonObject);
				}
			}

			return new ResponseEntity<>(jsonArray.toList(), HttpStatus.OK);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
			// e.getStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/listdoc-paginado")
	public ResponseEntity<String> Documento_Listar_paginado(@Valid @RequestBody Documento doc) {
		Auditoria auditoria = new Auditoria();
		System.out.println(" razon social: " + doc.getRazon_social());
		System.out.println(" destino: " + doc.getDestino());

		try {
			int TipoUsuario = new UserIdentityHelper().getTipoUsuario();
			if (TipoUsuario == 2)
				doc.setId_usuario(new UserIdentityHelper().getUserId() + "");
			else
				doc.setId_usuario("");

			auditoria = docService.Documento_Listar_Paginado(doc);
			JSONObject data = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			data.put("totalreg", doc.getTotalreg());

			if (auditoria.ejecucion_procedimiento) {
				List<Documento> lista = (List<Documento>) auditoria.objeto;
				for (Documento documento : lista) {
					if (Integer.parseInt(documento.getFlg_estado()) != 0) {
						data.put("totalreg", documento.getTotalreg());
						JSONObject jsonObject = new JSONObject();

						jsonObject.put("id_documento", documento.getId_documento());
						jsonObject.put("codigo_archivo", documento.getCodigo_archivo());
						jsonObject.put("asunto", documento.getAsunto());
						jsonObject.put("flg_estado", documento.getFlg_estado());
						jsonObject.put("id_estado_documento", documento.getId_estado_documento());
						jsonObject.put("estado_documento", documento.getDesc_estado_documento());
						// estadoService.getEstado(documento.getId_estado_documento()).getDesc_estado());

						jsonObject.put("desc_oficina", documento.getDesc_oficina());
						jsonObject.put("desc_tipo_documento", documento.getDesc_tipo_documento());
						jsonObject.put("nro_documento", documento.getNro_documento());
						jsonObject.put("nro_folios", documento.getNro_folios());
						jsonObject.put("nro_anexos", documento.getCuenta_anexos());
						jsonObject.put("srt_fecha_creacion", documento.getFec_creacion());
						jsonObject.put("srt_fecha_recibido", documento.getSrt_fecha_recibido());
						jsonObject.put("srt_fecha_modificacion", documento.getStr_fec_modificacion());
						jsonObject.put("anio", documento.getAnio() == 0 ? "" : documento.getAnio());
						jsonObject.put("numero_sid", documento.getNumero_sid());
						jsonObject.put("nombre_persona", documento.getNombre_persona());
						jsonObject.put("ip_creacion", documento.getIp_creacion());
						jsonObject.put("hoja_ruta_referencial", documento.getHoja_ruta());
						jsonObject.put("numreg", documento.getNumreg());
						jsonObject.put("fecha_anulacion", documento.getFecha_anulacion());
						jsonObject.put("fecha_observacion", documento.getFecha_observacion());
						jsonObject.put("fecha_subsanacion", documento.getFecha_subsanacion());
						jsonObject.put("usu_asignacion", documento.getUsu_asignacion());
						jsonObject.put("usu_asignacion", documento.getUsu_asignacion());
						jsonObject.put("esta_asignado", documento.isAsignado());
						jsonObject.put("nomb_usu_asignacion", documento.getNomb_usu_asignacion());
						jsonObject.put("obsSgdd", documento.getObsSgdd());
						jsonArray.put(jsonObject);
					}

				}
			}

			data.put("numpag", doc.getNumpag());
			data.put("numreg", doc.getNumreg());

			data.put("rows", jsonArray);
			return new ResponseEntity<>(data.toString(), HttpStatus.OK);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
			// e.getStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/listadocatendido")
	public ResponseEntity<List> DocumentoAtendido_Listar(@Valid @RequestBody Documento doc) {
		//
		Auditoria auditoria = new Auditoria();
		try {
			int TipoUsuario = new UserIdentityHelper().getTipoUsuario();
			if (TipoUsuario == 2)
				doc.setId_usuario(new UserIdentityHelper().getUserId() + "");
			else
				doc.setId_usuario("");

			auditoria = docService.Documento_Listar(doc);
			JSONArray jsonArray = new JSONArray();
			if (auditoria.ejecucion_procedimiento) {
				List<Documento> lista = (List<Documento>) auditoria.objeto;
				if (lista.size() > 0) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					for (Documento documento : lista) {
						try {
							IdValorDto retorno = ventanillastdProxy.estadoDeExpediente("mchavez",
									documento.getNumero_sid(),
									documento.getAnio(), new UserIdentityHelper().getClientIpAddress());
							// if(retorno.getTypeDesc())
							if (retorno.getId().equals(4)) {

								JSONObject jsonObject = new JSONObject();

								jsonObject.put("id_documento", documento.getId_documento());
								jsonObject.put("codigo_archivo", documento.getCodigo_archivo());
								jsonObject.put("asunto", documento.getAsunto());
								jsonObject.put("flg_estado", documento.getFlg_estado());
								jsonObject.put("id_estado_documento", documento.getId_estado_documento());
								jsonObject.put("estado_documento", documento.getDesc_estado_documento());

								jsonObject.put("desc_oficina", documento.getDesc_oficina());
								jsonObject.put("desc_tipo_documento", documento.getDesc_tipo_documento());
								jsonObject.put("nro_documento", documento.getNro_documento());
								jsonObject.put("nro_folios", documento.getNro_folios());
								jsonObject.put("nro_anexos", documento.getCuenta_anexos());
								jsonObject.put("srt_fecha_creacion", documento.getFec_creacion());
								jsonObject.put("srt_fecha_recibido", documento.getSrt_fecha_recibido());
								jsonObject.put("anio", documento.getAnio());
								jsonObject.put("numero_sid", documento.getNumero_sid());
								jsonObject.put("nombre_persona", documento.getNombre_persona());
								jsonObject.put("ip_creacion", documento.getIp_creacion());

								jsonArray.put(jsonObject);
							}
						} catch (Exception ex) {
							auditoria.Error(ex);
							System.out.println(auditoria.error_log);
							// e.getStackTrace();
							// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
						}
					}
				} else {
					JSONObject jsonObject = new JSONObject();
					jsonArray.put(jsonObject);
				}
			}

			return new ResponseEntity<>(jsonArray.toList(), HttpStatus.OK);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
			// e.getStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/reporte")
	public ResponseEntity<InputStreamResource> getExportarReporte(@Valid @RequestBody Documento doc) {
		ByteArrayInputStream stream = null;
		HttpHeaders headers = new HttpHeaders();

		// headers.add("Content-Disposition", "attachment; filename=Res");
		headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		headers.add("Content-Disposition", "inline; filename=foo.xlsx");
		try {
			int TipoUsuario = new UserIdentityHelper().getTipoUsuario();
			if (TipoUsuario == 2)
				doc.setId_usuario(new UserIdentityHelper().getUserId() + "");
			else
				doc.setId_usuario("");
			stream = docService.exportDocuments(doc);

			stream.close();
			System.out.println("ENTRO XD 6");
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}

	private void marcarDocumentoComoNoGeneradoHR(Long documentoId, String mensaje) {

		Optional<DocumentoEntity> optionalDocumento = documentoRepository.findById(documentoId);
		if (optionalDocumento.isPresent()) {
			DocumentoEntity documentoEnti = optionalDocumento.get();
			documentoEnti.setObsSgdd(mensaje);
			documentoRepository.save(documentoEnti);
			docService.Documento_FlgServicioError(documentoId.intValue());
		}

	}

	@PostMapping("/registrar-solicitud")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Insertar(@Valid @ModelAttribute Documento doc) {
		Auditoria auditoria = new Auditoria();

		Integer totalFiles = 1;
		Integer totalFilesUploaded = 1;
		try {
			// DocumentoAnexo anexo = new DocumentoAnexo();

			String message = "";
			String anexosHR = "";
			String hojaRuta = "";
			String plantillaCorreo = "email/SGDD_documento_crear";
			int documentoId = 0;
			String filename = UUID.randomUUID().toString();
			String IP = UserIdentityHelper.getClientIpAddress();
			doc.setIp_creacion(UserIdentityHelper.getClientIpAddress());
			doc.setId_estado_documento(1);
			doc.setFlg_estado("1");
			doc.setCodigo_archivo(filename);
			doc.setUsu_creacion(UserIdentityHelper.get_CodigoUsuario());
			doc.setId_usuario(UserIdentityHelper.getUserId() + "");
			doc.setId_estado_documento(8);
			HrDto expediente = new HrDto();
			auditoria = docService.Documento_Insertar(doc);

			if (auditoria.ejecucion_procedimiento) {

				if (!auditoria.rechazar) {
					documentoId = Integer.parseInt(auditoria.objeto.toString());

					try {

						// Datos de la persona
						Auditoria auditoriaPersona = personaService
								.UsuarioPersona_Listar(Long.valueOf(doc.getId_usuario()));
						UsuarioPersona mipersona = new UsuarioPersona();

						if (auditoriaPersona.ejecucion_procedimiento)
							mipersona = (UsuarioPersona) auditoriaPersona.objeto;

						// VentanillastdProxy proxy = new VentanillastdProxy();

						TdFlujoSDto[] oficinas = new TdFlujoSDto[1];
						TdFlujoSDto oficina = new TdFlujoSDto();

						oficina.setIdinstr(Long.valueOf(1));
						oficina.setIdunidadDestino(Long.valueOf(doc.getId_oficina()));
						oficina.setOriginal(true);
						oficinas[0] = oficina;

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

						// Prepara Anexo Principal
						Path path = Paths.get(fileServer, documentoId + "");
						Files.createDirectories(path);
						filename = CommonHelpers.Generar_Nombre_Archivo(doc.getFile(), filename);
						storageService.save(path.toString(), doc.getFile(), filename);
						ArrayList<AnexoDto> anexoDtoPrincipal = new ArrayList<AnexoDto>();
						byte[] fileByte = doc.getFile().getBytes();
						anexoDtoPrincipal
								.add(new AnexoDto(fileByte, fileByte.length, filename));

						String USU = "lmauricio";
						System.out.println("Creando Expediente:");
						// Crear expediente en el SGDD

						if (doc.getAnexoslink() != null) {
							for (String file_link : doc.getAnexoslink()) {

								DocumentoAnexoEntity documentoItem = new DocumentoAnexoEntity();
								if (documentoItem.getId() == null) {
									documentoItem.setId(new DocumentoAnexoPK());
								}

								documentoItem.getId().setIdDocumento((long) documentoId);
								Long siguienteOrden = documentoAnexoRepository
										.obtenerSiguienteOrden(documentoItem.getId().getIdDocumento());
								documentoItem.getId().setOrden(siguienteOrden);

								documentoItem.setNombreArchivo(file_link);
								documentoItem.setCodigoArchivo(file_link);
								documentoItem.setTamanioArchivo((long) 0);
								documentoItem.setExtensionArchivo("-");
								documentoItem.setMimeTypeArchivo("-");
								documentoItem.setFlagLink("2");
								documentoItem.setUsuarioCreacion(new UserIdentityHelper().getName());
								documentoAnexoRepository.save(documentoItem);
							}
							anexosHR = String.join(" , ", doc.getAnexoslink());
						}

						if (doc.getFilesAnexos() != null) {
							for (MultipartFile file : doc.getFilesAnexos()) {
								HrDto anexoSoap = null;
								filename = UUID.randomUUID().toString();
								filename = CommonHelpers.Generar_Nombre_Archivo(file, filename);

								DocumentoAnexoEntity documentoAnexoItem = new DocumentoAnexoEntity();
								if (documentoAnexoItem.getId() == null) {
									documentoAnexoItem.setId(new DocumentoAnexoPK());
								}
								documentoAnexoItem.getId().setIdDocumento((long) documentoId);
								Long siguienteOrden = documentoAnexoRepository
										.obtenerSiguienteOrden(documentoAnexoItem.getId().getIdDocumento());
								documentoAnexoItem.getId().setOrden(siguienteOrden);

								documentoAnexoItem.setCodigoArchivo(filename);
								documentoAnexoItem
										.setExtensionArchivo(FilenameUtils.getExtension(file.getOriginalFilename())
												.toLowerCase());
								documentoAnexoItem.setNombreArchivo(file.getOriginalFilename());
								documentoAnexoItem.setUsuarioCreacion(new UserIdentityHelper().getName());
								documentoAnexoItem.setTamanioArchivo((long) file.getBytes().length);

								String mimetype = FilenameUtils.getExtension(file.getContentType()).toLowerCase();
								documentoAnexoItem.setMimeTypeArchivo(mimetype);
								documentoAnexoItem.setFlagLink("1");
								documentoAnexoItem.setEstadoAnexo(0);

								documentoAnexoItem = documentoAnexoRepository.save(documentoAnexoItem);
								storageService.save(path.toString(), file, filename);
							}
						}

						expediente = ventanillastdProxy.crearExpediente(
								USU,
								auditoria.objeto.toString() + "",
								Long.valueOf(doc.getId_tipo_documento()),
								doc.getNro_documento(),
								doc.getNro_folios(),
								doc.getAsunto(),
								apellido_paterno,
								apellido_materno,
								nombre,
								dni,
								mipersona.getTelefono(),
								razon_social,
								ruc,
								mipersona.getDireccion(),
								mipersona.getDesc_departamento(),
								mipersona.getDesc_provincia(),
								mipersona.getDesc_distrito(),
								mipersona.getCorreo(),
								anexoDtoPrincipal.toArray(new AnexoDto[anexoDtoPrincipal.size()]),
								IP,
								oficinas,
								null,
								null,
								new long[0],
								0,
								anexosHR);
						if (expediente != null) {

							System.out.println("Datos de retorno: ");
							System.out.println("getNumeroDoc:" + expediente.getNumeroDoc());
							System.out.println("getNumeroSid:" + expediente.getNumeroSid());
							System.out.println("getIddoc:" + expediente.getIddoc());
							System.out.println("getFechaCompleto:" + expediente.getFechaCompleto());
							hojaRuta = expediente.getNumeroSid() + "-" + expediente.getNumeroAnio().toString();
							message = expediente.getNumeroSid().isEmpty() ? ""
									: "La solicitud " + documentoId + " con hoja de ruta " + hojaRuta
											+ " se ha generado correctamente";

							docService.Documento_Agregar_HojaRuta(Integer.valueOf(auditoria.objeto.toString()),
									expediente.getNumeroAnio(), expediente.getNumeroSid(), USU);

							// Logica para Anexos
							AnexoDto[] anexoDto = new AnexoDto[doc.getFilesAnexos().length];

							totalFiles += anexoDto.length;
							System.out.println("Total anexos: " + totalFiles);

							List<DocumentoAnexo> anexos = docService.getAnexosDocumentoById(documentoId);

							System.out.println("total files totalFaileFiles");

							for (DocumentoAnexo itemAnexo : anexos) {

								filename = itemAnexo.getCodigo_archivo();
								path = Paths.get(fileServer, documentoId + "", filename);
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
														itemAnexo.getNombre_archivo()),
												IP);
										// Aquí sabes que sería un "HTTP 200 OK"
										System.out.println("Anexo agregado correctamente: " + anexo);

										documentoItem.setEstadoAnexo(1);
										documentoItem.setIdAnexo(anexo.getIdanexo().toString());

										documentoAnexoRepository.save(documentoItem);

										 totalFilesUploaded += 1;
									} catch (Exception e) {
										// Otro error inesperado
										auditoria.Error(e);
										logger.info(auditoria.error_log);
										documentoItem.setEstadoAnexo(0);
										documentoItem.setIdAnexo(null);
										documentoAnexoRepository.save(documentoItem);
									}

								}

							}

						}

					} catch (Exception ex) {
						docService.Documento_FlgServicioError(documentoId);
						ObjectMapper mapper = new ObjectMapper();
						logger.error("ERROR EN LA CREACION DE LA HOJA DE RUTA SGDD:");
						logger.error(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expediente));
						plantillaCorreo = "email/SGDD_documento_crear_sin_HR";
						marcarDocumentoComoNoGeneradoHR(Long.valueOf(documentoId), "No se genero HR");
						auditoria.ejecucion_procedimiento = false;
						auditoria.mensaje_salida = message.isEmpty()
								? "La solicitud " + documentoId + " se ha generado correctamente, la Generacion de HR esta pendiente"
								: message;
						auditoria.Error(ex);
						logger.error(auditoria.error_log);
					}

					Casilla modelo = new Casilla();
					modelo.setId_documento(Long.valueOf(documentoId + ""));

					auditoria = docService.Documento_ListarUno(documentoId);
					Documento documento = (Documento) auditoria.objeto;

					modelo.setId_persona(Long.valueOf(documento.getId_persona()));
					modelo.setId_usuario(new UserIdentityHelper().getUserId());

					auditoria = personaService.Persona_Correo(modelo.getId_persona());
					PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;

					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

					String mimensaje = formatter.format(date);
					mimensaje = mimensaje.toUpperCase();
					Map<String, Object> params = new HashMap<>();
					params.put("idsolicitud", documentoId + "-" + formattery.format(date));
					params.put("hoja_ruta", hojaRuta);
					params.put("fec_registro", documento.getFec_creacion());
					params.put("nombre", entidad.getNombrepersona());
					params.put("mensaje", documento.getFec_creacion());
					params.put("imageResourceName", "logo.png");
					params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
					// String plantilla = "SGDD_documento_crear";
					String mensaje = emailComponent.getTemplate(plantillaCorreo, params);

					IP = new UserIdentityHelper().getClientIpAddress();
					String USUARIO = new UserIdentityHelper().get_CodigoUsuario();

					modelo.setIp_creacion(IP);
					modelo.setUsu_creacion(USUARIO);
					modelo.setObservacion(documento.getAsunto());
					modelo.setMensaje_html(mensaje);
					modelo.setFlg_notificacion(1);
					auditoria = casillaService.Casilla_Insertar(modelo);

					Map<String, Resource> resources = new HashMap<>();
					Resource resource = new Resource();
					Path path2 = Paths.get(fileServer, "logo.jpg");
					File file = path2.toFile();
					resource.setImageBytes(FileUtils.readFileToByteArray(file));
					resource.setContentType("image/png");
					resources.put("logo.png", resource);

					System.out.println("Correo:" + entidad.getCorreo());
					System.out.println("Correo CC:" + entidad.getCorreo_copia());
					// System.out.println("Plantilla:" + plantillaCorreo);
					// System.out.println("Plantilla:"+plantillaCorreo);
					emailutil = new EmailUtil();

					if (!"".equals(hojaRuta)) { // Valida que exista una Hoja de ruta
						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), plantillaCorreo, params, resources);
						} else {
							emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									entidad.getCorreo_copia(), plantillaCorreo, params, resources);
						}
					}

					auditoria.mensaje_salida = message.isEmpty()
							? "La solicitud " + documentoId + " se ha generado correctamente"
							: message;

					auditoria.mensaje_salida =
							expediente.getNumeroSid() == null ? auditoria.mensaje_salida + ", La Generacoin de HR esta pendiente" : auditoria.mensaje_salida;

					logger.info("totalFiles {}:", totalFiles);
					logger.info("totalFilesUploaded {}:", totalFilesUploaded);


					if (totalFiles != totalFilesUploaded) {
						marcarDocumentoComoNoGeneradoHR(Long.valueOf(documentoId), "No se mando los anexos completos");
						auditoria.mensaje_salida += ", pero en el proceso anexos no se han podido registrar";
					}
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			logger.error(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/finalizar-solicitud")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Finalizar(@Valid @ModelAttribute DocumentoFinalizar modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			String IP = new UserIdentityHelper().getClientIpAddress();

			DocumentoEstado documentoestado = new DocumentoEstado();
			documentoestado.setId_documento(modelo.getId_documento());
			documentoestado.setId_estado_documento(5);
			documentoestado.setUsu_modificacion(new UserIdentityHelper().get_CodigoUsuario());
			documentoestado.setIp_modificacion(IP);
			auditoria = docService.Documento_Estado(documentoestado);

			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {

					auditoria = docService.Documento_ListarUno(Integer.valueOf(modelo.getId_documento() + ""));
					Documento documento = (Documento) auditoria.objeto;
					auditoria = personaService.Persona_Correo(Long.valueOf(documento.getId_persona()));
					PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;
					Map<String, Object> params = new HashMap<>();
					params.put("nombre", entidad.getNombrepersona());
					params.put("tipoper", entidad.getDesctipopersona());
					params.put("tipodoc", entidad.getDesctipodocumento());
					params.put("documento", entidad.getNrodocumento());
					params.put("mensaje", modelo.getMensaje());
					params.put("nro_solicitud", documento.getId_documento());
					params.put("nro_documento", documento.getNro_documento());
					params.put("asunto", documento.getAsunto());

					params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
					String Plantilla = "email/documento";
					params.put("imageResourceName", "logo.png");
					String mensaje = emailComponent.getTemplate(Plantilla, params);

					Casilla casilla = new Casilla();
					casilla.setId_usuario(Integer.valueOf(documento.getId_usuario()));
					casilla.setId_documento(modelo.getId_documento());
					casilla.setObservacion(modelo.getMensaje());
					casilla.setMensaje_html(mensaje);
					casilla.setUsu_creacion(new UserIdentityHelper().get_CodigoUsuario());
					casilla.setIp_creacion(IP);
					auditoria.Limpiar();
					// auditoria = casillaService.Casilla_Insertar(casilla);

					Path path = Paths.get(fileServer, modelo.getId_documento() + "");

					if (auditoria.ejecucion_procedimiento) {
						if (!auditoria.rechazar) {
							// Long id_casilla = (Long) auditoria.objeto;
							Map<String, String> paramsFile = new HashMap<>();
							if (modelo.getArchivos() != null)
								for (MultipartFile file : modelo.getArchivos()) {
									CasillaArchivo miarchivo = new CasillaArchivo();
									String filename = UUID.randomUUID().toString();
									filename = CommonHelpers.Generar_Nombre_Archivo(file, filename);
									// miarchivo.setId_casilla(id_casilla);
									miarchivo.setNombre_archivo(file.getOriginalFilename());
									miarchivo.setCodigo_archivo(filename);
									miarchivo.setTamanio_archivo(file.getBytes().length);
									miarchivo.setExtension_archivo(
											FilenameUtils.getExtension(file.getOriginalFilename()));
									miarchivo.setUsu_creacion(new UserIdentityHelper().get_CodigoUsuario());
									miarchivo.setIp_creacion(IP);

									auditoria.Limpiar();// = casillaService.CasillaArchivo_Insertar(miarchivo);

									paramsFile.put(file.getOriginalFilename(), path.toString() + "/" + filename);
									if (auditoria.ejecucion_procedimiento)
										if (!auditoria.rechazar)
											storageService.save(path.toString(), file, filename);
										else
											break;
									else
										break;
								}

							if (auditoria.ejecucion_procedimiento) {
								if (!auditoria.rechazar) {
									Resource resource = new Resource();
									Path path2 = Paths.get(fileServer, "logo.jpg");
									File file = path2.toFile();
									resource.setImageBytes(FileUtils.readFileToByteArray(file));

									// resource.setImageBytes(Files.readAllBytes(Paths.get(fileServer
									// ,"logo.jpg")));
									resource.setContentType("image/png");

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
							}
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

	@PostMapping("/actualizar-solicitud")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Actualizar(@Valid @ModelAttribute Documento doc) {
		Auditoria auditoria = new Auditoria();

		try {
			Auditoria docResponse = docService.Documento_ListarUno(doc.getId_documento());
			if (docResponse.ejecucion_procedimiento) {
				if (!docResponse.rechazar) {
					Documento documento = (Documento) docResponse.objeto;
					ArrayList<AnexoDto> anexoDto = new ArrayList<AnexoDto>();
					ArrayList<DocumentoAnexo> anexos = new ArrayList<DocumentoAnexo>();
					DocumentoAnexo anexo = new DocumentoAnexo();
					HrDto expediente = new HrDto();
					// VentanillastdProxy proxy = new VentanillastdProxy();
					Path path;
					Integer totalFaileFiles = 0;
					String message = "";
					String USU = "lmauricio";
					String filenameAnexo = "";
					String filename = "";
					String anexosHR = "";
					byte[] fileByte = null;
					List<String> anexosLink = doc.getAnexoslink() != null ? doc.getAnexoslink()
							: new ArrayList<String>();

					documento.setUsu_modificacion(UserIdentityHelper.get_CodigoUsuario());
					// documento.setId_oficina(doc.getId_oficina());
					// documento.setNro_folios(doc.getNro_folios());
					// documento.setAsunto(doc.getAsunto());
					// documento.setHoja_ruta(doc.getHoja_ruta());
					documento.setId_estado_documento(doc.getId_estado_documento());
					List<DocumentoAnexo> adjuntos = docService.getAnexosDocumentoById(documento.getId_documento());
					// File principal
					if (doc.getFile() == null) {
						/*
						 * filename = documento.getCodigo_archivo() + ".pdf"; path =
						 * Paths.get(fileServer, documento.getId_documento() + "", filename); fileByte =
						 * Files.readAllBytes(path); anexoDto.add(new AnexoDto(fileByte,
						 * fileByte.length, filename)); System.out.println("File Principal actual: " +
						 * fileByte.length);
						 */
					} else {
						filename = documento.getCodigo_archivo() + ".pdf";
						fileByte = doc.getFile().getBytes();
						anexoDto.add(new AnexoDto(fileByte, fileByte.length, filename));
						System.out.println("File Principal Cambiado: " + fileByte.length);
					}

					filename = filename.replace(".pdf", "");

					// Anexos cargados en el servidor de archivos
					/*
					 * if (doc.getCodigo_archivo_anexos() != null) for (int i = 0; i <
					 * doc.getCodigo_archivo_anexos().length; i += 2) {
					 * 
					 * if (doc.getCodigo_archivo_anexos()[i + 1].length() == 1) {
					 * anexosLink.add(doc.getCodigo_archivo_anexos()[i + 0]); } else { filenameAnexo
					 * = doc.getCodigo_archivo_anexos()[i] + "." + doc.getCodigo_archivo_anexos()[i
					 * + 1]; path = Paths.get(fileServer, documento.getId_documento() + "",
					 * filenameAnexo); fileByte = Files.readAllBytes(path); anexoDto.add(new
					 * AnexoDto(fileByte, fileByte.length, filenameAnexo));
					 * System.out.println("Files Anexo: " + fileByte.length); } }
					 */
					// text.contains(word);
					if (doc.getFilesAnexos() != null)
						for (MultipartFile file : doc.getFilesAnexos()) {
							filenameAnexo = UUID.randomUUID().toString();
							anexo.setId_documento(documento.getId_documento());
							anexo.setCodigo_archivo(filenameAnexo);
							filenameAnexo = CommonHelpers.Generar_Nombre_Archivo(file, filenameAnexo);

							anexo.setNombre_archivo(file.getOriginalFilename());
							anexo.setExtension_archivo(FilenameUtils.getExtension(file.getOriginalFilename()));
							String nuevonom = "";
							int ver = 0;
							if (adjuntos != null) {
								for (DocumentoAnexo filex : adjuntos) {
									if (filex.getNombre_archivo().contains(file.getOriginalFilename())) {
										ver = ver + 1;
										nuevonom = "_V" + ver;
									}
								}
							}
							if (!nuevonom.equals("")) {
								anexo.setNombre_archivo(
										file.getOriginalFilename().replace(anexo.getExtension_archivo(), "") + nuevonom
												+ "." + anexo.getExtension_archivo());
							}
							anexo.setUsu_creacion(UserIdentityHelper.getName());
							anexo.setTamanio_archivo(file.getBytes().length);
							String mimetype = FilenameUtils.getExtension(file.getContentType()).toLowerCase();
							anexo.setMimetype_archivo(mimetype);
							anexo.setFlg_link("1");
							anexos.add(anexo);
							anexoDto.add(
									new AnexoDto(file.getBytes(), file.getBytes().length, anexo.getNombre_archivo()));
							System.out.println("Files Anexo Enviado: " + file.getBytes().length);
						}

					anexosHR = String.join(" , ", anexosLink);
					System.out.println("anexosHR: " + anexosHR);

					// Oficinas
					TdFlujoSDto[] oficinas = new TdFlujoSDto[1];
					oficinas[0] = new TdFlujoSDto(Long.valueOf(1), Long.valueOf(doc.getId_oficina()), "", true);

					System.out.println("Total Anexos: " + anexoDto.size());

					expediente = ventanillastdProxy.levantarObservacion(USU, documento.getNumero_sid(),
							documento.getAnio(),
							documento.getNro_documento(), anexoDto.toArray(new AnexoDto[anexoDto.size()]),
							UserIdentityHelper.getClientIpAddress(), anexosHR);

					System.out.println("Paso levantarObservacion");
					auditoria = docService.Documento_Actualizar(documento);

					if (auditoria.ejecucion_procedimiento) {
						if (!auditoria.rechazar) {
							path = Paths.get(fileServer, documento.getId_documento() + "");
							Files.createDirectories(path);

							if (doc.getFile() != null)
								storageService.replace(path.toString(), doc.getFile(), filename + ".pdf");

							if (doc.getCodigo_archivo_anexos() != null) {
								ArrayList<String> codigoAnexo = new ArrayList<String>();
								for (int i = 0; i < doc.getCodigo_archivo_anexos().length; i += 2) {
									codigoAnexo.add(doc.getCodigo_archivo_anexos()[i]);

									// Eliminar el archivo fisico
									// filename
									// =doc.getCodigo_archivo_anexos()[i]+"."+doc.getCodigo_archivo_anexos()[i+1];
									// path = Paths.get(fileServer, documento.getId_documento()+"",filename);
									// File temp = new File(path.toString());
									// if(temp.exists()) temp.delete();
								}

								docService.anularAnexos(doc.getId_documento(), String.join(",", codigoAnexo),
										UserIdentityHelper.getName());
							} else {
								docService.anularAnexos(doc.getId_documento(), "", UserIdentityHelper.getName());
							}

							if (doc.getFilesAnexos() != null)
								for (MultipartFile file : doc.getFilesAnexos()) {
									filename = UUID.randomUUID().toString();
									anexo.setId_documento(documento.getId_documento());
									anexo.setCodigo_archivo(filename);
									filename = CommonHelpers.Generar_Nombre_Archivo(file, filename);
									anexo.setExtension_archivo(
											FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase());
									anexo.setNombre_archivo(file.getOriginalFilename());
									anexo.setUsu_creacion(UserIdentityHelper.getName());
									anexo.setTamanio_archivo(file.getBytes().length);
									String mimetype = FilenameUtils.getExtension(file.getContentType());
									anexo.setMimetype_archivo(mimetype.toLowerCase());
									anexo.setFlg_link("1");
									RespuestaMessage respuestaAnexo = docService.crearAnexo(anexo);

									if (respuestaAnexo.getCode().equals(100))
										storageService.save(path.toString(), file, filename);
									else
										totalFaileFiles++;
								}

							if (doc.getAnexoslink() != null) {
								for (String file_link : doc.getAnexoslink()) {
									anexo.setId_documento(documento.getId_documento());
									anexo.setNombre_archivo(file_link);
									anexo.setCodigo_archivo(file_link);
									anexo.setTamanio_archivo(0);
									anexo.setExtension_archivo("-");
									anexo.setMimetype_archivo("-");
									anexo.setFlg_link("2"); // link
									anexo.setUsu_creacion(UserIdentityHelper.getName());
									RespuestaMessage respuestaAnexo = docService.crearAnexo(anexo);
								}

							}

							docResponse = docService.Documento_ListarUno(documento.getId_documento());
							documento = (Documento) docResponse.objeto;

							Casilla modelo = new Casilla();
							modelo.setId_documento(documento.getId_documento());
							modelo.setId_persona(Long.valueOf(documento.getId_persona()));
							modelo.setId_usuario(UserIdentityHelper.getUserId());

							Auditoria personaResponse = personaService.Persona_Correo(modelo.getId_persona());
							PersonaCorreo entidad = (PersonaCorreo) personaResponse.objeto;

							Date date = new Date();
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

							String mimensaje = formatter.format(date);
							mimensaje = mimensaje.toUpperCase();
							Map<String, Object> params = new HashMap<>();
							params.put("idsolicitud", doc.getId_documento() + "-" + formattery.format(date));
							params.put("hoja_ruta", documento.getNumero_sid() + "-" + documento.getAnio());
							params.put("nombre", entidad.getNombrepersona());
							params.put("mensaje", documento.getStr_fec_modificacion());
							params.put("imageResourceName", "logo.png");
							params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
							String Plantilla = "email/documento_subsanado";
							String mensaje = emailComponent.getTemplate(Plantilla, params);

							modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
							modelo.setUsu_creacion(UserIdentityHelper.get_CodigoUsuario());
							modelo.setObservacion(documento.getAsunto());
							modelo.setMensaje_html(mensaje);
							modelo.setFlg_notificacion(1);
							auditoria = casillaService.Casilla_Insertar(modelo);

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

							auditoria.mensaje_salida = "La solicitud " + documento.getId_documento()
									+ " se ha subsanado correctamente";

							if (totalFaileFiles > 0)
								auditoria.mensaje_salida += ", pero en el proceso " + totalFaileFiles
										+ "archivo(s) no se han podido registrar";
						}
					}
				}
			}
		} catch (

		Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/obtener-solicitud/{id}")
	public ResponseEntity<Auditoria> Documento_ListarUno(@PathVariable(value = "id") Integer documentoId) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = docService.Documento_ListarUno(documentoId);
			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {
					Documento documento = (Documento) auditoria.objeto;
					if (documento != null)
						documento.setAnexos(docService.getAnexosDocumentoById(documentoId));
				}
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/export/{id}/{fileType}/{fileName}")
	public ResponseEntity<InputStreamResource> exportFile(@PathVariable("id") Integer id,
			@PathVariable("fileType") String fileType, @PathVariable("fileName") String fileName) {
		ByteArrayInputStream stream = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Reportes.pdf");
		headers.add("Content-Type", "application/octet-stream");
		try {
			Path path = Paths.get(fileServer, id.toString(), fileName + "." + fileType.toLowerCase());
			File file = path.toFile();// new File(path.);
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			stream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			stream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}

	@GetMapping("/exportsolo/{fileType}/{fileName}")
	public ResponseEntity<InputStreamResource> exportFileSolo(@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName) {
		ByteArrayInputStream stream = null;
		System.out.println("Observacion  : " + fileName);
		System.out.println("Observacion  : " + fileType);
		HttpHeaders headers = new HttpHeaders();
		// headers.add("Content-Disposition", "attachment; filename=Reportes.pdf");
		headers.add("Content-Type", "application/octet-stream");
		try {
			Path path = Paths.get(fileServer, fileName + "." + fileType.toLowerCase());
			File file = path.toFile();// new File(path.);
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			headers.add("Content-Disposition", "attachment; filename=" + fileName + "." + fileType.toLowerCase());
			stream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			stream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}

	@PostMapping("/recepcionar-solicitud")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Recepcionar(@Valid @RequestBody Documento documento) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String USU = UserIdentityHelper.get_CodigoUsuario();
			String IP = UserIdentityHelper.getClientIpAddress();
			HrDto expediente = new HrDto();
			auditoria = docService.Documento_ListarUno(documento.getId_documento());

			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {
					Documento doc = (Documento) auditoria.objeto;
					String nombre_persona = "";
					UsuarioPersona mipersona = new UsuarioPersona();

					try {
						auditoria = personaService.UsuarioPersona_Listar(Long.valueOf(doc.getId_usuario()));

						if (auditoria.ejecucion_procedimiento) {
							try {
								mipersona = (UsuarioPersona) auditoria.objeto;
								// VentanillastdProxy proxy = new VentanillastdProxy();

								Path path = Paths.get(fileServer, doc.getId_documento() + "",
										doc.getCodigo_archivo() + ".pdf");
								File file = path.toFile();
								byte[] fileByte = FileUtils.readFileToByteArray(file);
								List<DocumentoAnexo> milista = docService
										.getAnexosDocumentoById(documento.getId_documento());

								int tamanio_lista = 0;
								if (milista.size() > 0) {
									for (DocumentoAnexo anexos : milista) {
										if (anexos.getFlg_link().equals("1") || anexos.getFlg_link().equals("")) {
											tamanio_lista++;
										}
									}
								}

								AnexoDto[] anexoDto = new AnexoDto[tamanio_lista + 1];

								int count = 0;
								String LinksConcat = "";

								anexoDto[count] = new AnexoDto(fileByte, fileByte.length, file.getName());

								for (DocumentoAnexo anexos : milista) {
									if (anexos.getFlg_link().equals("1") || anexos.getFlg_link().equals("")) {

										try {
											count++;
											Path pathanexo = Paths.get(fileServer, doc.getId_documento() + "",
													anexos.getCodigo_archivo() + "."
															+ anexos.getExtension_archivo().toLowerCase());
											File fileanexo = pathanexo.toFile();
											byte[] fileByteanexo = FileUtils.readFileToByteArray(fileanexo);

											AnexoDto a = new AnexoDto();
											a.setArchivo(fileByteanexo);
											a.setLength(fileByteanexo.length);
											a.setName(anexos.getNombre_archivo());

											anexoDto[count] = a;

										} catch (OutOfMemoryError ex) {
											auditoria.Rechazar("Error en memoria por anexos grandes");
											auditoria.ejecucion_procedimiento = false;
										}
									} else if (anexos.getFlg_link().equals("2")) {
										if (LinksConcat.equals("")) {
											LinksConcat = anexos.getNombre_archivo();
										} else {
											LinksConcat = LinksConcat + " , " + anexos.getNombre_archivo();
										}

									}
								}

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

								try {
									int sizeClasific = 0;
									if (documento.getId_clasificacion() != null)
										sizeClasific = 1;
									Long[] ClasificacionArray = new Long[sizeClasific];

									if (documento.getId_clasificacion() != null)
										ClasificacionArray[0] = Long.valueOf(documento.getId_clasificacion());

									// SI LA PERSONA VIENE DEL CONGRESO
									if (documento.getId_congresista() != null) {
										razon_social = "CONGRESO DE LA REPUBLICA";
										ruc = "20161749126";
									}

									if (anexoDto != null) {
										System.out.println("Anexos:" + anexoDto.length);
										for (AnexoDto file_link : anexoDto) {
										}
									}
									if (documento.getOficinas() != null) {
										System.out.println("Oficinas:" + documento.getOficinas().length);
										for (TdFlujoSDto file_link : documento.getOficinas()) {
										}
									}

									if (ClasificacionArray != null) {
										for (Long file_link : ClasificacionArray) {
											System.out.println(file_link);
										}
									}

									expediente = ventanillastdProxy.crearExpediente(USU, doc.getId_documento() + "",
											Long.valueOf(documento.getId_tipo_documento()),
											documento.getNro_documento(), documento.getNro_folios(),
											documento.getAsunto(), apellido_paterno, apellido_materno, nombre, dni,
											mipersona.getTelefono(), razon_social, ruc, mipersona.getDireccion(),
											mipersona.getDesc_departamento(), mipersona.getDesc_provincia(),
											mipersona.getDesc_distrito(), mipersona.getCorreo(), anexoDto, IP,
											documento.getOficinas(),
											documento.getId_congresista() == null ? null
													: Long.valueOf(documento.getId_congresista()),
											documento.getId_comision() == null ? null
													: Long.valueOf(documento.getId_comision()),
											new long[0], Integer.valueOf(documento.getId_prioridad()), LinksConcat);

								} catch (OutOfMemoryError ex) {
									auditoria.Rechazar("Error en el servicio STD");
									auditoria.ejecucion_procedimiento = false;
									System.out.println("Error Servicio STD : " + auditoria.error_log);
								}
							} catch (OutOfMemoryError ex) {
								auditoria.Rechazar("Error en memoria por documento grande");
								auditoria.ejecucion_procedimiento = false;
							}
						}

					} catch (Exception ex) {
						auditoria.Error(ex);
						System.out.println("Error AQUI : " + auditoria.error_log);
					}

					if (auditoria.ejecucion_procedimiento) {

						try {
							// actualiza id prioridad
							auditoria = docService.Documento_Recepcionar(doc.getId_documento(),
									expediente.getNumeroAnio(), expediente.getNumeroSid(), documento.getNro_folios(),
									documento.getAsunto(), UserIdentityHelper.get_CodigoUsuario(), IP,
									documento.getId_oficina(), documento.getId_tipo_documento(),
									documento.getHoja_ruta(), documento.getNro_documento(),
									documento.getDesc_congresista(), Integer.valueOf(documento.getId_prioridad()));

						} catch (Exception ex) {
							auditoria.ErrorSTD(ex);
							System.out.println("Error Servicio STD : " + auditoria.error_log);
						}

						// CORREO
						Date date = new Date();
						Auditoria auditoriax = docService.Documento_ListarUno(doc.getId_documento());
						Documento doc_uno = (Documento) auditoriax.objeto;

						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

						Auditoria auditoriaEncuesta = enlaceEncuestaService.obtenerEncuestaActiva();
						EnlaceEncuesta encuesta = (EnlaceEncuesta) auditoriaEncuesta.objeto;

						Map<String, Object> params = new HashMap<>();
						params.put("nombre", nombre_persona);
						params.put("idsolicitud", doc.getId_documento() + "-" + formattery.format(date));
						params.put("fec_registro", doc.getFec_creacion());
						params.put("mensaje", doc_uno.getSrt_fecha_recibido());
						params.put("mensaje2", expediente.getNumeroSid() + "-" + expediente.getNumeroAnio());
						params.put("oficina", doc.getDesc_oficina());
						params.put("hoja_ruta", expediente.getNumeroSid() + "-" + expediente.getNumeroAnio());
						params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
						String Plantilla = "email/documento_validar";
						params.put("imageResourceName", "logo.png");

						emailutil = new EmailUtil();
						String mensaje_html = emailutil.getTemplate(Plantilla, params);
						Casilla modelo = new Casilla();

						modelo.setId_documento(Integer.valueOf(doc.getId_documento()));
						modelo.setId_usuario(Integer.valueOf(doc.getId_usuario()));
						modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
						modelo.setUsu_creacion(UserIdentityHelper.get_CodigoUsuario());
						modelo.setObservacion("Solicitud validada correctamente");
						modelo.setMensaje_html(mensaje_html);
						modelo.setFlg_notificacion(1);
						auditoria = casillaService.Casilla_Insertar(modelo);

						Map<String, Resource> resources = new HashMap<>();
						Resource resource = new Resource();
						Path path = Paths.get(fileServer, "logo.jpg");
						File file = path.toFile();

						resource.setImageBytes(FileUtils.readFileToByteArray(file));
						resource.setContentType("image/png");
						resources.put("logo.png", resource);

						emailutil = new EmailUtil();

						if (micorrreo.equals("L")) {
							emailComponent.sendHTML(mipersona.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									mipersona.getCorreo_copia(), Plantilla, params, resources);
						} else {
							emailutil.sendHTML(mipersona.getCorreo(), "Mensaje de Mesa de Partes - MEF",
									mipersona.getCorreo_copia(), Plantilla, params, resources);
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

	@PostMapping("/anexar-solicitud")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Anexar(@Valid @RequestBody Documento documento) {
		Auditoria auditoria = new Auditoria();
		auditoria.Limpiar();
		try {
			String USU = new UserIdentityHelper().get_CodigoUsuario();
			String IP = UserIdentityHelper.getClientIpAddress();
			HrDto expediente = new HrDto();
			auditoria = docService.Documento_ListarUno(documento.getId_documento());
			String nombre_persona = "";
			if (auditoria.ejecucion_procedimiento) {
				if (!auditoria.rechazar) {
					Documento doc = (Documento) auditoria.objeto;
					// auditoria = docService.Documento_ListarUno(documentoId);
					try {
						auditoria = personaService.UsuarioPersona_Listar(Long.valueOf(doc.getId_usuario()));
						if (auditoria.ejecucion_procedimiento) {
							UsuarioPersona mipersona = (UsuarioPersona) auditoria.objeto;

							// VentanillastdProxy proxy = new VentanillastdProxy();
							Path path = Paths.get(fileServer, doc.getId_documento() + "",
									doc.getCodigo_archivo() + ".pdf");
							File file = path.toFile();
							byte[] fileByte = FileUtils.readFileToByteArray(file);
							// System.out.println(documento.getOficinas().length);
							// pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] oficinas;

							// pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] oficinas = new ArrayList<>();
							// long[] oficinas = {};
							List<DocumentoAnexo> milista = docService
									.getAnexosDocumentoById(documento.getId_documento());

							int tamanio_lista = 0;
							if (milista.size() > 0) {
								for (DocumentoAnexo anexos : milista) {
									if (anexos.getFlg_link().equals("1") || anexos.getFlg_link().equals("")) {
										tamanio_lista++;
									}
								}
							}

							AnexoDto[] anexoDto = new AnexoDto[tamanio_lista + 1];

							// System.out.println("hoja rutaprobandoo "+ documento.getHoja_ruta());

							int count = 0;
							anexoDto[count] = new AnexoDto(fileByte, fileByte.length,
									"ANX-" + documento.getHoja_ruta() + ".pdf");
							// documento.getHoja_ruta());

							if (tamanio_lista > 0)
								for (DocumentoAnexo anexos : milista) {
									if (anexos.getFlg_link().equals("1") || anexos.getFlg_link().equals("")) {

										System.out.println("anexar flg_Link : " + anexos.getFlg_link());

										count++;
										Path pathanexo = Paths.get(fileServer, doc.getId_documento() + "",
												anexos.getCodigo_archivo() + "." + anexos.getExtension_archivo());
										File fileanexo = pathanexo.toFile();
										byte[] fileByteanexo = FileUtils.readFileToByteArray(fileanexo);

										AnexoDto a = new AnexoDto();
										a.setArchivo(fileByteanexo);
										a.setLength(fileByteanexo.length);
										a.setName(anexos.getNombre_archivo());
										// a.setName("ANX-" + documento.getHoja_ruta() + anexos.getExtension_archivo());
										anexoDto[count] = a;
										// anexos.
									}
								}
							// oficinas[0].set
							// oficinas[0].setIdunidadDestino(idunidadDestino);
							/*
							 * if (documento.getCodigo_oficinas() != null) oficinas =
							 * documento.getCodigo_oficinas();
							 */
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
								nombre_persona = razon_social;
							} else {
								nombre_persona = nombre + " " + apellido_paterno + " " + apellido_materno;
								ruc = "";
								razon_social = "";
							}
							System.out.println("datos anexar : " + nombre);

							System.out.println("nombre : " + nombre);
							System.out.println("ape pat : " + apellido_paterno);
							System.out.println("ape mat : " + apellido_materno);
							System.out.println("dni : " + dni);
							System.out.println("ruc : " + ruc);
							System.out.println("razon_social : " + razon_social);
							System.out.println("telefono : " + mipersona.getTelefono());
							System.out.println("direccion : " + mipersona.getDireccion());
							System.out.println("departamento : " + mipersona.getDesc_departamento());
							System.out.println("provincia : " + mipersona.getDesc_provincia());
							System.out.println("distrito : " + mipersona.getDesc_distrito());
							System.out.println("correo : " + mipersona.getCorreo());

							for (TdFlujoSDto anexoDto2 : documento.getOficinas()) {
								System.out.println("Unidad Destino : " + anexoDto2.getIdunidadDestino());
								System.out.println("Observacion  : " + anexoDto2.getObservacion());
								System.out.println("es original  : " + anexoDto2.isOriginal());

							}

							try {
								System.out.println("servicio anexar expediente 1  ");

								System.out.println("USU : " + USU);
								System.out.println("Numero doc : " + documento.getNumero_sid());
								System.out.println("Anio : " + documento.getAnio());
								System.out.println("anexoDto : " + anexoDto.length);
								System.out.println("IP : " + IP);
								System.out.println("getOficinas : " + documento.getOficinas().length);

								System.out.println("servicio anexar expediente 2  ");

								expediente = ventanillastdProxy.anexarAExpediente(USU, documento.getNumero_sid(),
										documento.getAnio(), "", anexoDto, IP, documento.getOficinas());

								System.out.println("servicio anexar expediente 3  ");

								// CORREO
								Date date = new Date();
								SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
								SimpleDateFormat formattery = new SimpleDateFormat("yyyy");
								/*
								 * String mensaje = "Su solicitud ha sido verificada y aceptada con fecha <b>" +
								 * formatter.format(date) +
								 * "</b>. Asimismo, se le comunica que se le ha asignado la Hoja de Ruta " +
								 * expediente.getNumeroSid() + "-" + expediente.getNumeroAnio() + ". "; mensaje
								 * = mensaje.toUpperCase();
								 */

								Map<String, Object> params = new HashMap<>();
								params.put("nombre", nombre_persona);
								params.put("idsolicitud", doc.getId_documento() + "-" + formattery.format(date));
								params.put("fec_registro", doc.getFec_creacion());
								params.put("mensaje", formatter.format(date));
								params.put("mensaje2", expediente.getNumeroSid() + "-" + expediente.getNumeroAnio());
								params.put("oficina", doc.getDesc_oficina());
								params.put("hoja_ruta", expediente.getNumeroSid() + "-" + expediente.getNumeroAnio());

								System.out.println("servicio anexar expediente 4  ");

								params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
								String Plantilla = "email/documento_validar";
								params.put("imageResourceName", "logo.png");
								emailutil = new EmailUtil();
								String mensaje_html = emailutil.getTemplate(Plantilla, params);

								System.out.println("servicio anexar expediente 5 ");

								Casilla modelo = new Casilla();
								modelo.setId_documento(Integer.valueOf(doc.getId_documento()));
								modelo.setId_usuario(Integer.valueOf(doc.getId_usuario()));
								modelo.setIp_creacion(UserIdentityHelper.getClientIpAddress());
								modelo.setUsu_creacion(UserIdentityHelper.get_CodigoUsuario());
								modelo.setObservacion("Solicitud validada correctamente");
								modelo.setMensaje_html(mensaje_html);
								auditoria.Limpiar();// = casillaService.Casilla_Insertar(modelo);

								System.out.println("servicio anexar expediente 6  ");

								Map<String, Resource> resources = new HashMap<>();
								Resource resource = new Resource();
								path = Paths.get(fileServer, "logo.jpg");
								file = path.toFile();
								resource.setImageBytes(FileUtils.readFileToByteArray(file));
								resource.setContentType("image/png");
								resources.put("logo.png", resource);
								System.out.println("servicio anexar expediente 7  ");

								emailutil = new EmailUtil();
								if (micorrreo.equals("L")) {
									emailComponent.sendHTML(mipersona.getCorreo(), "Mensaje de Mesa de Partes - MEF",
											mipersona.getCorreo_copia(), Plantilla, params, resources);
								} else {
									emailutil.sendHTML(mipersona.getCorreo(), "Mensaje de Mesa de Partes - MEF",
											mipersona.getCorreo_copia(), Plantilla, params, resources);
								}
								// ******
								System.out.println("servicio anexar expediente 8  ");
							} catch (Exception ex) {
								auditoria.Error(ex);
								System.out.println("Servicio Mef : " + auditoria.error_log);
							}
						}

					} catch (Exception ex) {
						auditoria.Error(ex);
						System.out.println(auditoria.error_log);
					}
					if (auditoria.ejecucion_procedimiento) {
						System.out.println("recepcionar anexar 1  ");

						auditoria = docService.Documento_Recepcionar(doc.getId_documento(), documento.getAnio(),
								documento.getNumero_sid(), documento.getNro_folios(), documento.getAsunto(),
								UserIdentityHelper.get_CodigoUsuario(), IP, documento.getId_oficina(),
								documento.getId_tipo_documento(), documento.getHoja_ruta(),
								documento.getNro_documento(), documento.getDesc_congresista(),
								Integer.valueOf(documento.getId_prioridad()));

						System.out.println("recepcionar anexar 2  ");
						if (!auditoria.ejecucion_procedimiento) {
							System.out.println(auditoria.error_log);
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

	@PostMapping("/observar-solicitud")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Observar(@Valid @RequestBody DocumentoObservacion obs) {
		Auditoria auditoria = new Auditoria();
		try {
			System.out.println("Entro para observar");
			String USU = new UserIdentityHelper().get_CodigoUsuario();
			obs.setUsu_creacion(USU);

			System.out.println("Se identifico al usuario");
			String fecha_anulacion = CalcularFechaAnulacion();
			obs.setFecha_anulacion(fecha_anulacion);
			auditoria = docService.Documento_Observar(obs);

			System.out.println("Se observo el documento ");
			System.out.println("Datos: " + obs.getId_documento() + ", obs:" + obs.getObservacion() + ", fec:"
					+ obs.getFecha_anulacion());
			if (auditoria.ejecucion_procedimiento) {
				auditoria = docService.Documento_ListarUno(Integer.valueOf(obs.getId_documento()));
				Documento documento = (Documento) auditoria.objeto;
				Casilla modelo = new Casilla();
				auditoria = personaService.Persona_Correo(Long.valueOf(documento.getId_persona()));
				PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;

				Date date = new Date();
				// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				SimpleDateFormat formattery = new SimpleDateFormat("yyyy");

				Map<String, Object> params = new HashMap<>();
				params.put("nombre", entidad.getNombrepersona());
				params.put("idsolicitud", obs.getId_documento() + "-" + formattery.format(date));
				params.put("asunto", documento.getAsunto());
				params.put("fec_registro", documento.getFec_creacion());
				params.put("observacion", obs.getObservacion().toUpperCase());
				// params.put("nro_solicitud", documento.getId_documento());
				// params.put("nro_documento", documento.getNro_documento());
				// params.put("asunto", documento.getAsunto());
				// params.put("tipo_de_doc", documento.getDesc_tipo_documento());
				// String hoja_ruta = "";
				/*
				 * System.out.println("Entro 2"); System.out.println("Nu" +
				 * documento.getNumero_sid()); System.out.println("An" + documento.getAnio());
				 * System.out.println("Ho" + documento.getHoja_ruta());
				 * System.out.println("Entro 4");
				 */
				/*
				 * if (documento.getNumero_sid() != "") { hoja_ruta = documento.getNumero_sid()
				 * + "-" + (documento.getAnio() + ""); } else { hoja_ruta =
				 * documento.getHoja_ruta(); }
				 */
				// System.out.println("Entro 5");
				// params.put("hojaruta", hoja_ruta);
				params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
				String Plantilla = "email/documento_observar";
				params.put("imageResourceName", "logo.png");
				emailutil = new EmailUtil();
				String mensaje = emailutil.getTemplate(Plantilla, params);

				// System.out.println(mensaje);
				// System.out.println("Entro 6");
				modelo.setId_documento(Integer.valueOf(obs.getId_documento()));
				modelo.setId_usuario(Integer.valueOf(documento.getId_usuario()));
				modelo.setIp_creacion(new UserIdentityHelper().getClientIpAddress());
				modelo.setUsu_creacion(new UserIdentityHelper().get_CodigoUsuario());
				modelo.setObservacion(obs.getObservacion());
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

	@PostMapping("/actualizarEstado-doc")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Update_Estado(@Valid @RequestBody DocumentoObservacion obs) {
		Auditoria auditoria = new Auditoria();
		try {

			obs.setUsu_creacion(new UserIdentityHelper().get_CodigoUsuario());

			auditoria = docService.Documento_Actualizar_Estado(obs);
			// System.out.println("FECHA ANU " + fecha);

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	public String CalcularFechaAnulacion() throws ErrorInfo, RemoteException, ParseException {
		// VentanillastdProxy proxy = new VentanillastdProxy();
		SimpleDateFormat fromaterF = new SimpleDateFormat("dd/MM/yyyy");
		Date date_hoy = new Date();

		int Dias = (1 * 24);
		Calendar calendar_hoy = Calendar.getInstance();
		calendar_hoy.setTime(date_hoy);
		calendar_hoy.add(Calendar.HOUR, Dias);

		// Agregamos el 1er dia
		Date fecha_masuno = calendar_hoy.getTime();
		String Fecha_Anu = "";
		TaFeriadosDto[] ListaFeriados;
		try {
			ListaFeriados = ventanillastdProxy.listaDiasFeriados();

		} catch (Exception ex) {
			// auditoria.Error(ex);
			System.out.println("Feriados:" + ex.toString());
			ListaFeriados = null;
		}

		boolean valido_rutina = false;
		int agregar_dia = 1;
		while (valido_rutina == false) {
			// agregar_dia++;
			calendar_hoy.add(Calendar.HOUR, agregar_dia * 24);
			fecha_masuno = calendar_hoy.getTime();
			valido_rutina = true;

			if (ListaFeriados != null) {
				valido_rutina = Loop_Feriados(fecha_masuno, ListaFeriados);
			}
			/*
			 * if (ListaFeriados != null) { valido_rutina = Loop_Feriados(fecha_masuno,
			 * ListaFeriados); }
			 * 
			 * 
			 * System.out.println("Entro xd: " + valido_rutina);
			 * 
			 * if (!valido_rutina) { calendar_hoy.add(Calendar.HOUR, agregar_dia * 24);
			 * fecha_masuno = calendar_hoy.getTime(); //valido_rutina = true; } if
			 * (ListaFeriados != null && !valido_rutina) valido_rutina = false; else
			 * valido_rutina = true;
			 */
		}
		System.out.println("Fecha PreFinal: " + fecha_masuno);
		// Agregamos el 2do dia
		calendar_hoy.add(Calendar.HOUR, Dias);
		fecha_masuno = calendar_hoy.getTime();
		valido_rutina = false;
		// agregar_dia = 0;
		while (valido_rutina == false) {
			// agregar_dia++;

			fecha_masuno = calendar_hoy.getTime();
			valido_rutina = true;

			if (ListaFeriados != null)
				valido_rutina = Loop_Feriados(fecha_masuno, ListaFeriados);

			if (!valido_rutina)
				calendar_hoy.add(Calendar.HOUR, agregar_dia * 24);
			/*
			 * 
			 * //else //valido_rutina = false;
			 * 
			 * if (!valido_rutina) { calendar_hoy.add(Calendar.HOUR, agregar_dia * 24);
			 * fecha_masuno = calendar_hoy.getTime(); // valido_rutina = true; //
			 * System.out.println("dia2: " + fecha_masuno); } if (ListaFeriados != null &&
			 * !valido_rutina) valido_rutina = false; else valido_rutina = true;
			 */
		}
		System.out.println("Fecha Final: " + fecha_masuno);
		// Date fecha = new Date();
		// Date fechaactual = new Date(System.currentTimeMillis());

		// Date Fecha_Anulacion = fromaterF.parse(Fecha_Anulacion(0));
		// System.out.println("fec Anulacion " + Fecha_Anulacion);
		// System.out.println("fec Actual " + fechaactual);
		// TaFeriadosDto[] ListaFeriados = proxy.listaDiasFeriados();
		/*
		 * int PrimerDia = 0; int SegundoDia = 0; boolean _PrimerDia = false; boolean
		 * _SegundoDia = false;
		 */

		// int dias = 0;
		// boolean valido = true;
		Fecha_Anu = fromaterF.format(fecha_masuno);

		return Fecha_Anu;
	}

	private boolean Loop_Feriados(Date fecha, TaFeriadosDto[] ListaFeriados)
			throws ErrorInfo, RemoteException, ParseException {
		boolean valido = true;
		SimpleDateFormat fromaterF = new SimpleDateFormat("dd/MM/yyyy");
		// VentanillastdProxy proxy = new VentanillastdProxy();
		// TaFeriadosDto[] ListaFeriados = proxy.listaDiasFeriados();

		for (TaFeriadosDto diasf : ListaFeriados) {

			String Fecha_Feriado = fromaterF.format(diasf.getFeFecha().getTime());
			// date_hoy.setDate(1);// .add(Calendar.DAY_OF_MONTH, 7);
			// System.out.println("dia1: " + fecha);
			// System.out.println("dia nro: " + fecha.getDay());
			if (Fecha_Feriado.equals(fromaterF.format(fecha))) {
				System.out.println("Feriado: " + Fecha_Feriado);
				valido = false;
				break;
			}
			if (fecha.getDay() == 6 || fecha.getDay() == 0) {
				valido = false;
				System.out.println("dia: " + fecha);
				System.out.println("dia nro: " + fecha.getDay());
				break;
			}

		}
		return valido;
	}

	public String Fecha_Anulacion(int dias) {
		int diasValido = 0;
		if (dias == 0)
			diasValido = 2;
		else
			diasValido = dias;

		Date fecha = new Date();
		// Date fechaactual = new Date(System.currentTimeMillis());

		int Dias = (diasValido * 24);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.HOUR, Dias);
		Date FechaNueva = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String Fecha_Anulacion = sdf.format(FechaNueva);

		return Fecha_Anulacion;
	}

	@GetMapping("/obtener-observacion-solicitud/{id}")
	public ResponseEntity<Auditoria> Documento_Observacion_Listar(@PathVariable(value = "id") Integer documentoId) {
		Auditoria auditoria = new Auditoria();
		auditoria = docService.Documento_Observacion_Listar(documentoId);

		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/libertad")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> libertad() {
		Auditoria auditoria = new Auditoria();
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/VE_notificarsolicitud")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public ResponseEntity<Auditoria> Documento_Notificar(@Valid @ModelAttribute DocumentoFinalizar doc) {
		Auditoria auditoria = new Auditoria();
		try {
			System.out.println("Se llamo al metodo Notificar");

			String ASUNTO = doc.getAsunto();
			String MENSAJE = doc.getMensaje();
			String ID_USUARIO = doc.getId_usuario();
			String ID_PERSONA = doc.getId_persona();
			String ANIO = doc.getAnio();
			String NUMERO_SID = doc.getNumero_sid();
			String USU_MODIFICACION = doc.getUsu_modificacion();
			System.out.println("nuevo paso yor 1");
			String TIPO_DOC = doc.getTipo_documento();
			String HOJA_ENVIO = doc.getHoja_envio();
			String NRO_DOC = doc.getNro_documento();
			String OFICINA = doc.getOficina();
			System.out.println("nuevo paso yor 2");
			System.out.println("ASUNTO :" + ASUNTO);
			System.out.println("MENSAJE :" + MENSAJE);
			System.out.println("ID_USUARIO :" + ID_USUARIO);
			System.out.println("ID_PERSONA :" + ID_PERSONA);
			System.out.println("ANIO :" + ANIO);
			System.out.println("NUMERO_SID :" + NUMERO_SID);
			System.out.println("USU_MODIFICACION :" + USU_MODIFICACION);
			System.out.println("TIPO_DOC :" + TIPO_DOC);
			System.out.println("HOJA_ENVIO :" + HOJA_ENVIO);
			System.out.println("NRO_DOC :" + NRO_DOC);
			System.out.println("OFICINA :" + OFICINA);

			Boolean valido_obligatorio = true;
			Boolean validar_proceso = false;
			Boolean proceso1 = false;
			Boolean proceso2 = false;
			String mensaje_salida = "";
			System.out.println("nuevo paso yor 3");
			if (ASUNTO == null || ASUNTO == "") {
				valido_obligatorio = false;
				mensaje_salida = "El asunto es obligatorio";
				System.out.println("nuevo paso yor 4");
			}
			if (USU_MODIFICACION == null || USU_MODIFICACION == "") {
				valido_obligatorio = false;
				mensaje_salida = "El usuario que ejecuta el proceso es obligatorio";
				System.out.println("nuevo paso yor 5");
			}
			if (MENSAJE == null || MENSAJE == "") {
				valido_obligatorio = false;
				mensaje_salida = "El mensaje es obligatorio";
				System.out.println("nuevo paso yor 6");
			}
			if (HOJA_ENVIO == null || HOJA_ENVIO == "") {
				valido_obligatorio = false;
				mensaje_salida = "La hoja de envio es obligatorio";
				System.out.println("nuevo paso yor 7");
			}
			if (TIPO_DOC == null || TIPO_DOC == "") {
				valido_obligatorio = false;
				mensaje_salida = "El tipo de documento es obligatorio";
				System.out.println("nuevo paso yor 8");
			}
			if (NRO_DOC == null || NRO_DOC == "") {
				valido_obligatorio = false;
				mensaje_salida = "El nro documento es obligatorio";
				System.out.println("nuevo paso yor 9");
			}
			if (OFICINA == null || OFICINA == "") {
				valido_obligatorio = false;
				mensaje_salida = "La oficina es obligatorio";
				System.out.println("nuevo paso yor 10");
			}
			if (valido_obligatorio) {
				System.out.println("paso yor 11");
				if ((ID_USUARIO != null && ID_USUARIO != "") && (ID_PERSONA != null && ID_PERSONA != "")) {
					validar_proceso = true;
					proceso1 = true;
					System.out.println("nuevo paso yor 12");
				}
				if ((ANIO != null && ANIO != "") && (NUMERO_SID != null && NUMERO_SID != "")) {
					System.out.println("nuevo paso yor 13");
					Boolean valido_campos = true;
					if (TIPO_DOC == null || TIPO_DOC == "") {
						valido_obligatorio = false;
						mensaje_salida = "El tipo de documento es obligatorio";
						System.out.println("nuevo paso yor 14");
					}
					if (NRO_DOC == null || NRO_DOC == "") {
						valido_obligatorio = false;
						mensaje_salida = "El nro documento es obligatorio";
						System.out.println("nuevo paso yor 15");
					}
					if (OFICINA == null || OFICINA == "") {
						valido_obligatorio = false;
						mensaje_salida = "La oficina es obligatorio";
						System.out.println("nuevo paso yor 16");
					}
					if (valido_obligatorio) {
						validar_proceso = true;
						proceso2 = true;
						System.out.println("nuevo paso yor 17");
					}
				}
				if (!validar_proceso) {
					mensaje_salida = "Debe ingresar al menos la hoja de ruta o el id de usuario y persona";
					System.out.println("nuevo paso yor 18");
				}
			}
			System.out.println("entro xDD 1");
			if (valido_obligatorio && validar_proceso) {
				System.out.println("nuevo paso yor 19");
				System.out.println("entro xDD 2");
				String IP = new UserIdentityHelper().getClientIpAddress();
				if (proceso2) {
					System.out.println("nuevo paso yor 20");
					DocumentoFinalizar finalizar = new DocumentoFinalizar();
					System.out.println("nuevo paso yor 21");
					finalizar.setNumero_sid(NUMERO_SID);
					System.out.println("nuevo paso yor 22");
					finalizar.setAnio(ANIO);
					System.out.println("nuevo paso yor 23");
					auditoria = docService.Documento_HojaRuta(finalizar);
					System.out.println("nuevo paso yor 24");
					// System.out.println("entro xDD 3");
					if (auditoria.ejecucion_procedimiento) {
						System.out.println("paso yor 25");
						if (auditoria.rechazar) {
							System.out.println("nuevo paso yor 26");
							proceso2 = false;
							if (!proceso1) {
								System.out.println("paso yor 27");
								mensaje_salida = "La hoja de ruta no se encontro, como 2da opcion debe ingresar el id de usuario y persona para notificar ";
							} else {
								auditoria.Limpiar();
								System.out.println("nuevo paso yor 28");
							}
						}
					}
				} else {
					auditoria.Limpiar();
					System.out.println("nuevo paso yor 29");
				}
				System.out.println("entro xDD 4");
				System.out.println("nuevo paso yor 30");
				if (auditoria.ejecucion_procedimiento) {
					System.out.println("paso yor 31");
					if (!auditoria.rechazar) {
						System.out.println("nuevo paso yor 32");
						System.out.println("entro xDD 5");
						long documentoId = 0;
						System.out.println("nuevo paso yor 33");
						Documento documento = new Documento();
						System.out.println("nuevo paso yor 33");
						long id_persona = 0;
						int id_usuario = 0;
						if (proceso2) {
							System.out.println("nuevo paso yor 34");
							documentoId = Long.valueOf(auditoria.objeto.toString());
							System.out.println("nuevo paso yor 35");
							auditoria = docService.Documento_ListarUno(Integer.valueOf(documentoId + ""));
							System.out.println("nuevo paso yor 36");
							documento = (Documento) auditoria.objeto;
							System.out.println("nuevo paso yor 37");
							// id_persona = Long.valueOf(documento.getId_persona());
							System.out.println("nuevo paso yor 38");
							// id_usuario = Integer.valueOf(documento.getId_usuario());
							System.out.println("nuevo paso yor 39");
						} else {
							System.out.println("nuevo paso yor 40");

						}

						id_persona = Long.valueOf(ID_PERSONA);
						id_usuario = Integer.valueOf(ID_USUARIO);

						System.out.println("entro xDD 6");
						/*
						 * Casilla modelo = new Casilla(); modelo.setId_persona(id_persona);
						 * modelo.setId_usuario(id_usuario); modelo.setId_documento(documentoId);
						 */
						auditoria = personaService.Persona_Correo(id_persona);
						System.out.println("nuevo paso yor 41");
						System.out.println("entro xDD 7");
						if (auditoria.objeto != null) {
							System.out.println("nuevo paso yor 42");
							PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;
							System.out.println("nuevo paso yor 43");
							// String Plantilla = "";
							Map<String, Object> params = new HashMap<>();
							System.out.println("nuevo entro xDD 8");
							params.put("hoja_envio", HOJA_ENVIO);
							System.out.println("nuevo paso yor 44");
							params.put("nombre", entidad.getNombrepersona());
							System.out.println("nuevo paso yor 45");
							params.put("asunto", ASUNTO);
							System.out.println("nuevo paso yor 46");
							// params.put("asunto",
							// "Estimado usuario, Usted ha recibido una notificacion en su casilla
							// electronica.");

							System.out.println("entro xDD 9");
							String ID_DOC = "0";
							if (proceso2) {
								System.out.println("nuevo paso yor 47");
								ID_DOC = documento.getId_documento() + "";
								System.out.println("nuevo paso yor 48");
								params.put("hoja_ruta", documento.getNumero_sid() + "-" + documento.getAnio());
								System.out.println("nuevo paso yor 49");
								/*
								 * params.put("hoja_ruta", documento.getNumero_sid() + "-" +
								 * documento.getAnio()); params.put("tipo_documento",
								 * documento.getDesc_tipo_documento()); params.put("nro_documento",
								 * documento.getNro_documento());
								 */
							} else {
								params.put("hoja_ruta", NUMERO_SID + "-" + ANIO);
								System.out.println("nuevo paso yor 50");
								/*
								 * params.put("hoja_ruta", NUMERO_SID + "-" + ANIO);
								 * params.put("tipo_documento", TIPO_DOC); params.put("nro_documento", NRO_DOC);
								 */
							}
							System.out.println("nuevo paso yor 51");
							params.put("tipo_documento", TIPO_DOC);
							System.out.println("nuevo paso yor 52");
							params.put("nro_documento", NRO_DOC);
							System.out.println("nuevo paso yor 53");
							System.out.println("numerosid:" + NUMERO_SID);
							// System.out.println("entro xDD 10");
							params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
							System.out.println("paso yor 54");
							// ****************************** CORREO
							String Plantilla = "email/documento_notificacion";
							System.out.println("nuevo paso yor 55");
							params.put("imageResourceName", "logo.png");
							System.out.println("paso yor 56");
							emailutil = new EmailUtil();
							System.out.println("paso yor 57");
							String mensaje_html = "";

							System.out.println("entro xDD 11");
							System.out.println("entro xDD 12:" + mensaje_html);
							Casilla modelo = new Casilla();
							System.out.println("nuevo paso yor 58");
							modelo.setHoja_envio(HOJA_ENVIO);
							System.out.println("paso yor 59");
							modelo.setHoja_ruta(NUMERO_SID + "-" + ANIO);
							System.out.println("paso yor 60");
							modelo.setTipo_doc(TIPO_DOC);
							System.out.println("nuevo paso yor 61");
							modelo.setNro_doc(NRO_DOC);
							System.out.println("paso yor 62");
							modelo.setOficina(OFICINA);
							System.out.println("nuevo paso yor 63");
							modelo.setId_persona(id_persona);
							System.out.println("paso yor 64");
							modelo.setId_documento(Integer.valueOf(ID_DOC));
							System.out.println("paso yor 65");
							modelo.setId_usuario(Integer.valueOf(doc.getId_usuario()));
							System.out.println("paso yor 66");
							modelo.setIp_creacion(new UserIdentityHelper().getClientIpAddress());
							System.out.println("paso yor 67");
							modelo.setUsu_creacion(USU_MODIFICACION);
							System.out.println("paso yor 68");
							modelo.setObservacion(MENSAJE);
							System.out.println("paso yor 69");
							modelo.setMensaje_html(mensaje_html);
							System.out.println("nuevo paso yor 70");

							System.out.println("HOJA_ENVIO:" + HOJA_ENVIO);
							System.out.println("NUMERO_SID:" + NUMERO_SID);
							System.out.println("TIPO_DOC:" + TIPO_DOC);
							System.out.println("NRO_DOC:" + NRO_DOC);
							System.out.println("OFICINA:" + OFICINA);
							System.out.println("id_persona:" + id_persona);
							System.out.println("ID_DOC:" + ID_DOC);
							System.out.println("MENSAJE:" + MENSAJE);

							modelo.setFlg_notificacion(2);
							// System.out.println("mensaje_html:"+mensaje_html);
							auditoria = casillaService.Casilla_Insertar(modelo);
							System.out.println("entro xDD 13");
							System.out.println("paso yor 71");
							Map<String, Resource> resources = new HashMap<>();
							Resource resource = new Resource();
							System.out.println("nuevo paso yor 72");
							Path path = Paths.get(fileServer, "logo.jpg");
							System.out.println("paso yor 73");
							File file = path.toFile();
							System.out.println("paso yor 74");
							resource.setImageBytes(FileUtils.readFileToByteArray(file));
							System.out.println("nuevo paso yor 75");
							resource.setContentType("image/png");
							System.out.println("nuevo paso yor 76");
							// resources.put("logo.png", resource);

							// params.put("id_casilla", (Long)auditoria.objeto);
							// params.put("url_img_servelet", url_servelet);

							String Url_Servelet = url_servelet + "?id=" + (Long) auditoria.objeto;
							System.out.println("nuevo paso yor 77");
							params.put("url_img_servelet", Url_Servelet);
							System.out.println("paso yor 78");
							System.out.println("URL_SERVELET:" + Url_Servelet);

							mensaje_html = emailutil.getTemplate(Plantilla, params);
							System.out.println("paso yor 79");
							modelo.setMensaje_html(mensaje_html);
							System.out.println("paso yor 80");
							modelo.setId_casilla((Long) auditoria.objeto);
							System.out.println("nuevo paso yor 81");
							auditoria = casillaService.Casilla_Actualizar(modelo);
							System.out.println("paso yor 82");
							emailutil = new EmailUtil();
							System.out.println("paso yor 83");
							if (micorrreo.equals("L")) {
								System.out.println("paso yor 84");
								emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), Plantilla, params, resources);
								System.out.println("paso yor 85");
							} else {
								System.out.println("paso yor 86");
								emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), Plantilla, params, resources);
								System.out.println("paso yor 87");
							}
							// **************************************
							System.out.println("nuevo paso yor 88");
							path = Paths.get(fileServer);
							System.out.println("paso yor 89");
							if (auditoria.ejecucion_procedimiento) {
								System.out.println("paso yor 90");
								if (!auditoria.rechazar) {
									System.out.println("paso yor 91");
									Long id_casilla = (Long) auditoria.objeto;
									System.out.println("paso yor 92");
									Map<String, String> paramsFile = new HashMap<>();
									if (doc.getFilesAnexos() != null)
										System.out.println("paso yor 93");
									for (MultipartFile filex : doc.getFilesAnexos()) {
										System.out.println("paso yor 94");
										CasillaArchivo miarchivo = new CasillaArchivo();
										System.out.println("paso yor 95");
										String filename = UUID.randomUUID().toString();
										System.out.println("paso yor 96");
										filename = CommonHelpers.Generar_Nombre_Archivo(filex, filename);
										System.out.println("paso yor 97");
										miarchivo.setId_casilla(id_casilla);
										System.out.println("paso yor 98");
										miarchivo.setNombre_archivo(filex.getOriginalFilename());
										System.out.println("paso yor 99");
										miarchivo.setCodigo_archivo(filename);
										System.out.println("paso yor 100");
										miarchivo.setTamanio_archivo(filex.getBytes().length);
										System.out.println("paso yor 101");
										miarchivo.setExtension_archivo(
												FilenameUtils.getExtension(filex.getOriginalFilename()));
										miarchivo.setUsu_creacion(doc.getUsu_modificacion());
										System.out.println("paso yor 102");
										miarchivo.setIp_creacion(IP);
										System.out.println("paso yor 103");
										auditoria = casillaService.CasillaArchivo_Insertar(miarchivo);
										System.out.println("paso yor 104");
										paramsFile.put(filex.getOriginalFilename(), path.toString() + "/" + filename);
										System.out.println("paso yor 105");
										if (auditoria.ejecucion_procedimiento)
											if (!auditoria.rechazar)
												storageService.save(path.toString(), filex, filename);
											else
												break;
										else
											break;
									}
									System.out.println("paso yor 106");
									CasillaUno model = new CasillaUno();
									System.out.println("paso yor 107");
									model.setId_usuario("0");
									System.out.println("paso yor 108");
									model.setId_casilla(id_casilla.toString());
									System.out.println("paso yor 109");
									auditoria.objeto = casillaService.Casilla_Listar_Uno(model);
									System.out.println("paso yor 110");
								}
							}
						} else {
							System.out.println("paso yor 111");
							auditoria.Rechazar("No se encontro al usuario con el id usuario y persona ingresado");
						}
					}
				}
			} else {
				System.out.println("paso yor 112");
				auditoria.ejecucion_procedimiento = false;
				auditoria.mensaje_salida = mensaje_salida;
			}

		} catch (Exception ex) {
			System.out.println("paso yor 113");
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		System.out.println("paso yor 114");
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/VE_atendersolicitud")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Atender(@Valid @ModelAttribute DocumentoFinalizar doc) {
		Auditoria auditoria = new Auditoria();
		try {

			System.out.println("Se llamo al metodo Atender");
			String ASUNTO = doc.getAsunto();
			String MENSAJE = doc.getMensaje();
			String ID_USUARIO = doc.getId_usuario();
			String ID_PERSONA = doc.getId_persona();
			String ANIO = doc.getAnio();
			String NUMERO_SID = doc.getNumero_sid();
			String USU_MODIFICACION = doc.getUsu_modificacion();

			String TIPO_DOC = doc.getTipo_documento();
			String HOJA_ENVIO = doc.getHoja_envio();
			String NRO_DOC = doc.getNro_documento();
			String OFICINA = doc.getOficina();

			System.out.println("ASUNTO :" + ASUNTO);
			System.out.println("MENSAJE :" + MENSAJE);
			System.out.println("ID_USUARIO :" + ID_USUARIO);
			System.out.println("ID_PERSONA :" + ID_PERSONA);
			System.out.println("ANIO :" + ANIO);
			System.out.println("NUMERO_SID :" + NUMERO_SID);
			System.out.println("USU_MODIFICACION :" + USU_MODIFICACION);
			System.out.println("TIPO_DOC :" + TIPO_DOC);
			System.out.println("HOJA_ENVIO :" + HOJA_ENVIO);
			System.out.println("NRO_DOC :" + NRO_DOC);
			System.out.println("OFICINA :" + OFICINA);
			// System.out.println("Entro 1");
			Boolean valido_obligatorio = true;
			Boolean validar_proceso = false;
			Boolean proceso1 = false;
			Boolean proceso2 = false;
			String mensaje_salida = "";

			// System.out.println("Entro 2");

			if (ASUNTO == null || ASUNTO == "") {
				valido_obligatorio = false;
				mensaje_salida = "El asunto es obligatorio";
			}
			if (USU_MODIFICACION == null || USU_MODIFICACION == "") {
				valido_obligatorio = false;
				mensaje_salida = "El usuario que ejecuta el proceso es obligatorio";
			}
			if (HOJA_ENVIO == null || HOJA_ENVIO == "") {
				valido_obligatorio = false;
				mensaje_salida = "La hoja de envio es obligatorio";
			}
			System.out.println("Entro 3");
			if (MENSAJE == null || MENSAJE == "") {
				valido_obligatorio = false;
				mensaje_salida = "El mensaje es obligatorio";
			}

			if (TIPO_DOC == null || TIPO_DOC == "") {
				valido_obligatorio = false;
				mensaje_salida = "El tipo de documento es obligatorio";
			}
			if (NRO_DOC == null || NRO_DOC == "") {
				valido_obligatorio = false;
				mensaje_salida = "El nro documento es obligatorio";
			}
			if (OFICINA == null || OFICINA == "") {
				valido_obligatorio = false;
				mensaje_salida = "La oficina es obligatorio";
			}
			if (valido_obligatorio) {
				System.out.println("Entro 4");
				if ((ID_USUARIO != null && ID_USUARIO != "") && (ID_PERSONA != null && ID_PERSONA != "")) {
					validar_proceso = true;
					proceso1 = true;
				}
				System.out.println("Entro 5");
				if ((ANIO != null && ANIO != "") && (NUMERO_SID != null && NUMERO_SID != "")) {
					if (TIPO_DOC == null || TIPO_DOC == "") {
						valido_obligatorio = false;
						mensaje_salida = "El tipo de documento es obligatorio";
					}
					if (NRO_DOC == null || NRO_DOC == "") {
						valido_obligatorio = false;
						mensaje_salida = "El nro documento es obligatorio";
					}
					if (OFICINA == null || OFICINA == "") {
						valido_obligatorio = false;
						mensaje_salida = "La oficina es obligatorio";
					}
					if (valido_obligatorio) {
						validar_proceso = true;
						proceso2 = true;
					}
				} else {
					validar_proceso = false;
				}
				System.out.println("Entro 6");
				if (!validar_proceso) {
					mensaje_salida = "Debe ingresar la hoja de ruta completa";
				}
			}
			if (valido_obligatorio && validar_proceso) {
				String IP = new UserIdentityHelper().getClientIpAddress();
				if (proceso2) {
					DocumentoFinalizar finalizar = new DocumentoFinalizar();
					finalizar.setUsu_modificacion(USU_MODIFICACION);
					finalizar.setNumero_sid(NUMERO_SID);
					finalizar.setAnio(ANIO);
					auditoria = docService.Documento_Atender(finalizar);
					if (auditoria.ejecucion_procedimiento) {
						if (auditoria.rechazar) {
							proceso2 = false;
							if (!proceso1) {
								mensaje_salida = "La hoja de ruta no se encontro";
							}
						}
					}
				}
				if (auditoria.ejecucion_procedimiento && proceso2) {
					if (!auditoria.rechazar) {
						long documentoId = 0;
						Documento documento = new Documento();
						long id_persona = 0;
						int id_usuario = 0;
						if (proceso2) {
							documentoId = Long.valueOf(auditoria.objeto.toString());
							auditoria = docService.Documento_ListarUno(Integer.valueOf(documentoId + ""));
							documento = (Documento) auditoria.objeto;
							id_persona = Long.valueOf(documento.getId_persona());
							id_usuario = Integer.valueOf(documento.getId_usuario());
						} else {
							id_persona = Long.valueOf(ID_PERSONA);
							id_usuario = Integer.valueOf(ID_USUARIO);
						}

						/*
						 * Casilla modelo = new Casilla(); modelo.setId_persona(id_persona);
						 * modelo.setId_usuario(id_usuario); modelo.setId_documento(documentoId);
						 */
						auditoria = personaService.Persona_Correo(id_persona);
						if (auditoria.objeto != null) {
							PersonaCorreo entidad = (PersonaCorreo) auditoria.objeto;
							// String Plantilla = "";
							Map<String, Object> params = new HashMap<>();

							params.put("hoja_envio", HOJA_ENVIO);
							params.put("nombre", entidad.getNombrepersona());
							params.put("asunto", ASUNTO);

							String ID_DOC = "0";
							if (proceso2) {
								ID_DOC = documento.getId_documento() + "";
								params.put("hoja_ruta", documento.getNumero_sid() + "-" + documento.getAnio());
								// params.put("tipo_documento", documento.getDesc_tipo_documento());
								// params.put("nro_documento", documento.getNro_documento());
							} else {
								params.put("hoja_ruta", NUMERO_SID + "-" + ANIO);
								// params.put("tipo_documento", TIPO_DOC);
								// params.put("nro_documento", NRO_DOC);
							}
							params.put("tipo_documento", TIPO_DOC);
							params.put("nro_documento", NRO_DOC);
							params.put("anio", Calendar.getInstance().get(Calendar.YEAR));
							// ****************************** CORREO
							String Plantilla = "email/documento_notificacion";
							params.put("imageResourceName", "logo.png");
							emailutil = new EmailUtil();
							String mensaje_html = "";

							Casilla modelo = new Casilla();
							modelo.setHoja_envio(HOJA_ENVIO);
							modelo.setHoja_ruta(NUMERO_SID + "-" + ANIO);
							modelo.setTipo_doc(TIPO_DOC);
							modelo.setNro_doc(NRO_DOC);
							modelo.setOficina(OFICINA);
							modelo.setId_persona(id_persona);
							modelo.setId_documento(Integer.valueOf(ID_DOC));
							modelo.setId_usuario(Integer.valueOf(doc.getId_usuario()));
							modelo.setIp_creacion(new UserIdentityHelper().getClientIpAddress());
							modelo.setUsu_creacion(USU_MODIFICACION);
							modelo.setObservacion("Solicitud validada correctamente");
							modelo.setMensaje_html(mensaje_html);
							auditoria = casillaService.Casilla_Insertar(modelo);

							Map<String, Resource> resources = new HashMap<>();
							Resource resource = new Resource();
							Path path = Paths.get(fileServer, "logo.jpg");
							File file = path.toFile();
							resource.setImageBytes(FileUtils.readFileToByteArray(file));
							resource.setContentType("image/png");
							// resources.put("logo.png", resource);

							String Url_Servelet = url_servelet + "?id=" + (Long) auditoria.objeto;
							params.put("url_img_servelet", Url_Servelet);

							System.out.println("URL_SERVELET:" + Url_Servelet);

							mensaje_html = emailutil.getTemplate(Plantilla, params);
							modelo.setMensaje_html(mensaje_html);
							modelo.setId_casilla((Long) auditoria.objeto);

							auditoria = casillaService.Casilla_Actualizar(modelo);

							emailutil = new EmailUtil();
							if (micorrreo.equals("L")) {
								emailComponent.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), Plantilla, params, resources);
							} else {
								emailutil.sendHTML(entidad.getCorreo(), "Mensaje de Mesa de Partes - MEF",
										entidad.getCorreo_copia(), Plantilla, params, resources);
							}
							// **************************************
							path = Paths.get(fileServer);
							if (auditoria.ejecucion_procedimiento) {
								if (!auditoria.rechazar) {
									Long id_casilla = (Long) auditoria.objeto;
									Map<String, String> paramsFile = new HashMap<>();
									if (doc.getFilesAnexos() != null)
										for (MultipartFile filex : doc.getFilesAnexos()) {
											CasillaArchivo miarchivo = new CasillaArchivo();
											String filename = UUID.randomUUID().toString();
											filename = CommonHelpers.Generar_Nombre_Archivo(filex, filename);
											miarchivo.setId_casilla(id_casilla);
											miarchivo.setNombre_archivo(filex.getOriginalFilename());
											miarchivo.setCodigo_archivo(filename);
											miarchivo.setTamanio_archivo(filex.getBytes().length);
											miarchivo.setExtension_archivo(
													FilenameUtils.getExtension(filex.getOriginalFilename()));
											miarchivo.setUsu_creacion(doc.getUsu_modificacion());
											miarchivo.setIp_creacion(IP);

											auditoria = casillaService.CasillaArchivo_Insertar(miarchivo);

											paramsFile.put(filex.getOriginalFilename(),
													path.toString() + "/" + filename);
											if (auditoria.ejecucion_procedimiento)
												if (!auditoria.rechazar)
													storageService.save(path.toString(), filex, filename);
												else
													break;
											else
												break;
										}
									CasillaUno model = new CasillaUno();
									model.setId_usuario("0");
									model.setId_casilla(id_casilla.toString());
									auditoria.objeto = casillaService.Casilla_Listar_Uno(model);
								}
							}
						} else {
							auditoria.Rechazar("No se encontro al usuario con el id usuario y persona ingresado");
						}
					}
				}
			} else {
				auditoria.ejecucion_procedimiento = false;
				auditoria.mensaje_salida = mensaje_salida;
			}

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/document-settings")
	public ResponseEntity<Auditoria> Settings() {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria.Limpiar();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("months_per_report", monthsPerReport);
			auditoria.objeto = jsonObject.toMap();
			System.out.println(monthsPerReport);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/asignar-documento")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Asignar(@Valid @RequestBody Documento documento) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = docService.Documento_Asignar(documento.getId_documento(),
					new UserIdentityHelper().get_CodigoUsuario());

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/validar-recepcion-observacion/{id}")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Validar_Recep_Observar(@PathVariable(value = "id") Integer documentoId) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = docService.Documento_Validar_Recep_Observar(documentoId,
					new UserIdentityHelper().get_CodigoUsuario());

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/registrar-anexo")
	@Consumes("multipart/form-data;charset=UTF-8")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Insertar(@Valid @ModelAttribute DocumentoAnexo anexo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria.Limpiar();
			String filename = UUID.randomUUID().toString();
			String mimetype = FilenameUtils.getExtension(anexo.getFile().getContentType()).toLowerCase();

			Path path = Paths.get(fileServer, anexo.getId_documento() + "");
			Files.createDirectories(path);

			// anexo.setId_documento(documentoId);
			anexo.setCodigo_archivo(filename);
			filename = CommonHelpers.Generar_Nombre_Archivo(anexo.getFile(), filename);

			anexo.setExtension_archivo(FilenameUtils.getExtension(anexo.getFile().getOriginalFilename()));
			anexo.setNombre_archivo(anexo.getFile().getOriginalFilename());

			anexo.setUsu_creacion(UserIdentityHelper.getName());
			anexo.setTamanio_archivo(anexo.getFile().getBytes().length);

			anexo.setMimetype_archivo(mimetype);
			anexo.setFlg_link("1");
			RespuestaMessage result = docService.crearAnexo(anexo);

			if (result.getCode().equals(100))
				storageService.save(path.toString(), anexo.getFile(), filename);
			else
				auditoria.Rechazar(result.getMessage());

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/obtener-anexos/{id}")
	public ResponseEntity<Auditoria> Documento_Anexos(@PathVariable(value = "id") Integer documentoId) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria.Limpiar();
			auditoria.objeto = docService.getAnexosDocumentoById(documentoId);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/anular-anexo")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Anexo_Anular(@Valid @RequestBody DocumentoAnexo anexo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria.Limpiar();
			RespuestaMessage result = docService.anularAnexo(anexo.getId_documento(), anexo.getCodigo_archivo(),
					UserIdentityHelper.getName());

			if (!result.getCode().equals(100))
				auditoria.Rechazar(result.getMessage());

		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/validar-hoja-ruta/{id}/{hoja_ruta}")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Validar_Recep_Observar(@PathVariable(value = "id") Integer documentoId,
			@PathVariable(value = "hoja_ruta") String hojaRuta) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = docService.Validar_Hoja_Ruta_Anexo(documentoId, hojaRuta);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/desasignar-solicitud")
	@Produces("application/json")
	public ResponseEntity<Auditoria> Documento_Desasignar(@Valid @RequestBody Documento documento) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria.Limpiar();
			auditoria = docService.Documento_Desasignar(documento.getId_documento());
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/agregar-expediente-reload")
	@Produces("application/json")
	public ResponseEntity<Auditoria> agregarAExpedienteReload(@Valid @RequestBody Documento documento) {
		Auditoria auditoria = new Auditoria();
		HrDto anexo = null;
		try {
			/*
			Path path = Paths.get(fileServer, id.toString(), fileName + "." + fileType.toLowerCase());
			File file = path.toFile();// new File(path.);
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			stream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			 */

			DocumentoAnexo itemAnexo = documento.getAnexos().get(0);

			String filename = itemAnexo.getCodigo_archivo();
			Path path = Paths.get(fileServer, itemAnexo.getId_documento() + "", filename);
			File file = path.toFile();
			byte[] fileByte = Files.readAllBytes(path);

			anexo = ventanillastdProxy.agregarAExpediente(
					"lmauricio",
					documento.getNumero_sid(),
					documento.getAnio(),
					new AnexoDto(
							fileByte,
							fileByte.length,
							itemAnexo.getNombre_archivo()
					),
					UserIdentityHelper.getClientIpAddress());

			logger.info("Anexo obtenido de SOAP: " + anexo);

			auditoria = docService.updateAnexo(String.valueOf(anexo.getIdanexo()),documento.getId_documento(), itemAnexo.getOrden());

		} catch (Exception ex) {
			auditoria.Error(ex);
			auditoria.mensaje_salida=auditoria.error_log;
			logger.info(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
}
