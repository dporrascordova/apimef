package mef.application.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import pe.gob.mef.std.bs.web.ws.VentanillastdProxy;
import pe.gob.mef.std.bs.web.ws.Ventanillastd_ServiceLocator;

@Component
public class VentanillaConfig {

    @Value("${ventanillaSTD.url}")
    private String ventanillaSTDUrl;

    @PostConstruct
    public void logConfig() {
        System.out.println("Loaded VentanillaSTD URL: " + ventanillaSTDUrl);
    }

    public String getVentanillaSTDUrl() {
        return ventanillaSTDUrl;
    }

    @Bean
    public VentanillastdProxy ventanillastdProxy(Ventanillastd_ServiceLocator serviceLocator) {
        VentanillastdProxy proxy = new VentanillastdProxy();
        proxy.setServiceLocator(serviceLocator); // Inyectar manualmente el servicio
        return proxy;
    }
}
