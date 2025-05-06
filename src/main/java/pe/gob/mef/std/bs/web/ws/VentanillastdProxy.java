package pe.gob.mef.std.bs.web.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VentanillastdProxy implements pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType {
  private static final Logger logger = LoggerFactory.getLogger(VentanillastdProxy.class);

  private String _endpoint = null;
  private pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType ventanillastd_PortType = null;
  private Ventanillastd_ServiceLocator serviceLocator;

  public void setServiceLocator(Ventanillastd_ServiceLocator serviceLocator) {
    this.serviceLocator = serviceLocator;
    _initVentanillastdProxy();
  }

  public VentanillastdProxy() {
  }

  private void _initVentanillastdProxy() {
    try {
      if (serviceLocator != null) {
        ventanillastd_PortType = serviceLocator.getVentanillaSTDPort();

        if (ventanillastd_PortType != null) {
          if (_endpoint != null) {
            ((javax.xml.rpc.Stub) ventanillastd_PortType)._setProperty("javax.xml.rpc.service.endpoint.address",
                _endpoint);
          } else {
            _endpoint = (String) ((javax.xml.rpc.Stub) ventanillastd_PortType)
                ._getProperty("javax.xml.rpc.service.endpoint.address");
          }
        }
      } else {
        System.out.println("Error: serviceLocator is null.");
      }

    } catch (javax.xml.rpc.ServiceException serviceException) {
      serviceException.printStackTrace();
    }
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ventanillastd_PortType != null)
      ((javax.xml.rpc.Stub) ventanillastd_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType getVentanillastd_PortType() {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType;
  }

  public pe.gob.mef.std.bs.web.ws.ExpedienteWSDto consultaExpediente(int anio, java.lang.String numero)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.consultaExpediente(anio, numero);
  }

  public pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[] listaRepetidos(long idproveRep, long idclaseRep,
      java.lang.String numerodocRep) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.listaRepetidos(idproveRep, idclaseRep, numerodocRep);
  }

  public pe.gob.mef.std.bs.web.ws.HrDto anexarAExpediente(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID,
      int NUMEROANIO, java.lang.String NUM_OFICIO, pe.gob.mef.std.bs.web.ws.AnexoDto[] ANEXOS,
      java.lang.String REMOTEADDRESS, pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] UNIDADES)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    logger.info(">>>Inicio anexarAExpediente()");
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.anexarAExpediente(NOMBRECORTO, NUMEROSID, NUMEROANIO, NUM_OFICIO, ANEXOS,
        REMOTEADDRESS, UNIDADES);
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] instrucciones()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.instrucciones();
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto estadoDeExpediente(java.lang.String NOMBRECORTO,
      java.lang.String NUMEROSID, int NUMEROANIO, java.lang.String REMOTEADDRESS)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.estadoDeExpediente(NOMBRECORTO, NUMEROSID, NUMEROANIO, REMOTEADDRESS);
  }

  public pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[] listaUsuariosMesa()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.listaUsuariosMesa();
  }

  public pe.gob.mef.std.bs.web.ws.TaFeriadosDto[] listaDiasFeriados()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.listaDiasFeriados();
  }

  public pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[] listaClasificaciones()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.listaClasificaciones();
  }

  public pe.gob.mef.std.bs.web.ws.HrDto levantarObservacion(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID,
      int NUMEROANIO, java.lang.String NUM_OFICIO, pe.gob.mef.std.bs.web.ws.AnexoDto[] ANEXOS,
      java.lang.String REMOTEADDRESS, java.lang.String URLANEXOS)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.levantarObservacion(NOMBRECORTO, NUMEROSID, NUMEROANIO, NUM_OFICIO, ANEXOS,
        REMOTEADDRESS, URLANEXOS);
  }

  public pe.gob.mef.std.bs.web.ws.HrDto agregarAExpediente(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID,
      int NUMEROANIO, pe.gob.mef.std.bs.web.ws.AnexoDto ANEXO, java.lang.String REMOTEADDRESS)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    logger.info(">>>Inicio agregarAExpediente()");

    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.agregarAExpediente(NOMBRECORTO, NUMEROSID, NUMEROANIO, ANEXO, REMOTEADDRESS);
  }

  public pe.gob.mef.std.bs.web.ws.HrDto crearExpediente(java.lang.String NOMBRECORTO, java.lang.String NUM_REGISTRO,
      long TIPO_DOCUMENTO, java.lang.String NUM_OFICIO, int NUM_FOLIOS, java.lang.String ASUNTO,
      java.lang.String APELLIDOPATERNO, java.lang.String APELLIDOMATERNO, java.lang.String NOMBRES,
      java.lang.String DNI, java.lang.String TELEFONO, java.lang.String RAZONSOCIAL, java.lang.String RUC,
      java.lang.String DIRECCION, java.lang.String DEPARTAMENTO, java.lang.String PROVINCIA, java.lang.String DISTRITO,
      java.lang.String CORREO, pe.gob.mef.std.bs.web.ws.AnexoDto[] ANEXOS, java.lang.String REMOTEADDRESS,
      pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] UNIDADES, Long IDCONGRESISTA, Long IDCOMISION, long[] CLASIFICACIONES,
      int PRIORIDAD, java.lang.String ANEXOSHR) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.crearExpediente(NOMBRECORTO, NUM_REGISTRO, TIPO_DOCUMENTO, NUM_OFICIO, NUM_FOLIOS,
        ASUNTO, APELLIDOPATERNO, APELLIDOMATERNO, NOMBRES, DNI, TELEFONO, RAZONSOCIAL, RUC, DIRECCION, DEPARTAMENTO,
        PROVINCIA, DISTRITO, CORREO, ANEXOS, REMOTEADDRESS, UNIDADES, IDCONGRESISTA, IDCOMISION, CLASIFICACIONES,
        PRIORIDAD, ANEXOSHR);
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] estadosExpediente()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.estadosExpediente();
  }

  public pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[] ubigeos()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.ubigeos();
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] consultaEntidadXRazon(java.lang.String razonSocial)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.consultaEntidadXRazon(razonSocial);
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] consultaEntidadXRuc(java.lang.String ruc)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.consultaEntidadXRuc(ruc);
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] consultaPersonaXDNI(java.lang.String dni)
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.consultaPersonaXDNI(dni);
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] congresistasActivos()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.congresistasActivos();
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] comisionessActivas()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.comisionessActivas();
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[] comisionesCongre()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.comisionesCongre();
  }

  public pe.gob.mef.std.bs.web.ws.IdValorDto[] tiposDocumentos()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.tiposDocumentos();
  }

  public pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[] unidadesOrganicas()
      throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
    if (ventanillastd_PortType == null)
      _initVentanillastdProxy();
    return ventanillastd_PortType.unidadesOrganicas();
  }

}