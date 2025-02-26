package mef.application.configuration;


//import org.apache.catalina.Context;
//import org.apache.tomcat.util.descriptor.web.ContextResource;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TomcatConfiguration {
//
//  @Bean
//  public ServletWebServerFactory servletContainer() {
//    return new TomcatServletWebServerFactory() {
//      @Override
//      protected void postProcessContext(Context context) {
//        ContextResource resource = new ContextResource();
//        resource.setName("jdbc/ventanillaDS");
//        resource.setType("javax.sql.DataSource");
//        resource.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
//        resource.setProperty("url", "jdbc:oracle:thin:@10.0.100.157:20200:siga02");
//        resource.setProperty("username", "dbuser");
//        resource.setProperty("password", "dbpassword");
//        resource.setProperty("maxTotal", "100");
//        resource.setProperty("maxIdle", "30");
//        resource.setProperty("maxWaitMillis", "10000");
//
//        context.getNamingResources().addResource(resource);
//      }
//    };
//  }
//}