package mef.application.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logo-mef")
public class ImageLogoServlet extends HttpServlet {

	private static final long serialVersionUID = 4460627478173578748L;
	private static final String RUTA_LOGO = "/WEB-INF/images/logo.jpg";
	private static final String JDBC_URL="jdbc:oracle:thin:@10.0.100.157:20200:siga02";
	private static final String USERNAME="SISVENVI";
	private static final String PASSWORD="SISVENVI21";
	private static final String SQL = "UPDATE T_VEND_CASILLA SET ID_ESTADO = 1, FEC_MODIFICACION = SYSDATE WHERE ID_CASILLA = ?";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	//@SuppressWarnings("rawtypes")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/jpg");
        
     	try {	
			String parameter = request.getParameter("id");
			if(parameter!=null && parameter.length()>0){
				long id = Long.parseLong(parameter);
				if(id>0){
                 Casilla_Estado(id);
				}
			}
		} catch (Exception ex) {			
			throw new RuntimeException("No se pudo actulizar la casilla", ex);
		}  

    	try {
    		String pathLogoMEF = request.getSession().getServletContext().getRealPath(RUTA_LOGO);
    		System.out.println(pathLogoMEF);
    		    		
        	FileInputStream fis=new FileInputStream(pathLogoMEF);
        	int c = 0;
        	while((c=fis.read())!=-1)        
        		out.write(c);
         
        	fis.close();
        	out.flush();
        	out.close();
        } catch (IOException ioe) {
            throw new RuntimeException("No se pudo generar la imagen", ioe);
        }
	}

	private void Casilla_Estado(Long id_casilla) {	
        try(Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD); 
        	PreparedStatement stmt = conn.prepareStatement(SQL);) {     
        	  stmt.setLong(1, id_casilla);
              stmt.executeUpdate();
              System.out.println("Casilla actualizada correctamente");
            } catch (SQLException e) {
              e.printStackTrace();
            }        
	}
}