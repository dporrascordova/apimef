package localhost.ws;

public class SunatSoapProxy implements localhost.ws.SunatSoap {
  private String _endpoint = null;
  private localhost.ws.SunatSoap sunatSoap = null;
  
  public SunatSoapProxy() {
    _initSunatSoapProxy();
  }
  
  public SunatSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSunatSoapProxy();
  }
  
  private void _initSunatSoapProxy() {
    try {
      sunatSoap = (new localhost.ws.SunatLocator()).getSunatSoap();
      if (sunatSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sunatSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sunatSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sunatSoap != null)
      ((javax.xml.rpc.Stub)sunatSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public localhost.ws.SunatSoap getSunatSoap() {
    if (sunatSoap == null)
      _initSunatSoapProxy();
    return sunatSoap;
  }
  
  public localhost.ws.Tabla[] TRuc(java.lang.String noRUC) throws java.rmi.RemoteException{
    if (sunatSoap == null)
      _initSunatSoapProxy();
    return sunatSoap.TRuc(noRUC);
  }
  
  
}