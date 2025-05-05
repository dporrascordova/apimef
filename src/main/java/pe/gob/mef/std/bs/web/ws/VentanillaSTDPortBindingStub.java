/**
 * VentanillaSTDPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class VentanillaSTDPortBindingStub extends org.apache.axis.client.Stub implements pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    private static final Logger logger = LoggerFactory.getLogger(VentanillaSTDPortBindingStub.class);

    static {
        _operations = new org.apache.axis.description.OperationDesc[21];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "anio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "numero"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "expedienteWSDto"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.ExpedienteWSDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "expediente"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaRepetidos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idproveRep"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idclaseRep"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "numerodocRep"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdExpedienteRepetidosWSDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "docsRepet"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("anexarAExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NOMBRECORTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROSID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROANIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUM_OFICIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ANEXOS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDtoArray"), pe.gob.mef.std.bs.web.ws.AnexoDto[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "REMOTEADDRESS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "UNIDADES"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdFlujoSDtoArray"), pe.gob.mef.std.bs.web.ws.TdFlujoSDto[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "hrDto"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.HrDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "URL"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("instrucciones");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "instrucciones"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("estadoDeExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NOMBRECORTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROSID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROANIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "REMOTEADDRESS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDto"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "estado"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaUsuariosMesa");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ttUsuariosSistemaDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaUsuarios"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaDiasFeriados");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taFeriadosDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.TaFeriadosDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaDiasFeriados"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaClasificaciones");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taInformacionCompDTOArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaClasificaciones"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("levantarObservacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NOMBRECORTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROSID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROANIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUM_OFICIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ANEXOS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDtoArray"), pe.gob.mef.std.bs.web.ws.AnexoDto[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "REMOTEADDRESS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "URLANEXOS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "hrDto"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.HrDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "URL"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("agregarAExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NOMBRECORTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROSID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUMEROANIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ANEXO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDto"), pe.gob.mef.std.bs.web.ws.AnexoDto.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "REMOTEADDRESS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "hrDto"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.HrDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "URL"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("crearExpediente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NOMBRECORTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUM_REGISTRO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TIPO_DOCUMENTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUM_OFICIO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NUM_FOLIOS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ASUNTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "APELLIDOPATERNO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "APELLIDOMATERNO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NOMBRES"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "DNI"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TELEFONO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "RAZONSOCIAL"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "RUC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "DIRECCION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "DEPARTAMENTO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "PROVINCIA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "DISTRITO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CORREO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ANEXOS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDtoArray"), pe.gob.mef.std.bs.web.ws.AnexoDto[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "REMOTEADDRESS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "UNIDADES"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdFlujoSDtoArray"), pe.gob.mef.std.bs.web.ws.TdFlujoSDto[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IDCONGRESISTA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IDCOMISION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CLASIFICACIONES"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://jaxb.dev.java.net/array", "longArray"), long[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "PRIORIDAD"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ANEXOSHR"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "hrDto"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.HrDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "URL"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("estadosExpediente");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "estadosExp"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ubigeos");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "acMsUbigwsDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ubigeos"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaEntidadXRazon");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "razonSocial"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entidadesRazon"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaEntidadXRuc");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ruc"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entidadesRuc"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultaPersonaXDNI");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dni"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "personasDNI"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("congresistasActivos");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "congresistas"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("comisionessActivas");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "comisiones"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("comisionesCongre");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoComCongreArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "comisionesYcongre"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("tiposDocumentos");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "tiposDocumentos"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unidadesOrganicas");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "unidadesOrganicasTreeDtoArray"));
        oper.setReturnClass(pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "unidadesOrganicas"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ErrorDeServicio"),
                      "pe.gob.mef.std.bs.web.ws.ErrorInfo",
                      new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo"), 
                      true
                     ));
        _operations[20] = oper;

    }

    public VentanillaSTDPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public VentanillaSTDPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public VentanillaSTDPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://jaxb.dev.java.net/array", "longArray");
            cachedSerQNames.add(qName);
            cls = long[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "acMsUbigwsDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "acMsUbigwsDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "acMsUbigwsDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.AnexoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.AnexoDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexoDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "errorInfo");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.ErrorInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "expedienteWSDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.ExpedienteWSDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "hrDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.HrDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.IdValorDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.IdValorDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoComCongre");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoComCongreArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoComCongre");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "movimientoWSDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.MovimientoWSDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taFeriadosDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TaFeriadosDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taFeriadosDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TaFeriadosDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taFeriadosDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taInformacionCompDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taInformacionCompDTOArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taInformacionCompDTO");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdExpedienteRepetidosWSDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdExpedienteRepetidosWSDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdExpedienteRepetidosWSDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdFlujoSDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TdFlujoSDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdFlujoSDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TdFlujoSDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdFlujoSDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ttUsuariosSistemaDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ttUsuariosSistemaDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ttUsuariosSistemaDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "unidadesOrganicasTreeDto");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "unidadesOrganicasTreeDtoArray");
            cachedSerQNames.add(qName);
            cls = pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "unidadesOrganicasTreeDto");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public pe.gob.mef.std.bs.web.ws.ExpedienteWSDto consultaExpediente(int anio, java.lang.String numero) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "consultaExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(anio), numero});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.ExpedienteWSDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.ExpedienteWSDto) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.ExpedienteWSDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[] listaRepetidos(long idproveRep, long idclaseRep, java.lang.String numerodocRep) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "listaRepetidos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(idproveRep), new java.lang.Long(idclaseRep), numerodocRep});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.TdExpedienteRepetidosWSDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.HrDto anexarAExpediente(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID, int NUMEROANIO, java.lang.String NUM_OFICIO, pe.gob.mef.std.bs.web.ws.AnexoDto[] ANEXOS, java.lang.String REMOTEADDRESS, pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] UNIDADES) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "anexarAExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {NOMBRECORTO, NUMEROSID, new java.lang.Integer(NUMEROANIO), NUM_OFICIO, ANEXOS, REMOTEADDRESS, UNIDADES});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.HrDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.HrDto) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.HrDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] instrucciones() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "instrucciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto estadoDeExpediente(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID, int NUMEROANIO, java.lang.String REMOTEADDRESS) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "estadoDeExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {NOMBRECORTO, NUMEROSID, new java.lang.Integer(NUMEROANIO), REMOTEADDRESS});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[] listaUsuariosMesa() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "listaUsuariosMesa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.TtUsuariosSistemaDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.TaFeriadosDto[] listaDiasFeriados() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "listaDiasFeriados"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.TaFeriadosDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.TaFeriadosDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.TaFeriadosDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[] listaClasificaciones() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "listaClasificaciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.TaInformacionCompDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.HrDto levantarObservacion(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID, int NUMEROANIO, java.lang.String NUM_OFICIO, pe.gob.mef.std.bs.web.ws.AnexoDto[] ANEXOS, java.lang.String REMOTEADDRESS, java.lang.String URLANEXOS) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "levantarObservacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {NOMBRECORTO, NUMEROSID, new java.lang.Integer(NUMEROANIO), NUM_OFICIO, ANEXOS, REMOTEADDRESS, URLANEXOS});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.HrDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.HrDto) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.HrDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.HrDto agregarAExpediente(java.lang.String NOMBRECORTO, java.lang.String NUMEROSID, int NUMEROANIO, pe.gob.mef.std.bs.web.ws.AnexoDto ANEXO, java.lang.String REMOTEADDRESS) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "agregarAExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{NOMBRECORTO, NUMEROSID, new java.lang.Integer(NUMEROANIO), ANEXO, REMOTEADDRESS});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (pe.gob.mef.std.bs.web.ws.HrDto) _resp;
                } catch (java.lang.Exception _exception) {
                    return (pe.gob.mef.std.bs.web.ws.HrDto) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.HrDto.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
                    throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    public pe.gob.mef.std.bs.web.ws.HrDto crearExpediente(java.lang.String NOMBRECORTO, java.lang.String NUM_REGISTRO, long TIPO_DOCUMENTO, java.lang.String NUM_OFICIO, int NUM_FOLIOS, java.lang.String ASUNTO, java.lang.String APELLIDOPATERNO, java.lang.String APELLIDOMATERNO, java.lang.String NOMBRES, java.lang.String DNI, java.lang.String TELEFONO, java.lang.String RAZONSOCIAL, java.lang.String RUC, java.lang.String DIRECCION, java.lang.String DEPARTAMENTO, java.lang.String PROVINCIA, java.lang.String DISTRITO, java.lang.String CORREO, pe.gob.mef.std.bs.web.ws.AnexoDto[] ANEXOS, java.lang.String REMOTEADDRESS, pe.gob.mef.std.bs.web.ws.TdFlujoSDto[] UNIDADES, Long IDCONGRESISTA, Long IDCOMISION, long[] CLASIFICACIONES, int PRIORIDAD, java.lang.String ANEXOSHR) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "crearExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {NOMBRECORTO, NUM_REGISTRO, new java.lang.Long(TIPO_DOCUMENTO), NUM_OFICIO, new java.lang.Integer(NUM_FOLIOS), ASUNTO, APELLIDOPATERNO, APELLIDOMATERNO, NOMBRES, DNI, TELEFONO, RAZONSOCIAL, RUC, DIRECCION, DEPARTAMENTO, PROVINCIA, DISTRITO, CORREO, ANEXOS, REMOTEADDRESS, UNIDADES, IDCONGRESISTA, IDCOMISION, CLASIFICACIONES, new java.lang.Integer(PRIORIDAD), ANEXOSHR});

        if (_resp instanceof java.rmi.RemoteException) {
            logger.error(((RemoteException) _resp).getMessage());
            throw (java.rmi.RemoteException)_resp;

        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.HrDto) _resp;
            } catch (java.lang.Exception _exception) {
                logger.error(_exception.getMessage());
                return (pe.gob.mef.std.bs.web.ws.HrDto) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.HrDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
            logger.error(axisFaultException.detail.getMessage());
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
            logger.error(axisFaultException.detail.getMessage());
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] estadosExpediente() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "estadosExpediente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[] ubigeos() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ubigeos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] consultaEntidadXRazon(java.lang.String razonSocial) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "consultaEntidadXRazon"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {razonSocial});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] consultaEntidadXRuc(java.lang.String ruc) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "consultaEntidadXRuc"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ruc});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] consultaPersonaXDNI(java.lang.String dni) throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "consultaPersonaXDNI"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dni});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] congresistasActivos() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "congresistasActivos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] comisionessActivas() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "comisionessActivas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[] comisionesCongre() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "comisionesCongre"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDtoComCongre[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto[] tiposDocumentos() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tiposDocumentos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.IdValorDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.IdValorDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[] unidadesOrganicas() throws java.rmi.RemoteException, pe.gob.mef.std.bs.web.ws.ErrorInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "unidadesOrganicas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mef.std.bs.web.ws.UnidadesOrganicasTreeDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.mef.std.bs.web.ws.ErrorInfo) {
              throw (pe.gob.mef.std.bs.web.ws.ErrorInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
