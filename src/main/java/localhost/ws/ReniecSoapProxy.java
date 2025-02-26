package localhost.ws;

public class ReniecSoapProxy implements localhost.ws.ReniecSoap {
  private String _endpoint = null;
  private localhost.ws.ReniecSoap reniecSoap = null;
  
  public ReniecSoapProxy() {
    _initReniecSoapProxy();
  }
  
  public ReniecSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initReniecSoapProxy();
  }
  
  private void _initReniecSoapProxy() {
    try {
      reniecSoap = (new localhost.ws.ReniecLocator()).getReniecSoap();
      if (reniecSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)reniecSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)reniecSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (reniecSoap != null)
      ((javax.xml.rpc.Stub)reniecSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public localhost.ws.ReniecSoap getReniecSoap() {
    if (reniecSoap == null)
      _initReniecSoapProxy();
    return reniecSoap;
  }
  
  public localhost.ws.Tabla[] TDni(java.lang.String noDNI) throws java.rmi.RemoteException{
    if (reniecSoap == null)
      _initReniecSoapProxy();
    return reniecSoap.TDni(noDNI);
  }
  
  
}