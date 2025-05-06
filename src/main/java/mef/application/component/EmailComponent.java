package mef.application.component;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import mef.application.dto.Resource;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.DatatypeConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mef.application.configuration.AsyncConfiguration;

public class EmailComponent {
	// @Autowired
	// private AsyncService service;
	@Value("${spring.mail.username}")
	private String correofrom;

	@Value("${spring.mail.password}")
	private String clave;
	
	protected final Logger logger = LoggerFactory.getLogger(EmailComponent.class);

	private SpringTemplateEngine thymeleafEngine;

	private JavaMailSender emailSender;

	public EmailComponent(SpringTemplateEngine thymeleafEngine, JavaMailSender emailSender) {
		this.thymeleafEngine = thymeleafEngine;
		this.emailSender = emailSender;
	}

	public void sendPlainText(String to, String subject, String text) {
		logger.info("Init send plain text email");
		// validate(to);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
		logger.info("Send email '{}' to: {}", subject, to);
	}

	public String getTemplate(String templateName, Map<String, Object> params) {
		Context context = new Context();
		context.setVariables(params);
		
		String html = thymeleafEngine.process(templateName, context);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			html = html.replace("${" + entry.getKey() + "}", entry.getValue().toString());
			//System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		return html;
		
		//return thymeleafEngine.process(templateName, context);
	}

	public void sendHTMLAsync(String to, String subject, String CC, String templateName, Map<String, Object> params) {
		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		emailExecutor.execute(new Runnable() {
			@Override
			public void run() {
				sendHTML(to, subject, CC, templateName, params);
			}
		});
		emailExecutor.shutdown();
	}

	/*
	 * public void sendHTMLAsync(String to, String subject, String templateName,
	 * Map<String, Object> params, Map<String, String> files) { Boolean send = true;
	 * ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
	 * emailExecutor.execute(new Runnable() {
	 * 
	 * @Override public void run() { sendHTMLFiles(to, subject, templateName,
	 * params, files); } }); emailExecutor.shutdown(); }
	 */

	public void sendHTML(String to, String subject, String CC, String templateName, Map<String, Object> params) {
		sendHTML(to, subject, CC, templateName, params, null);
	}

