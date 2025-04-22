package mef.application.controlador;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import localhost.ws.ReniecLocator;
import localhost.ws.SunatLocator;
import localhost.ws.Tabla;
import mef.application.infrastructure.UserIdentityHelper;
import mef.application.modelo.Auditoria;
import mef.application.modelo.Distrito;
import mef.application.modelo.OficinaEstado;
import mef.application.modelo.OficinaGrilla;
import mef.application.modelo.Oficinas;
import mef.application.modelo.Persona;
import mef.application.modelo.Provincia;
import mef.application.modelo.TipoDocumento;
import mef.application.service.DocumentoService;
import mef.application.service.EstadoService;
import mef.application.service.FilesStorageService;
import mef.application.service.MefService;
import pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto;
import pe.gob.mef.std.bs.web.ws.IdValorDto;
import pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto;
import pe.gob.mef.std.bs.web.ws.VentanillastdProxy;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MefServicioController {
	@Value("${file.fileserver}")
	private String fileServer;

	@Autowired
	FilesStorageService storageService;

	@Autowired
	MefService mefService;

	@Autowired
	DocumentoService docService;

	@Autowired
	EstadoService estadoService;

	@Autowired
	VentanillastdProxy proxy;

	@GetMapping("/listaubigeo")
	public ResponseEntity<Auditoria> Mef_Listar_Ubigeos() {
		Auditoria auditoria = new Auditoria();

		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.ubigeos();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listacongresistas")
	public ResponseEntity<Auditoria> Mef_Listar_Congreso() {
		Auditoria auditoria = new Auditoria();
		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.congresistasActivos();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listarepetidos")
	public ResponseEntity<Auditoria> Mef_Listar_Repetidos(
			@RequestParam(name = "tipopersona", required = true) Integer tipopersona,
			@RequestParam(name = "nrodocpersona", required = true) String nrodocumentoP,
			@RequestParam(name = "idclaseRep", required = true) long id_tipo_documento,
			@RequestParam(name = "nrodocumento", required = true) String numero_documento) {
		Auditoria auditoria = new Auditoria();
		System.out.println("tpopersona" + tipopersona);
		System.out.println("nrodocpersona" + nrodocumentoP);
		System.out.println("idclaseRep" + id_tipo_documento);
		System.out.println("NRO DOC" + numero_documento);
		try {
			auditoria.Limpiar();
			long idProveedor = 0;
			boolean valido = false;
			Persona persona = new Persona();
			// VentanillastdProxy proxy = new VentanillastdProxy();
			IdValorDto[] mipersona;
			if (tipopersona.equals(1)) {
				System.out.println("natural 1 DNI: " + nrodocumentoP);
				mipersona = proxy.consultaPersonaXDNI(nrodocumentoP);

			} else {
				System.out.println("juridico 2 ");
				mipersona = proxy.consultaEntidadXRuc(nrodocumentoP);
			}
			if (mipersona != null) {
				// System.out.println("Entro 3 " + mipersona[0].getId());
				if (mipersona.length > 0) {
					System.out.println("Entro 4");
					for (int i = 0; i < mipersona.length; i++) {
						valido = true;
						idProveedor = mipersona[i].getId();
					}
				}
			}
			System.out.println("Entro 5");
			if (valido) {
				System.out.println("idproveedor: " + idProveedor + " tipodocumento:" + id_tipo_documento
						+ " NroDocumento" + numero_documento);
				auditoria.objeto = proxy.listaRepetidos(idProveedor, id_tipo_documento, numero_documento);
			}
			System.out.println("Entro 6");
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listaComisiones")
	public ResponseEntity<Auditoria> Mef_Listar_Comision() {
		Auditoria auditoria = new Auditoria();
		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.comisionessActivas();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listaClasificaciones")
	public ResponseEntity<Auditoria> Mef_Listar_Clasificaciones() {
		Auditoria auditoria = new Auditoria();
		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.listaClasificaciones();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listadep")
	public ResponseEntity<Auditoria> Mef_Dep_Listar() {
		Auditoria auditoria = new Auditoria();
		// Ubigeo ubigeo = new Ubigeo();
		// ubigeo.departamento = new ArrayList<>();
		try {
			auditoria = mefService.Mef_Servicio(3);
			if (auditoria.ejecucion_procedimiento) {
				if (auditoria.objeto.toString().equals("1")) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					AcMsUbigwsDto[] ubigeos = proxy.ubigeos();
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
					ubigeos = null;
				}
			}

			auditoria = mefService.Mef_Dep_Listar();
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/exportmanualexterno")
	public ResponseEntity<InputStreamResource> exportFileManualExterno() {
		ByteArrayInputStream stream = null;
		// System.out.println("Observacion : " + fileName);
		// System.out.println("Observacion : " + fileType);
		HttpHeaders headers = new HttpHeaders();
		// headers.add("Content-Disposition", "attachment; filename=Reportes.pdf");
		headers.add("Content-Type", "application/octet-stream");
		try {
			Path path = Paths.get(fileServer, "ManualExterno" + "." + "pdf");
			File file = path.toFile();// new File(path.);
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			headers.add("Content-Disposition", "attachment; filename=" + "ManualExterno" + "." + "pdf");
			stream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			stream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}

	@GetMapping("/listaprovincia/{id_departamento}")
	public ResponseEntity<Auditoria> Mef_Provincia(@PathVariable("id_departamento") Integer id_departamento) {
		Auditoria auditoria = new Auditoria();
		// Ubigeo ubigeo = new Ubigeo();
		// ubigeo.departamento = new ArrayList<>();
		try {
			auditoria = mefService.Mef_Servicio(4);
			if (auditoria.ejecucion_procedimiento) {
				if (auditoria.objeto.toString().equals("1")) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					AcMsUbigwsDto[] ubigeos = proxy.ubigeos();
					String cadena = "";
					int cuenta = 0;
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
					ubigeos = null;
				}
			}
			Provincia modelo = new Provincia();
			modelo.setId_departamento(id_departamento);
			auditoria = mefService.Mef_Prov_Listar(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listadistrito/{id_provincia}/{id_departamento}")
	public ResponseEntity<Auditoria> Mef_Distrito(@PathVariable("id_provincia") Integer id_provincia,
			@PathVariable("id_departamento") Integer id_departamento) {
		Auditoria auditoria = new Auditoria();
		// Ubigeo ubigeo = new Ubigeo();
		// ubigeo.departamento = new ArrayList<>();
		try {
			boolean enc = false;
			auditoria = mefService.Mef_Servicio(5);
			if (auditoria.ejecucion_procedimiento) {
				if (auditoria.objeto.toString().equals("1")) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					AcMsUbigwsDto[] ubigeos = proxy.ubigeos();
					String cadena = "";
					int cuenta = 0;
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

							/*
							 * if (ubi.getNombre().equals("CHORRILLOS")) {
							 * System.out.println("ESTE ES");
							 * System.out.println(cadena);
							 * enc = true;
							 * }
							 */
							cadena += id_dist + "|" + id_prov + "|" + id_dep + "|" + Descripcion;
							cuenta++;

							if (cadena.length() > 3500) {
								/*
								 * if (enc) {
								 * System.out.println("entro a actualizar ES");
								 * System.out.println(cadena);
								 * enc = false;
								 * }
								 */

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
				}
			}
			Distrito modelo = new Distrito();
			modelo.setId_provincia(id_provincia);
			modelo.setId_departamento(id_departamento);
			auditoria = mefService.Mef_Dist_Listar(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/mefconsultapersona/{nrodocumento}/{tipopersona}")
	public ResponseEntity<Auditoria> Mef_Persona(@PathVariable("nrodocumento") String nrodocumento,
			@PathVariable("tipopersona") Integer tipopersona) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria.Limpiar();
			Persona persona = new Persona();

			boolean valido = false;
			String NRO_DOCUMENTO = "0";
			String APE_PATERNO = "";
			String APE_MATERNO = "";
			String NOMBRES = "";
			String RUC = "";
			String RAZON_SOCIAL = "";

			// if (!valido) {
			// System.out.println("Entro 2223 ");
			boolean validoreniecsunat = false;
			if (tipopersona.equals(1)) {
				// System.out.println("Entro 2223xxxx ");
				ReniecLocator sun = new ReniecLocator();
				System.out.println("Entro a natural - RENIEC  ");
				System.out.println("DNI:" + nrodocumento);
				Tabla[] PersonaDni = sun.getReniecSoap().TDni(nrodocumento); // Dni(nrodocumento);
				System.out.println("Paso RENIEC  ");
				if (PersonaDni.length > 0) {
					System.out.println("Hay Data!  ");
					NRO_DOCUMENTO = nrodocumento;
					APE_PATERNO = PersonaDni[0].getT02();
					APE_MATERNO = PersonaDni[0].getT03();
					NOMBRES = PersonaDni[0].getT04();
					validoreniecsunat = true;
				}
			} else {
				SunatLocator sun = new SunatLocator();
				System.out.println("Entro a juridica - SUNAT  ");
				System.out.println("RUC:" + nrodocumento);
				Tabla[] PersonaSunat = sun.getSunatSoap().TRuc(nrodocumento);
				System.out.println("Entro 2");
				if (PersonaSunat.length > 0) {
					System.out.println("Entro 3 datos");
					RUC = nrodocumento;
					RAZON_SOCIAL = PersonaSunat[0].getT01();
					validoreniecsunat = true;
				}
			}
			/*
			 * if (!validoreniecsunat) { System.out.println("Entro 4 ");
			 * auditoria.Rechazar("No se encontraron registros con ese documento"); }
			 */
			// }
			System.out.println("4 " + validoreniecsunat);
			if (!validoreniecsunat) {
				// VentanillastdProxy proxy = new VentanillastdProxy();
				IdValorDto[] mipersona;
				if (tipopersona.equals(1)) {
					System.out.println("Entro 1 ");
					mipersona = proxy.consultaPersonaXDNI(nrodocumento);
					System.out.println("Entro 2 ");
				} else {
					mipersona = proxy.consultaEntidadXRuc(nrodocumento);
				}
				if (mipersona != null) {
					System.out.println("Entro 5 ");
					if (mipersona.length > 0) {
						System.out.println("Entro 8 ");
						for (int i = 0; i < mipersona.length; i++) {
							System.out.println("Entro 9 ");
							valido = true;
							if (tipopersona.equals(1)) {
								String nombre = mipersona[i].getValor();
								NRO_DOCUMENTO = mipersona[i].getId().toString();
								APE_PATERNO = nombre.split(",")[0].split(" ")[0].trim();
								APE_MATERNO = nombre.split(",")[0].split(" ")[1].trim();
								NOMBRES = nombre.split(",")[1].trim();
							} else {
								RUC = nrodocumento;
								RAZON_SOCIAL = mipersona[i].getValor().trim();
							}
						}
					}
				}
			}
			if (!valido && !validoreniecsunat) {
				System.out.println("Entro 4 ");
				auditoria.Rechazar("No se encontraron registros con ese documento");
			}
			persona.setUDocumento(Integer.valueOf(NRO_DOCUMENTO));
			persona.setNat_Ape_Pat(APE_PATERNO);
			persona.setNat_Ape_Mat(APE_MATERNO);
			persona.setNat_Nombres(NOMBRES);
			persona.setJur_RUC(RUC);
			persona.setJur_RazonSocial(RAZON_SOCIAL);
			auditoria.objeto = persona;
		} catch (Exception ex) {
			System.out.println("Entro 3");
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}

		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/listaoficinastodo")
	public ResponseEntity<Auditoria> Mef_Oficinas_Listar(@Valid @RequestBody OficinaGrilla modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = mefService.Mef_Oficinas_Listar(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/listatipodoctodo")
	public ResponseEntity<Auditoria> Mef_Tipo_Doc_Listar_Lx(@Valid @RequestBody TipoDocumento modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = mefService.Mef_Tipo_Doc_Listar_Lx(modelo);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/tipodocestado")
	public ResponseEntity<Auditoria> Mef_Tipo_Doc_Estado(@Valid @ModelAttribute TipoDocumento modelo) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = mefService.Mef_Tipo_Doc_Estado(modelo);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listaoficinasinterno")
	public ResponseEntity<Auditoria> Mef_Oficinas_Listar_Interno() {
		Auditoria auditoria = new Auditoria();
		Oficinas oficinas = new Oficinas();
		oficinas.lista_oficinas = new ArrayList<>();
		try {
			auditoria = mefService.Mef_Servicio(1);
			if (auditoria.ejecucion_procedimiento) {
				if (auditoria.objeto.toString().equals("1")) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					UnidadesOrganicasTreeDto[] mioficinas = proxy.unidadesOrganicas();
					String cadena = "";
					int cuenta = 0;
					for (int i = 0; i < mioficinas.length; i++) {
						String conjefe = "0";
						String Jefe = "-";
						if (mioficinas[i].isConjefe()) {
							conjefe = "1";
							Jefe = mioficinas[i].getJefe().isEmpty() ? "-" : mioficinas[i].getJefe();
						}
						if (cuenta != 0) {
							cadena += "=";
						}
						Long Idunidad = mioficinas[i].getIdunidad();
						String Acronimo = mioficinas[i].getAcronimo().isEmpty() ? "-" : mioficinas[i].getAcronimo();
						String Codigo = mioficinas[i].getCodigo().isEmpty() ? "-" : mioficinas[i].getCodigo();
						String Descripcion = mioficinas[i].getDescripcion().isEmpty() ? "-"
								: mioficinas[i].getDescripcion();
						String DescripcionCompleta = mioficinas[i].getDescripcionCompleta().isEmpty() ? "-"
								: mioficinas[i].getDescripcionCompleta();

						cadena += Idunidad + "|" + Acronimo + "|" + Codigo + "|" + conjefe + "|" + Descripcion + "|"
								+ DescripcionCompleta + "|" + Jefe.replace("'", "");
						cuenta++;

						if (cadena.length() > 3500) {
							auditoria = mefService.Mef_Oficinas_Actualizar(cadena);
							cadena = "";
							cuenta = 0;
						}
					}
					if (cadena != "") {
						auditoria = mefService.Mef_Oficinas_Actualizar(cadena);
						cadena = "";
						cuenta = 0;
					}
					mioficinas = null;
				}
			}
			oficinas.setFlg_interno(1);
			auditoria = mefService.Mef_Oficinas_Listar_Interno(oficinas);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listaoficinasexterno")
	public ResponseEntity<Auditoria> Mef_Oficinas_Listar_Externo() {
		Auditoria auditoria = new Auditoria();
		Oficinas oficinas = new Oficinas();
		oficinas.lista_oficinas = new ArrayList<>();
		try {
			auditoria = mefService.Mef_Servicio(1);
			if (auditoria.ejecucion_procedimiento) {
				if (auditoria.objeto.toString().equals("1")) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					UnidadesOrganicasTreeDto[] mioficinas = proxy.unidadesOrganicas();
					String cadena = "";
					int cuenta = 0;
					for (int i = 0; i < mioficinas.length; i++) {
						String conjefe = "0";
						String Jefe = "-";
						if (mioficinas[i].isConjefe()) {
							conjefe = "1";
							Jefe = mioficinas[i].getJefe().isEmpty() ? "-" : mioficinas[i].getJefe();
						}
						if (cuenta != 0) {
							cadena += "=";
						}
						Long Idunidad = mioficinas[i].getIdunidad();
						String Acronimo = mioficinas[i].getAcronimo().isEmpty() ? "-" : mioficinas[i].getAcronimo();
						String Codigo = mioficinas[i].getCodigo().isEmpty() ? "-" : mioficinas[i].getCodigo();
						String Descripcion = mioficinas[i].getDescripcion().isEmpty() ? "-"
								: mioficinas[i].getDescripcion();
						String DescripcionCompleta = mioficinas[i].getDescripcionCompleta().isEmpty() ? "-"
								: mioficinas[i].getDescripcionCompleta();

						cadena += Idunidad + "|" + Acronimo + "|" + Codigo + "|" + conjefe + "|" + Descripcion + "|"
								+ DescripcionCompleta + "|" + Jefe.replace("'", "");
						cuenta++;

						if (cadena.length() > 3500) {
							auditoria = mefService.Mef_Oficinas_Actualizar(cadena);
							cadena = "";
							cuenta = 0;
						}
					}
					if (cadena != "") {
						auditoria = mefService.Mef_Oficinas_Actualizar(cadena);
						cadena = "";
						cuenta = 0;
					}
					mioficinas = null;
				}
			}
			oficinas.setFlg_externo(1);
			auditoria = mefService.Mef_Oficinas_Listar_Externo(oficinas);
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@PostMapping("/oficinaestadoex")
	public ResponseEntity<Auditoria> Mef_Oficinas_Estado_Externo(@Valid @ModelAttribute OficinaEstado oficina) {
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = mefService.Mef_Oficinas_Estado_Externo(oficina);
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/listatipodedoc")
	public ResponseEntity<Auditoria> Mef_TipoDocumento() {
		Auditoria auditoria = new Auditoria();
		TipoDocumento tiposdoc = new TipoDocumento();
		tiposdoc.lista_tipodoc = new ArrayList<>();
		try {
			auditoria = mefService.Mef_Servicio(2);
			if (auditoria.ejecucion_procedimiento) {
				if (auditoria.objeto.toString().equals("1")) {
					// VentanillastdProxy proxy = new VentanillastdProxy();
					IdValorDto[] mitipos = proxy.tiposDocumentos();

					System.out.println(mitipos.length);

					String cadena = "";
					int cuenta = 0;
					for (int i = 0; i < mitipos.length; i++) {
						if (cuenta != 0) {
							cadena += "=";
						}
						Long Idunidad = mitipos[i].getId();
						String Acronimo = mitipos[i].getValor().isEmpty() ? "-" : mitipos[i].getValor();

						cadena += Idunidad + "|" + Acronimo;
						cuenta++;

						if (cadena.length() > 3500) {
							auditoria = mefService.Mef_TipoDoc_Actualizar(cadena);
							cadena = "";
							cuenta = 0;
						}
					}
					if (cadena != "") {
						auditoria = mefService.Mef_TipoDoc_Actualizar(cadena);
						cadena = "";
						cuenta = 0;
					}
					mitipos = null;
				}
			}

			auditoria = mefService.Mef_Tipo_Doc_Listar();
			if (!auditoria.ejecucion_procedimiento) {
				System.out.println(auditoria.error_log);
			}
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/consultaexpediente/{anio}/{numeroSid}")
	public ResponseEntity<Auditoria> Mef_Expediente(@PathVariable("anio") Integer anio,
			@PathVariable("numeroSid") String numeroSid) {
		Auditoria auditoria = new Auditoria();
		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.consultaExpediente(anio, numeroSid);
			auditoria.Limpiar();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/consultaestadoexpediente/{anio}/{nombrecorto}/{numeroSid}")
	public ResponseEntity<Auditoria> Mef_Expediente_estado(@PathVariable("anio") Integer anio,
			@PathVariable("nombrecorto") String nombrecorto, @PathVariable("numeroSid") String numeroSid) {
		Auditoria auditoria = new Auditoria();
		try {
			String USU = new UserIdentityHelper().get_CodigoUsuario();
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.estadoDeExpediente(USU, numeroSid, anio, UserIdentityHelper.getClientIpAddress());
			auditoria.Limpiar();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/consultainstrucciones")
	public ResponseEntity<Auditoria> Mef_Instrucciones() {
		Auditoria auditoria = new Auditoria();
		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.instrucciones();
			auditoria.Limpiar();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}

	@GetMapping("/consultapersonalmef")
	public ResponseEntity<Auditoria> Mef_Personal() {
		Auditoria auditoria = new Auditoria();
		try {
			// VentanillastdProxy proxy = new VentanillastdProxy();
			auditoria.objeto = proxy.listaUsuariosMesa();
			auditoria.Limpiar();
		} catch (Exception ex) {
			auditoria.Error(ex);
			System.out.println(auditoria.error_log);
		}
		return new ResponseEntity<Auditoria>(auditoria, HttpStatus.OK);
	}
}
