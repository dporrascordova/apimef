package mef.application.infrastructure;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import mef.application.security.JwtProvider;

public class UserIdentityHelper {

	@Value("${grokonez.app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	private static JwtProvider tokenProvider;

	private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	/**
	 * Obtiene el nombre de usuario logueado
	 * 
	 * @return
	 */
	public static String getName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return null;
		return auth.getName();
	}

	public static String getAuthorization() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getHeader("Authorization");
	}

	private static String getJwt(HttpServletRequest request) {

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}

		return null;
	}

	public static int getUserId() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String jwt = getJwt(request).replace("Bearer ", "");
		byte[] keyBytes = Decoders.BASE64.decode("n2r5u8xADGKaPdSgVkYp3s6v9yBEHMbQeThWmZq4t7wzCFJNcRfuW3Op09q");
		Keys.hmacShaKeyFor(keyBytes);

		Claims claimsx = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).parseClaimsJws(jwt).getBody();
		// System.out.println("Aqui : " + claimsx.get("Oficina").toString());
		return Integer.valueOf(claimsx.get("UsuarioId").toString().replace(".0", "") + "");
	}

	public static int getTipoUsuario() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String jwt = getJwt(request).replace("Bearer ", "");
		byte[] keyBytes = Decoders.BASE64.decode("n2r5u8xADGKaPdSgVkYp3s6v9yBEHMbQeThWmZq4t7wzCFJNcRfuW3Op09q");
		Keys.hmacShaKeyFor(keyBytes);

		Claims claimsx = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).parseClaimsJws(jwt).getBody();
		// System.out.println("Aqui : " + claimsx.get("Oficina").toString());
		return Integer.valueOf(claimsx.get("TipoUsuario").toString().replace(".0", "") + "");
	}

	
	public static String get_CodigoUsuario() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String jwt = getJwt(request).replace("Bearer ", "");
		byte[] keyBytes = Decoders.BASE64.decode("n2r5u8xADGKaPdSgVkYp3s6v9yBEHMbQeThWmZq4t7wzCFJNcRfuW3Op09q");
		Keys.hmacShaKeyFor(keyBytes);

		Claims claimsx = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).parseClaimsJws(jwt).getBody(); 
		return String.valueOf(claimsx.get("UsuarioCod").toString().replace(".0", "") + "");
	}
	
	public static String getClientIpAddress() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		for (String header : IP_HEADER_CANDIDATES) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}
}