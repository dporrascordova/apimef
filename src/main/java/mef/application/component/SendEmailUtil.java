package mef.application.component;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;

import com.sun.mail.smtp.SMTPTransport;

import mef.application.dto.Resource;

//MZARATE 27062019 MIGRACION IVAN INICIO
public class SendEmailUtil implements Serializable, TransportListener, ConnectionListener {

	/**
	 * //MPINARES 08032019 - INICIO
	 */

	@Value("${file.fileserver}")
	private String fileServer;

	private static final long serialVersionUID = 382707667317107783L;

	private static final Logger log = Logger.getLogger(SendEmailUtil.class.getName());

	private String serverName = null;

	private Integer serverPort = 993;

	// IVILLAFANA 25022019 INICIO
	// private boolean useSsl = true;
	// private boolean useTls = true;
	private boolean useSsl = false;
	private boolean useTls = false;
	// IVILLAFANA 25022019 FIN

	// IVILLAFANA 25022019 INICIO
	private int contador = 1;
	// IVILLAFANA 25022019 FIN

	private String userName = null;

	private String password = null;

	// props.put("mail.smtp.debug", "true");
	private boolean debug = false;

	private String fromEmail = null;

	public SendEmailUtil(String serverName, Integer serverPort, String fromEmail, String userName, String password) {
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.userName = userName;
		this.password = password;
		this.fromEmail = fromEmail;
	}

	public boolean isUseSsl() {
		return useSsl;
	}

	public void setUseSsl(boolean useSsl) {
		this.useSsl = useSsl;
	}

	public boolean isUseTls() {
		return useTls;
	}

	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void sendEmail(String toEmail, String coEmail, String ccEmail, String subject, String body,
			Map<String, String> files, Map<String, Resource> resources) {
		System.out
				.println("SendEmailUtil.sendEmail(" + toEmail + ", " + coEmail + ", " + ccEmail + ", " + subject + ")");
		// SendEmailUtil.sendEmail(ve.usuario001@gmail.com, -, null, Persona registrada)
		if (debug)
			log.info("SEND EMAIL A " + toEmail);
		files = null;
		Session session = null;
		
		Properties props = new Properties();
		if (useSsl) {
			if (debug)
				log.info("SSLEmail Start");
			props.put("mail.smtp.host", serverName); // SMTP Host
			props.put("mail.smtp.socketFactory.port", serverPort); // "465" // SSL Port
			//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", serverPort); // "465" // SMTP Port
			props.put("mail.smtp.debug", Boolean.toString(debug));

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};

//			session = Session.getDefaultInstance(props, auth);
			// MPINARES 08032019 - INICIO
			
			
			session = Session.getInstance(props, auth);
			// MPINARES 08032019 - FIN

		} else if (useTls) {
			if (debug)
				log.info("TLSEmail Start");
			props.put("mail.smtp.host", serverName); // SMTP Host
			props.put("mail.smtp.port", serverPort); // "587" // TLS Port
			props.put("mail.smtp.auth", "true"); // enable authentication
			props.put("mail.smtp.ehlo", false);

			props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
			props.put("mail.smtp.debug", Boolean.toString(debug));
			// create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			session = Session.getInstance(props, auth);
		} else {
			if (debug)
				log.info("Normal Email Start");
			props.put("mail.smtp.host", serverName);
			props.put("mail.smtp.port", serverPort);
			props.put("mail.smtp.debug", Boolean.toString(debug));

			session = Session.getInstance(props, null);
		}

		if (debug)
			log.info("Fin de optener Session");

		props.put("mail.debug", Boolean.toString(debug));

		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			
			msg.addHeader("Cache-Control", "no-cache; max-age=0");
			
			
			// msg.addHeader("Content-Transfer-Encoding", "8bit");

//			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
			msg.setFrom(new InternetAddress(fromEmail, "VENTANILLA VIRTUAL"));
			msg.setReplyTo(InternetAddress.parse(fromEmail, false));
			msg.setSubject(subject, "UTF-8");
			// message.setSubject( subject, "utf-8" ); // <----
			msg.setSentDate(new Date());

