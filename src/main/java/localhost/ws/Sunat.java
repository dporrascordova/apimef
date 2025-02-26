/**
 * Sunat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package localhost.ws;

public interface Sunat extends javax.xml.rpc.Service {
    public java.lang.String getSunatSoapAddress();

    public localhost.ws.SunatSoap getSunatSoap() throws javax.xml.rpc.ServiceException;

    public localhost.ws.SunatSoap getSunatSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
