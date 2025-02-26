package mef.application.consumingws;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class UsuarioClient extends WebServiceGatewaySupport{
	/*
	private static final Logger log = LoggerFactory.getLogger(UsuarioClient.class);
	  
	public ValidarUsuarioResponse validaUsuario(String user, String password) {
		System.out.println("Entro 2 " + user + password);
		ObjectFactory factory = new ObjectFactory();
		JAXBElement<String> vUser = factory.createValidarUsuarioUSUARIO(user);
		JAXBElement<String> vPassword = factory.createValidarUsuarioCLAVE(password);
		System.out.println("Entro 3 " + vPassword);
		ValidarUsuario vUsuario = new ValidarUsuario();
		vUsuario.setCLAVE(vPassword);
		vUsuario.setUSUARIO(vUser);

	 	ValidarUsuarioResponse response = (ValidarUsuarioResponse) getWebServiceTemplate().
				marshalSendAndReceive("http://linksoft-desarrollo.sytes.net/WS_Seguridad/WSSeguridad.svc", vUsuario, 
						new SoapActionCallback("http://tempuri.org/IWSSeguridad/ValidarUsuario"));
	 	log.info("response: "+response);
		return response;
	}
	*/
	
	/*public Object Ubigeo(String user, String password) {
		System.out.println("Entro 2 " + user + password);
		ObjectFactory factory = new ObjectFactory();
		JAXBElement<String> vUser = factory.createValidarUsuarioUSUARIO(user);
		JAXBElement<String> vPassword = factory.createValidarUsuarioCLAVE(password);
		System.out.println("Entro 3 " + vPassword);
		ValidarUsuario vUsuario = new ValidarUsuario();
		vUsuario.setCLAVE(vPassword);
		vUsuario.setUSUARIO(vUser);
		http://10.5.112.43:8080/tramite/webservice/ventanillastd?wsdl
	 	ValidarUsuarioResponse response = (ValidarUsuarioResponse) getWebServiceTemplate().
				marshalSendAndReceive("http://linksoft-desarrollo.sytes.net/WS_Seguridad/WSSeguridad.svc", vUsuario, 
						new SoapActionCallback("http://tempuri.org/IWSSeguridad/ValidarUsuario"));
	 	log.info("response: "+response);
		return response;
	}*/

}