	@Bean
	public Session mailSender() throws IOException {
		final String username = correofrom;
		final String password = clave;
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "ofine.mef.gob.pe");
		prop.put("mail.smtp.port", "25");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		return session;
	}

	@Async("asyncExecutor")
	public void sendHTML(String to, String subject, String CC, String templateName, Map<String, Object> params,
			Map<String, Resource> resources) {
		try {
			/*
			 * System.out.println(CC); System.out.println(to); System.out.println(subject);
			 */
			logger.info("Init send HTML email");
			String text = getTemplate(templateName, params);
			// emailSender = mailSender();
			// MimeMessage mimeMessage = new MimeMessage(mailSender());
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(correofrom);
			helper.setTo(to);
			
			System.out.println("enviar correo bd : " + to);
			
			if (!CC.equals("-"))
				helper.setCc(CC);
			System.out.println("enviar correo  2 : " + CC);
			// helper.addAttachment(attachmentFilename, file);
			helper.setSubject(subject);
			//helper.addCc("richardcarbajalg@gmail.com");
			helper.setText(text, true);

			if (resources != null) {
				for (Map.Entry<String, Resource> entry : resources.entrySet()) {
					Resource resource = entry.getValue();
					helper.addInline(entry.getKey(), new ByteArrayResource(resource.getImageBytes()),
							resource.getContentType());
				}
			}
			//System.out.println(helper.getMimeMessage().getContent().toString());
			emailSender.send(mimeMessage);
//			logger.info("Send email '{}' to: {}", subject, to);
		} catch (Exception e) {
			System.out.println("mierror: " + e.toString());
			logger.error("Error : {}, to: {}, ", e.getMessage(), to);
		}
	}

	@Async("asyncExecutor")
	public void sendHTMLFiles2(String to, String subject, String templateName, Map<String, Object> params,
			Map<String, Resource> resources, Map<String, String> files) {
		try {
			logger.info("Init send HTML email");
			String text = getTemplate(templateName, params);
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(correofrom);
			helper.setTo(to);
			// helper.addAttachment(attachmentFilename, file);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.addCc("darwinleonardsl@gmail.com");
			if (resources != null) {
				for (Map.Entry<String, Resource> entry : resources.entrySet()) {
					Resource resource = entry.getValue();
					helper.addInline(entry.getKey(), new ByteArrayResource(resource.getImageBytes()),
							resource.getContentType());
				}
			}
			System.out.println(helper.getMimeMessage().getContent().toString());
			/*
			 * if (files.size() > 0) { for (Map.Entry<String, String> entry :
			 * files.entrySet()) { // helper.addAttachment(entry.getKey(),
			 * entry.getValue()); // chment(entry.getKey(),
			 * Files.readAllBytes(Paths.get(entry.getValue()))); //
			 * content.addBodyPart(getMimeBodyPart(entry.getKey(), entry.getValue())); //
			 * OutputStream os = new BufferedOutputStream(new FileOutputStream(new //
			 * File(ruta))); helper.addAttachment(entry.getKey(), new ByteArrayResource(
			 * IOUtils.toByteArray(new FileInputStream(new File(entry.getValue()))))); } }
			 */
			emailSender.send(mimeMessage);
			logger.info("Files _ Send email '{}' to: {}", subject, to);
			/*
			 * String base3 = ""; String text = getTemplate(templateName, params); if
			 * (resources != null) { for (Map.Entry<String, Resource> entry :
			 * resources.entrySet()) { Resource resource = entry.getValue(); byte[] bytes =
			 * resource.getImageBytes(); base3 = new String(Base64.encodeBase64(bytes),
			 * "UTF-8");
			 * 
			 * text = text.replace("logomail", "data:image/png;base64," + base3); } }
			 * MimeMultipart content = new MimeMultipart("mixed"); MimeMessage mimeMessage =
			 * emailSender.createMimeMessage(); // MimeMessageHelper helper = new
			 * MimeMessageHelper(mimeMessage, true); MimeMessageHelper helper = new
			 * MimeMessageHelper(mimeMessage, true); helper.setFrom(correofrom);
			 * helper.setSubject(subject); helper.setTo(to); helper.setText(text, true); if
			 * (resources != null) { for (Map.Entry<String, Resource> entry :
			 * resources.entrySet()) { Resource resource = entry.getValue();
			 * helper.addInline(entry.getKey(), new
			 * ByteArrayResource(resource.getImageBytes()), resource.getContentType()); } }
			 * 
			 * if (files.size() > 0) { for (Map.Entry<String, String> entry :
			 * files.entrySet()) { // helper.addAttachment(entry.getKey(),
			 * entry.getValue()); // chment(entry.getKey(),
			 * Files.readAllBytes(Paths.get(entry.getValue()))); //
			 * content.addBodyPart(getMimeBodyPart(entry.getKey(), entry.getValue())); //
			 * OutputStream os = new BufferedOutputStream(new FileOutputStream(new //
			 * File(ruta))); helper.addAttachment(entry.getKey(), new ByteArrayResource(
			 * IOUtils.toByteArray(new FileInputStream(new File(entry.getValue()))))); } }
			 * 
			 * // FileInputStream fileInputStreamReader = new FileInputStream(file);
			 * 
			 * // fileInputStreamReader.read(bytes);
			 */
			/*
			 * byte[] tile = DatatypeConverter.parseBase64Binary(base3); MimeBodyPart
			 * textPart = new MimeBodyPart(); DataHandler dataHandler = new DataHandler(new
			 * ByteArrayDataSource(tile, "image/jpeg"));
			 * textPart.setDataHandler(dataHandler); textPart.setText(text,
			 * StandardCharsets.UTF_8.name(),"html" ); textPart.setHeader("Content-ID",
			 * "<the-img-1>");
			 */

			// content.addBodyPart(textPart);
			// content.addBodyPart(textPart);

			// mimeMessage.setContent(content);
			// mimeMessage.saveChanges();
			/*
			 * emailSender.send(mimeMessage); logger.info("Send email '{}' to: {}", subject,
			 * to);
			 */
		} catch (Exception e) {
			logger.error("Error : {}, to: {}, ", e.getMessage(), to);
		}
	}

	@Value("${file.fileserver}")
	private String fileServer;

	@Async("asyncExecutor")
	public void sendHTMLFiles3(String to, String subject, String CC, String templateName, Map<String, Object> params,
			Map<String, Resource> resources, Map<String, String> files) {
		try {
			String base3 = "";
			String text = getTemplate(templateName, params);
			if (resources != null) {
				for (Map.Entry<String, Resource> entry : resources.entrySet()) {

					// data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUA
					// AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
					// 9TXL0Y4OHwAAAABJRU5ErkJggg==
					Resource resource = entry.getValue();
					byte[] bytes = resource.getImageBytes();
					base3 = new String(Base64.encodeBase64(bytes), "UTF-8");
					// text.replace("miimg", base3);

					// text = text.replace("logomail", "data:image/png;base64," + base3);
					// System.out.println(base3);
					// System.out.println(text);

					/*
					 * helper.addInline(entry.getKey(), new
					 * ByteArrayResource(resource.getImageBytes()), resource.getContentType());
					 */
				}
			}
			logger.info("Init xxx1112 send HTML email");
			// String text = getTemplate(templateName, params);
			MimeMultipart content = new MimeMultipart("mixed");
			MimeMessage mimeMessage = emailSender.createMimeMessage();

			// MimeMessage mimeMessage = new MimeMessage(mailSender());//
			// emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(correofrom);
			helper.setTo(to);
			if (CC != "-")
				helper.setCc(CC);
			// helper.setCc("richardcarbajalg@gmail.com");
			// helper.addAttachment(attachmentFilename, file);
			helper.setSubject(subject);
			helper.setText(text, true);
			if (resources != null) {
				for (Map.Entry<String, Resource> entry : resources.entrySet()) {
					Resource resource = entry.getValue();
					helper.addInline(entry.getKey(), new ByteArrayResource(resource.getImageBytes()),
							resource.getContentType());

				}
			}
			Path path = Paths.get(fileServer);
			// Path path2 = Paths.get(fileServer);
			// content.addBodyPart(getMimeBodyPart("logox.png", path.toString() + "/" +
			// "logo.jpg"));

			if (files.size() > 0) {
				for (Map.Entry<String, String> entry : files.entrySet()) {
					// helper.addAttachment(entry.getKey(), entry.getValue());
					// chment(entry.getKey(), Files.readAllBytes(Paths.get(entry.getValue())));
					content.addBodyPart(getMimeBodyPart(entry.getKey(), entry.getValue()));
					// OutputStream os = new BufferedOutputStream(new FileOutputStream(new
					// File(ruta)));
					// helper.addAttachment(entry.getKey(), new ByteArrayResource(
					// IOUtils.toByteArray(new FileInputStream(new File(entry.getValue())))));
				}
			}

			byte[] tile = DatatypeConverter.parseBase64Binary(base3);
			MimeBodyPart textoPart = new MimeBodyPart();
			textoPart.setText(text, StandardCharsets.UTF_8.name(), "html");
			textoPart.setContent(text, "text/plain; charset=ISO-8859-1");
			content.addBodyPart(textoPart);
			// mail.setContent(testMail.getTexto(), "text/plain; charset=ISO-8859-1");
			MimeBodyPart imagePart = new MimeBodyPart();
			// textPart.setContent(text, "text/html");
			// textPart.setContent(text, "text/html");
			// DataSource fds = new
			// FileDataSource(tile);//"/home/manisha/javamail-mini-logo.png");
			DataHandler fds = new DataHandler(new ByteArrayDataSource(tile, "image/jpg"));
			imagePart.setDataHandler(fds);
			// textPart.setDataHandler(fds);
			// textPart.setText(text, StandardCharsets.UTF_8.name(), "html");

			imagePart.setHeader("Content-ID", "<2imageResourceName>");
			imagePart.setDisposition(MimeBodyPart.INLINE);
			// String imageFilePath = mapInlineImages.get(contentId);
			try {
				imagePart.attachFile(path.toString() + "/" + "logo.jpg");
			} catch (IOException ex) {
				//ex.printStackTrace();
			}
			content.addBodyPart(imagePart);

			mimeMessage.setContent(content);
			mimeMessage.saveChanges();
			emailSender.send(mimeMessage);
			logger.info("Filesx _ Send email '{}' to: {}", subject, to);
			/*
			 * String base3 = ""; String text = getTemplate(templateName, params); if
			 * (resources != null) { for (Map.Entry<String, Resource> entry :
			 * resources.entrySet()) { Resource resource = entry.getValue(); byte[] bytes =
			 * resource.getImageBytes(); base3 = new String(Base64.encodeBase64(bytes),
			 * "UTF-8");
			 * 
			 * text = text.replace("logomail", "data:image/png;base64," + base3); } }
			 * MimeMultipart content = new MimeMultipart("mixed"); MimeMessage mimeMessage =
			 * emailSender.createMimeMessage(); // MimeMessageHelper helper = new
			 * MimeMessageHelper(mimeMessage, true); MimeMessageHelper helper = new
			 * MimeMessageHelper(mimeMessage, true); helper.setFrom(correofrom);
			 * helper.setSubject(subject); helper.setTo(to); helper.setText(text, true); if
			 * (resources != null) { for (Map.Entry<String, Resource> entry :
			 * resources.entrySet()) { Resource resource = entry.getValue();
			 * helper.addInline(entry.getKey(), new
			 * ByteArrayResource(resource.getImageBytes()), resource.getContentType()); } }
			 * 
			 * if (files.size() > 0) { for (Map.Entry<String, String> entry :
			 * files.entrySet()) { // helper.addAttachment(entry.getKey(),
			 * entry.getValue()); // chment(entry.getKey(),
			 * Files.readAllBytes(Paths.get(entry.getValue()))); //
			 * content.addBodyPart(getMimeBodyPart(entry.getKey(), entry.getValue())); //
			 * OutputStream os = new BufferedOutputStream(new FileOutputStream(new //
			 * File(ruta))); helper.addAttachment(entry.getKey(), new ByteArrayResource(
			 * IOUtils.toByteArray(new FileInputStream(new File(entry.getValue()))))); } }
			 * 
			 * // FileInputStream fileInputStreamReader = new FileInputStream(file);
			 * 
			 * // fileInputStreamReader.read(bytes);
			 */
			/*
			 * byte[] tile = DatatypeConverter.parseBase64Binary(base3); MimeBodyPart
			 * textPart = new MimeBodyPart(); DataHandler dataHandler = new DataHandler(new
			 * ByteArrayDataSource(tile, "image/jpeg"));
			 * textPart.setDataHandler(dataHandler); textPart.setText(text,
			 * StandardCharsets.UTF_8.name(),"html" ); textPart.setHeader("Content-ID",
			 * "<the-img-1>");
			 */

			// content.addBodyPart(textPart);
			// content.addBodyPart(textPart);

			// mimeMessage.setContent(content);
			// mimeMessage.saveChanges();
			/*
			 * emailSender.send(mimeMessage); logger.info("Send email '{}' to: {}", subject,
			 * to);
			 */
		} catch (Exception e) {
			logger.error("Error : {}, to: {}, ", e.getMessage(), to);
		}
	}

	private MimeBodyPart getMimeBodyPart(String fileName, String path) throws MessagingException, IOException {
		logger.info("Add file Name = {} , Path = {}", fileName, path);
		MimeBodyPart attachmentPart = new MimeBodyPart();
		attachmentPart.setFileName(fileName);
		attachmentPart.setDataHandler(getAttachment(path));
		return attachmentPart;
	}

	private DataHandler getAttachment(String path) throws IOException {
		File file = new File(path);
		DataSource source = new ByteArrayDataSource(Files.readAllBytes(file.toPath()),
				MediaType.APPLICATION_PDF.toString());
		return new DataHandler(source);
	}
}
