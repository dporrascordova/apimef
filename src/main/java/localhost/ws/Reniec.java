/**
 * Reniec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package localhost.ws;

public interface Reniec extends javax.xml.rpc.Service {
    public java.lang.String getReniecSoapAddress();

    public localhost.ws.ReniecSoap getReniecSoap() throws javax.xml.rpc.ServiceException;

    public localhost.ws.ReniecSoap getReniecSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
