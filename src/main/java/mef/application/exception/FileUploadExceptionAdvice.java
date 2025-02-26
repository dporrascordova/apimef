package mef.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mef.application.modelo.RespuestaMessage;

public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<RespuestaMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespuestaMessage("Archivo demasiado grande."));
	}

}
