/*
 * JCatalog Project
 */
package mef.application.component;

/*import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;*/
import java.util.ArrayList;
//import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Properties;
//import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ViewResolver;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//import org.thymeleaf.templateresolver.ITemplateResolver;

import mef.application.dto.Resource;

/**
 * Utility class to send email.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class EmailUtil {

	protected final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.username}")
	private String correofrom;

	@Value("${spring.mail.password}")
	private String clave;

	private SpringTemplateEngine thymeleafEngine;
	// private ApplicationContext applicationContext;
	/*
	 * public EmailUtil(SpringTemplateEngine thymeleafEngine) { this.thymeleafEngine
	 * = thymeleafEngine; // this.emailSender = emailSender; }
	 */

	// @Bean

	// @Bean

	// @Bean
	public SpringTemplateEngine templateEngine(String templateName, Context context) {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setEnableSpringELCompiler(true);
		springTemplateEngine.setTemplateResolver(getTemplateResolver());
		springTemplateEngine.process(templateName, context);
		return springTemplateEngine;
	}

	// @Bean
	/*
	 * public ITemplateResolver templateResolver(){ SpringResourceTemplateResolver
	 * springResourceTemplateResolver = new SpringResourceTemplateResolver(); //
	 * ApplicationContext applicationContext2 = new ApplicationContext(context);
	 * //applicationContext.
	 * //springResourceTemplateResolver.setContext(applicationContext); //String
	 * html = springResourceTemplateResolver.process(templateName, context);
	 * springResourceTemplateResolver.setPrefix("templates/");
	 * springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
	 * springResourceTemplateResolver.setSuffix(".html");
	 * springResourceTemplateResolver.setCharacterEncoding("UTF-8"); return
	 * springResourceTemplateResolver; } public ViewResolver viewResolver(String
	 * templateName,Context context){ ThymeleafViewResolver thymeleafViewResolver =
	 * new ThymeleafViewResolver();
	 * thymeleafViewResolver.setTemplateEngine(templateEngine(templateName,context))
	 * ; thymeleafViewResolver.setCharacterEncoding("UTF-8"); return
	 * thymeleafViewResolver; }
	 * 
	 * public ClassLoaderTemplateResolver getTemplateResolver() {
	 * ClassLoaderTemplateResolver templateResolver = new
	 * ClassLoaderTemplateResolver(); templateResolver.setPrefix("templates/");
	 * templateResolver.setCacheable(false); templateResolver.setSuffix(".html");
	 * templateResolver.setTemplateMode("HTML5");
	 * templateResolver.setCharacterEncoding("UTF-8");
	 * 
	 * return templateResolver; }
	 * 
	 * public String getTemplatexx(String templateName, Map<String, Object> params)
	 * { Context context = new Context(); context.setVariables(params); ViewResolver
	 * x = viewResolver(templateName,context); System.out.println("Que fue " +
	 * x.toString());
	 * 
	 * SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	 * templateEngine.setTemplateResolver(getTemplateResolver());
	 * 
	 * String html = templateEngine.process(templateName, context);
	 * 
	 * for (Map.Entry<String, Object> entry : params.entrySet()) { html =
	 * html.replace("${" + entry.getKey() + "}", entry.getValue().toString());
	 * System.out.println(entry.getKey() + "/" + entry.getValue()); } return html; }
	 */

	public ClassLoaderTemplateResolver getTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");

		return templateResolver;
	}

	public String getTemplate(String templateName, Map<String, Object> params) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(getTemplateResolver());
		Context context = new Context();
		context.setVariables(params);
		String html = templateEngine.process(templateName, context);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			html = html.replace("${" + entry.getKey() + "}", entry.getValue().toString());
			//System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		return html;
	}

	@Async("asyncExecutor")
	public void sendHTML(String to, String subject, String CC, String templateName, Map<String, Object> params,
			Map<String, Resource> resources) {
		try {
			System.out.println("enviar correo SIN BD  : " + to);
			String text = getTemplate(templateName, params);
			List<String> recipients = new ArrayList<String>();
			recipients.add(to);
			String sub = subject;
			String msg = text;

			
			/*  host = "smtp.gmail.com"; correofrom = "ivansperezt@gmail.com"; clave =
			  "password@ivans2020"; Integer port = 465;*/
			 
			host = "ofine.mef.gob.pe";
			correofrom = "ventanilla@mef.gob.pe";
			clave = "ir%Wd,m3f";
			Integer port = 25;

			SendEmailUtil sendEmailUtil = new SendEmailUtil(host, port, correofrom, correofrom, clave);

			sendEmailUtil.setDebug(true);

			int validar = 0;
			sendEmailUtil.setUseSsl(false);
			sendEmailUtil.setUseSsl(false);

			 //int validar = 2;

			if (validar > 1)
				sendEmailUtil.setUseSsl(true);
			else if (validar > 0)
				sendEmailUtil.setUseTls(true);

			for (Iterator<String> it = recipients.iterator(); it.hasNext();) {
				String email = (String) it.next();
				sendEmailUtil.sendEmail(email, null, CC, sub, msg, null, resources);
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	
	@Async("asyncExecutor")
	public void sendEmail(String to, String subject, String CC, String msg ,Map<String, Resource> resources) {
		try {

		
			List<String> recipients = new ArrayList<String>();
			recipients.add(to);
			String sub = subject;
			
			
			/*  host = "smtp.gmail.com"; correofrom = "ivansperezt@gmail.com"; clave =
			  "password@ivans2020"; Integer port = 465;*/
			 
			host = "ofine.mef.gob.pe";
			correofrom = "ventanilla@mef.gob.pe";
			clave = "ir%Wd,m3f";
			Integer port = 25;

			SendEmailUtil sendEmailUtil = new SendEmailUtil(host, port, correofrom, correofrom, clave);

			sendEmailUtil.setDebug(true);

			int validar = 0;
			sendEmailUtil.setUseSsl(false);
			sendEmailUtil.setUseSsl(false);

			 //int validar = 2;

			if (validar > 1)
				sendEmailUtil.setUseSsl(true);
			else if (validar > 0)
				sendEmailUtil.setUseTls(true);

			for (Iterator<String> it = recipients.iterator(); it.hasNext();) {
				String email = (String) it.next();
				sendEmailUtil.sendEmail(email, null, CC, sub, msg, null, resources);
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	@Async("asyncExecutor")
	public void sendHTMLFiles2(String to, String subject, String CC, String templateName, Map<String, Object> params,
			Map<String, Resource> resources, Map<String, String> files) {
		try {
			String text = getTemplate(templateName, params);
			List<String> recipients = new ArrayList<String>();
			recipients.add(to);
			String sub = subject;
			String msg = text;

			host = "ofine.mef.gob.pe";
			correofrom = "ventanillaelectronic@mef.gob.pe";
			clave = "ventanilla2020";
			Integer port = 25;
			SendEmailUtil sendEmailUtil = new SendEmailUtil(host, port, correofrom, correofrom, clave);

			// SendEmailUtil sendEmailUtil = new SendEmailUtil("smtp.gmail.com",
			// Integer.valueOf(25), "vimerobox@gmail.com", "vimerobox@gmail.com",
			// "rony123456");

			sendEmailUtil.setDebug(true);

			int validar = 0;

			if (validar > 1)
				sendEmailUtil.setUseSsl(true);
			else if (validar > 0)
				sendEmailUtil.setUseTls(true);

			for (Iterator<String> it = recipients.iterator(); it.hasNext();) {
				String email = (String) it.next();
				// sendEmailUtil.sendEmail(email, CC, null, sub, msg, files);
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			List<String> recipients = new ArrayList<String>();
			recipients.add("caguilar@mef.gob.pe");
			recipients.add("cafach@gmail.com");
			recipients.add("ayauyo148@gmail.com");
			String sub = "Asunto Encuesta:)";
			String msg = "Este mensaje es de prueba XXX...";
			SendEmailUtil sendEmailUtil = new SendEmailUtil("ofine.mef.gob.pe", 25, "ventanillaelectronic@mef.gob.pe",
					"ventanillaelectronic@mef.gob.pe", "ventanilla2020");

			sendEmailUtil.setDebug(true);

			int validar = 0;

			if (validar > 1)
				sendEmailUtil.setUseSsl(true);
			else if (validar > 0)
				sendEmailUtil.setUseTls(true);

			for (Iterator<String> it = recipients.iterator(); it.hasNext();) {
				String email = (String) it.next();
				// sendEmailUtil.sendEmail(email, null, null, sub, msg, null);
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