			if (debug)
				log.info("SET CORREO TO " + toEmail);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, true));
			if (coEmail != null) {
				String[] correos = coEmail.split(",");
				for (String entry : correos) {
					if (!entry.equals("-")) {
						try {
							if (debug)
								log.info("SET CORREO BCC " + entry);
							msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(entry, true));
						} catch (AddressException ae) {
							//if (debug)
								//ae.printStackTrace();
						}
					} 
				}
				
				
			}

			// msg.addRecipients(Message.RecipientType.CC,
			// InternetAddress.parse("darwinleonardsl@gmail.com", true));
			if (ccEmail != null) {
				if (!ccEmail.equals("-")) {
					try {
						if (debug)
							log.info("SET CORREO CC " + ccEmail);
						msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, true));
					} catch (AddressException ae) {
						//if (debug)
							//ae.printStackTrace();
					}
				}
			}

			log.info("hola 1");
			if (files != null) {
				if (files.size() > 0) {
					// msg.setText(body, "UTF-8", "html");
					/*
					 * MimeBodyPart textoPart = new MimeBodyPart(); textoPart.setText(body,
					 * StandardCharsets.UTF_8.name(), "html"); textoPart.setContent(body,
					 * "text/html"); content.addBodyPart(textoPart);
					 */

					for (Map.Entry<String, String> entry : files.entrySet()) {

						File f = new File(entry.getValue());
						if (f.exists()) {
							// Create the message body part
							MimeBodyPart messageBodyPart = new MimeBodyPart();

							// Fill the message
							messageBodyPart.setText(body, "UTF-8", "html");
							// messageBodyPart.setText(body);

							// Create a multipart message for attachment
							Multipart multipart = new MimeMultipart();

							// Set text message part
							multipart.addBodyPart(messageBodyPart);

							// Second part is attachment
							messageBodyPart = new MimeBodyPart();

							DataSource source = new FileDataSource(f);
							messageBodyPart.setDataHandler(new DataHandler(source));
							messageBodyPart.setFileName(f.getName());
							multipart.addBodyPart(messageBodyPart);

							// Send the complete message parts
							msg.setContent(multipart);

						} /*
							 * else { msg.setText(body, "UTF-8", "html"); }
							 */

						// helper.addAttachment(entry.getKey(), entry.getValue());
						// chment(entry.getKey(), Files.readAllBytes(Paths.get(entry.getValue())));
						// content.addBodyPart(getMimeBodyPart(entry.getKey(), entry.getValue()));
						// OutputStream os = new BufferedOutputStream(new FileOutputStream(new
						// File(ruta)));
						// helper.addAttachment(entry.getKey(), new ByteArrayResource(
						// IOUtils.toByteArray(new FileInputStream(new File(entry.getValue())))));
					}
				} else {
					msg.setText(body, "UTF-8", "html");
				}
			} else {
				msg.setText(body, "UTF-8", "html");
			}

			msg.setHeader("Content-Encoding", "UTF-8");
			MimeMultipart multipart = new MimeMultipart("related");
			//body="fdsfsfds";
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body, "UTF-8", "html");
			//messageBodyPart.setContent(html, "text/html; charset=utf-8");
			//messageBodyPart.setContent(body, "text/html; charset=utf-8");
			multipart.addBodyPart(messageBodyPart);
			String servido = "/UPLOAD/";
			Path path = Paths.get(servido, "logo.jpg");
			File file = path.toFile();
			log.info("logo " + path);
			log.info("size" + file.length());
			//Path path2 = Paths.get(servido, "el_peru_primero.jpg");
			// File file2 = path2.toFile();
			// Path path2 = Paths.get(fileServer, "el_peru_primero.jpg");
			// Path path = Paths.get(fileServer, "logo.jpg");
			if (resources != null) {
				int cuen = 0;
				for (Map.Entry<String, Resource> entry : resources.entrySet()) {

					Resource resource = entry.getValue();

					// first part (the html)

					// String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
					// messageBodyPart.setContent(htmlText, "text/html");
					// add it
					//if (cuen != 0)
						//file = path2.toFile();
					// second part (the image)
					messageBodyPart = new MimeBodyPart();

					DataSource fds = new FileDataSource(file);// (resource.getImageBytes());
					// "/home/manisha/javamail-mini-logo.png");

					messageBodyPart.setDataHandler(new DataHandler(fds));
					messageBodyPart.setHeader("Content-ID", "<" + entry.getKey() + ">");

					// add image to the multipart
					multipart.addBodyPart(messageBodyPart);

					// put everything together
					msg.setContent(multipart);
					cuen++;
					//break;
					// msg.set.addInline(entry.getKey(), new
					// ByteArrayResource(resource.getImageBytes()),
					// resource.getContentType());
				}
			} 

			log.info("hola 2");
			if (debug)
				log.info("Message is ready a " + serverName + " p " + serverPort + " u " + userName + " pss " + password
						+ " ssl " + useSsl + " ttl " + useTls + " file ");
			// log.info("Message is ready a "+serverName+" p "+serverPort+" u "+userName+"
			// pss "+password+" ssl "+useSsl+" ttl "+useTls+" file
			// "+filepath==null?"":filepath);

			log.info("serverName" + serverName);
			log.info("serverPort" + serverPort);
			log.info("userName" + userName);
			log.info("password" + password);
			log.info("useSsl" + useSsl);
			log.info("useTls" + useTls);
			log.info("hola 3");
			//System.setProperty("mail.mime.charset", "utf8");
			SMTPTransport tsmtp = (SMTPTransport) session.getTransport("smtp");

			if (debug) {
				tsmtp.addConnectionListener(this);
				tsmtp.addTransportListener(this);
			}
			// tsmtp.send(msg);
			// SMTPTransport.send(msg);
			Transport.send(msg);

			if (debug)
				log.info("EMail Sent Successfully!!");

		} catch (AuthenticationFailedException afe) {
			System.out.println("ADVERTENCIA: No se pudo enviar el correo! " + afe.getMessage());
			// IVILLAFANA 25022019 INICIO
			contador++;
			if (contador < 10) {
				System.out.println("Enviando de nuevo (" + contador + ")...");
				// sendEmail(toEmail, coEmail, ccEmail, subject, body, filepath);
			} else {
				System.out.println("ERROR: intento número " + contador + ": No se pudo enviar correo.");
				contador = 1;
			}
			// IVILLAFANA 25022019 FIN
		} catch (MessagingException e1) {
			System.out.println("ADVERTENCIA2: No se pudo enviar el correo! " + e1.getMessage());
			//e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			System.out.println("ADVERTENCIA3: No se pudo enviar el correo! " + e1.getMessage());
			//e1.printStackTrace();
		}

	}

	public void closed(ConnectionEvent arg0) {
		if (debug)
			log.info("Cerrando conexi?n " + arg0.toString());
	}

	public void disconnected(ConnectionEvent arg0) {
		if (debug)
			log.info("Desconectandose " + arg0.toString());
	}

	public void opened(ConnectionEvent arg0) {
		if (debug)
			log.info("Abriendo conexi?n " + arg0.toString());
	}

	public void messageDelivered(TransportEvent arg0) {
		if (debug) {
			log.info("Message delivered for: ");
			if (arg0 != null) {
				Address[] a = arg0.getValidSentAddresses();
				if (a != null && a.length > 0) {
					for (int i = 0; i < a.length; i++) {
						log.info(a[i].toString());
					}
				}
			}
		}
	}

	public void messageNotDelivered(TransportEvent arg0) {
		if (debug) {
			log.info("Message not delivered for:");
			if (arg0 != null) {
				Address[] a = arg0.getValidUnsentAddresses();
				if (a != null && a.length > 0) {
					for (int i = 0; i < a.length; i++) {
						log.info(a[i].toString());
					}
				}
			}
		}
	}

	public void messagePartiallyDelivered(TransportEvent arg0) {
		if (debug) {
			log.info("These addresses are invalid:");
			if (arg0 != null) {
				Address[] a = arg0.getInvalidAddresses();
				if (a != null && a.length > 0) {
					for (int i = 0; i < a.length; i++) {
						log.info(a[i].toString());
					}
				}
			}
		}
	}
}
