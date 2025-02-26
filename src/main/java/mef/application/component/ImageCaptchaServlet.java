package mef.application.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

@WebServlet("/captcha-servlet")
public class ImageCaptchaServlet extends HttpServlet {
	/** 
	 * 
	 */
    @Value("${captcha.time}")
	private int captcha_time;

	private static final long serialVersionUID = -720183226887688840L;
	/*
	 * private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	 */

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@SuppressWarnings("rawtypes")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType(CONTENT_TYPE);
		/*
		 * PrintWriter out = response.getWriter(); out.println("<html>");
		 * out.println("<head><title>ImageCaptchaDemo</title></head>");
		 * out.println("<body>");
		 * out.println("<p>The servlet has received a GET. This is the reply.</p>");
		 * out.println("</body></html>"); out.close();
		 */
		System.out.println("CODIGO 1");
		response.setContentType("image/jpg");
		try {
//            Color backgroundColor = Color.orange;
//            Color borderColor = Color.red;
			Color textColor = Color.white;
			System.out.println("CODIGO 2");
			Color circleColor = new Color(160, 160, 160);
			Font textFont = new Font("Arial", Font.PLAIN, 24);
			int charsToPrint = 6;
			int width = 150;
			try {
				width = request.getParameter("width") != null ? Integer.parseInt(request.getParameter("width")) : 150;
			} catch (NumberFormatException ioe) {
			}
			int height = 40;
			try {
				height = request.getParameter("height") != null ? Integer.parseInt(request.getParameter("height")) : 40;
			} catch (NumberFormatException ioe) {
			}

			System.out.println("CODIGO 3");
			int circlesToDraw = 4;
			float horizMargin = 20.0f;
			float imageQuality = 0.95f; // max is 1.0 (this is for jpeg)
			double rotationRange = 0.7; // this is radians
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

			// Draw an oval
			Color backgroundColor = Color.gray;
//            g.setColor(new Color(204, 133, 48));
			g.setColor(backgroundColor);
			g.fillRect(0, 0, width, height);

			// lets make some noisey circles
			g.setColor(circleColor);
			for (int i = 0; i < circlesToDraw; i++) {
				Random random = new Random(); // Sensitive use of Random
				//byte bytes[] = new byte[10];
				//random.nextBytes(bytes);
				  
				int circleRadius = (int) (random.nextDouble() * height / 2.0);
				int circleX = (int) (random.nextDouble() * width - circleRadius);
				int circleY = (int) (random.nextDouble() * height - circleRadius);
				/*int circleRadius = (int) (Math.random() * height / 2.0);
				int circleX = (int) (Math.random() * width - circleRadius);
				int circleY = (int) (Math.random() * height - circleRadius);*/
				g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
			}

			g.setColor(textColor);
			g.setFont(textFont);

			FontMetrics fontMetrics = g.getFontMetrics();
			int maxAdvance = fontMetrics.getMaxAdvance();
			int fontHeight = fontMetrics.getHeight();

			// i removed 1 and l and i because there are confusing to users...
			// Z, z, and N also get confusing when rotated
			// 0, O, and o are also confusing...
			// lowercase G looks a lot like a 9 so i killed it
			// this should ideally be done for every language...
			// i like controlling the characters though because it helps prevent confusion
			String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYabcdefhjkmnpqrstuvwxy23456789";
			char[] chars = elegibleChars.toCharArray();

			float spaceForLetters = -horizMargin * 2 + width;
			float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);

			// AffineTransform transform = g.getTransform();

			StringBuffer finalString = new StringBuffer();

			for (int i = 0; i < charsToPrint; i++) {
				Random random = new Random(); 
				//double randomValue = Math.random();
				double randomValue = random.nextDouble(); // Math.random();
				int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
				char characterToShow = chars[randomIndex];
				finalString.append(characterToShow);
 
				int charWidth = fontMetrics.charWidth(characterToShow);
				int charDim = Math.max(maxAdvance, fontHeight);
				int halfCharDim = (int) (charDim / 2);

				BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
				Graphics2D charGraphics = charImage.createGraphics();
				charGraphics.translate(halfCharDim, halfCharDim);
				//double angle = (Math.random() - 0.5) * rotationRange;
				double angle = (random.nextDouble() - 0.5) * rotationRange;
				charGraphics.transform(AffineTransform.getRotateInstance(angle));
				charGraphics.translate(-halfCharDim, -halfCharDim);
				charGraphics.setColor(textColor);
				charGraphics.setFont(textFont);

				int charX = (int) (0.5 * charDim - 0.5 * charWidth);
				charGraphics.drawString("" + characterToShow, charX,
						(int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));

				float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
				int y = (int) ((height - charDim) / 2);
				g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);

				charGraphics.dispose();
			}

			// Write the image as a jpg
			Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
			if (iter.hasNext()) {
				ImageWriter writer = (ImageWriter) iter.next();
				ImageWriteParam iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(imageQuality);
				try {
					writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
					IIOImage imageIO = new IIOImage(bufferedImage, null, null);
					writer.write(null, imageIO, iwp);
				} catch (UnknownHostException  uhex) {
					// ...
				}
			} else {
				// throw new RuntimeException("no encoder found for jsp");
			}
			request.getSession().setAttribute("info__captcha", finalString.toString());
			request.getSession().setMaxInactiveInterval(captcha_time); // in seconds

			System.out.println("CODIGO 6");
			g.dispose();
		} catch (IOException ioe) {
			// throw new RuntimeException("Unable to build image", ioe);
		}

	}
}
