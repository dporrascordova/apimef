/**
 * 
 */
package mef.application.component;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;

/**
 * @author cafach
 *
 */
@WebFilter(filterName="/CaptchaFilter",urlPatterns="/*" )
public class CaptchaFilter implements Filter {

	/**
	 * 
	 */
	public CaptchaFilter() {
		System.out.println("Entro xxd");
		// TODO Auto-generated constructor stub
	}

	@Bean
    public void onStartup(ServletContext sc) throws ServletException {
        // ...
		System.out.println("Entro xd");
        sc.getSessionCookieConfig().setHttpOnly(true);        
        sc.getSessionCookieConfig().setSecure(true);        
    }
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
		      FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = ((HttpServletRequest)request);
		if(httpServletRequest.getSession()!=null){
			String captcha = (String) httpServletRequest.getSession().getAttribute("info__captcha");
			//System.out.println("CODIGO "+captcha+"      "+httpServletRequest.getRequestURI());			
		}		 
		chain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
