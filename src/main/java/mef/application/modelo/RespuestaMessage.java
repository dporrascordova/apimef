package mef.application.modelo;

import java.io.Serializable;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RespuestaMessage implements Serializable {
	
	private String status;
    private String message;
    private Integer code;
    private Object result;
 
	private static final long serialVersionUID = 1L;
    
	public RespuestaMessage() {
		
	}
	
    public RespuestaMessage(Exception e) {
		this(e.getClass().getSimpleName(), e.getMessage());
	}

	public RespuestaMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public RespuestaMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.status = code ==100?"Ok":"Error";
	}
	
	public RespuestaMessage(Integer code,Object retVal, String message) {
		this.code = code;
		this.result = retVal;
		this.message = message;
		this.status = code ==100?"Ok":"Error";	
	}
	
	
	public RespuestaMessage(String status) {
		this.status = status;
	}
	
	public RespuestaMessage(Integer code) {
		this.code = code;
		this.status = code ==100?"Ok":"Error";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
