package mef.application.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

//import mef.application.consumingws.wsdl.RespuestaSeguridadU;
import mef.application.modelo.Usuario;
import mef.application.modelo.UsuarioPersona;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${grokonez.app.jwtSecret}")
	private String jwtSecret;

	@Value("${grokonez.app.jwtExpiration}")
	private int jwtExpiration;

	public String generateJwtToken(UsuarioPersona  respuesta) {
		Collection<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		return Jwts.builder().setId(UUID.randomUUID().toString())
				.setSubject(String.valueOf(respuesta.getId_usuario()))
				.claim("UsuarioId", respuesta.getId_usuario())
				.claim("UsuarioCod", respuesta.getCod_usuario())
				.claim("OficinaId", respuesta.getId_oficina())
				.claim("Oficina", respuesta.getDesc_oficina())
				.claim("Nombres", respuesta.getNombre_usuario())
				.claim("Apellidos", respuesta.getNombre_usuario())
				.claim("TipoUsuario", respuesta.getId_tipo_usuario())
				.claim("PersonaId", respuesta.getId_persona())
				.claim("TipoPersonaId", respuesta.getId_tipo_persona())
			
				
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
				.signWith(getSigningKey()).compact();
	}

	public String generateJwtToken(String usuario) {
		Collection<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(usuario)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
				.signWith(getSigningKey()).compact();

	}

	private Key getSigningKey() { 
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validateJwtToken(String authToken) { 
		if (!authToken.equals(null) && !authToken.equals("null") && !authToken.equals("")) { 
			try {
				Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken);
				return true;
			} catch (InvalidKeyException e) {
				logger.error("Invalid JWT signature -> Message: {} ", e);
			} catch (MalformedJwtException e) {
				logger.error("Invalid JWT token -> Message: {}", e);
			} catch (ExpiredJwtException e) {
				logger.error("Expired JWT token -> Message: {}", e);
			} catch (UnsupportedJwtException e) {
				logger.error("Unsupported JWT token -> Message: {}", e);
			} catch (IllegalArgumentException e) {
				logger.error("JWT claims string is empty -> Message: {}", e);
			}
		}
		return false;
	}

	public Claims getUserNameFromJwtToken(String token) {

		return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();// .getSubject();

	}
}