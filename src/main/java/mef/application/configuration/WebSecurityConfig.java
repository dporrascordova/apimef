package mef.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import mef.application.security.JwtAuthEntryPoint;
import mef.application.security.JwtAuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
		return new JwtAuthTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Value("${swagger.enabled}")
	private boolean swaggerEnabled;

	// "/api/listapersona",
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // Sensitive
		// http.cors().and().csrf().disable();
		// http.cors().and().csrf().disable().authorizeRequests()
		http.cors().and().authorizeRequests()
				.antMatchers("/api/exportmanualexterno", "/api/VE_notificarsolicitud", "/api/listarusuariospersonaslibre",
						"/api/listaoficinastodo", "/api/listatipodedoc", "/api/mefconsultapersona/{nrodocumento}/{tipopersona}",
						"/api/listadep", "/api/verificamiclave", "/api/quieromiclave", "/api/verificamicodigo",
						"/api/VE_atendersolicitud",
						"/api/crearpersonanatural", "/api/login", "/api/listaprovincia/{id_departamento}",
						"/api/listadistrito/{id_provincia}/{id_departamento}", "/api/estado", "/api/crearpersonajuridica",
						"/api/libertad", "captcha-servlet", "logo-mef", "/resources/**")
				.permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		if (swaggerEnabled) {
			http.authorizeRequests()
					.antMatchers("/v3/api-docs/**", "/swagger-ui/**")
					.denyAll();
		}

	}

}