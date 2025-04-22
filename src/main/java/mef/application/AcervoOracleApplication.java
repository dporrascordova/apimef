package mef.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = { "pe.gob.mef.std.bs.web.ws", "mef.application" })
@ServletComponentScan
@EnableScheduling

public class AcervoOracleApplication extends SpringBootServletInitializer {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						// .allowedOrigins("*")
						.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
				// registry.addMapping("/**").allowedMethods("GET", "POST");
				// ; //.allowedOrigins("http://localhost:8080");
			}
		};
	}

	/*
	 * @Bean
	 * public void onStartup(ServletContext sc) throws ServletException {
	 * // ...
	 * System.out.println("Entro xd");
	 * sc.getSessionCookieConfig().setHttpOnly(true);
	 * sc.getSessionCookieConfig().setSecure(true);
	 * }
	 */

	/*
	 * public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**");
	 * }
	 */
	/*
	 * @Bean
	 * public WebMvcConfigurer corsConfigurer() {
	 * return new WebMvcConfigurer() {
	 * 
	 * };
	 * }
	 */

	// Metodo principal
	public static void main(String[] args) {
		System.out.print("V1.0.5");
		SpringApplication.run(AcervoOracleApplication.class, args);
		// onStartup();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AcervoOracleApplication.class);
	}

	/*
	 * @Resource
	 * FilesStorageService storageService;
	 * 
	 * public static void main(String[] args) {
	 * SpringApplication.run(AcervoOracleApplication.class, args);
	 * }
	 * 
	 * @Override
	 * public void run(String... args) throws Exception {
	 * //storageService.deleteAll();
	 * //storageService.init();
	 * }
	 */

}
