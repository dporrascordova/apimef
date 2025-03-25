/**
 * Ventanillastd_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class Ventanillastd_ServiceLocator extends org.apache.axis.client.Service implements pe.gob.mef.std.bs.web.ws.Ventanillastd_Service {

    public Ventanillastd_ServiceLocator() {
    }


    public Ventanillastd_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Ventanillastd_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for VentanillaSTDPort
//     private java.lang.String VentanillaSTDPort_address = "http://sisadm.mef.gob.pe:8280/tramite/webservice/ventanillastd?wsdl";
//    private java.lang.String VentanillaSTDPort_address = "http://10.5.115.48:8080/tramite/webservice/ventanillastd?wsdl";  //error
   private java.lang.String VentanillaSTDPort_address = "http://10.5.112.43:8080/tramite/webservice/ventanillastd?wsdl";
    //http://sisadm.mef.gob.pe:8280/tramite/webservice/ventanillastd?wsdl
    //http://10.5.115.48:8080/tramite/webservice/ventanillastd?wsdl

    public java.lang.String getVentanillaSTDPortAddress() {
        return VentanillaSTDPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String VentanillaSTDPortWSDDServiceName = "VentanillaSTDPort";

    public java.lang.String getVentanillaSTDPortWSDDServiceName() {
        return VentanillaSTDPortWSDDServiceName;
    }

    public void setVentanillaSTDPortWSDDServiceName(java.lang.String name) {
        VentanillaSTDPortWSDDServiceName = name;
    }

    public pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType getVentanillaSTDPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(VentanillaSTDPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getVentanillaSTDPort(endpoint);
    }

    public pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType getVentanillaSTDPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pe.gob.mef.std.bs.web.ws.VentanillaSTDPortBindingStub _stub = new pe.gob.mef.std.bs.web.ws.VentanillaSTDPortBindingStub(portAddress, this);
            _stub.setPortName(getVentanillaSTDPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setVentanillaSTDPortEndpointAddress(java.lang.String address) {
        VentanillaSTDPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pe.gob.mef.std.bs.web.ws.Ventanillastd_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                pe.gob.mef.std.bs.web.ws.VentanillaSTDPortBindingStub _stub = new pe.gob.mef.std.bs.web.ws.VentanillaSTDPortBindingStub(new java.net.URL(VentanillaSTDPort_address), this);
                _stub.setPortName(getVentanillaSTDPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("VentanillaSTDPort".equals(inputPortName)) {
            return getVentanillaSTDPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "ventanillastd");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "VentanillaSTDPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("VentanillaSTDPort".equals(portName)) {
            setVentanillaSTDPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
