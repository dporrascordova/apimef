package mef.application.controlador;

import mef.application.dto.ResponseData;
import mef.application.dto.ResponseError;
import mef.application.exception.ValidateException;
import mef.application.modelo.Auditoria;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindException;
//import java.net.BindException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static mef.application.dto.ResponseData.ERROR;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(ValidateException.class)
	public ResponseEntity<ResponseData> handler(ValidateException e) {
		return new ResponseEntity<>(ERROR(HttpStatus.BAD_REQUEST.value(), e.getErrors()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ResponseData> handler(ResponseStatusException e) {
		return new ResponseEntity<>(ERROR(e.getStatus().value(), e.getReason()), e.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handler(MethodArgumentNotValidException ex) {
		return getError(ex.getBindingResult().getFieldErrors());
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> handler(BindException ex) {
		return getError(ex.getFieldErrors());
	}

	public ResponseEntity<?> getError(List<FieldError> list) {
		List<ResponseError> errors = list.stream().map(x -> new ResponseError(x.getField(), x.getDefaultMessage()))
				.collect(toList());
		Auditoria auditoria = new Auditoria();
		auditoria.error_lista = true;
		auditoria.objeto = errors;
		auditoria.ejecucion_procedimiento = false;
		return new ResponseEntity<>(auditoria, HttpStatus.OK);
	}

	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public
	 * ResponseEntity<?> handler(MethodArgumentNotValidException ex) {
	 * List<ResponseError> errors = ex.getBindingResult().getFieldErrors().stream()
	 * .map(x -> new ResponseError(x.getField(),
	 * x.getDefaultMessage())).collect(toList()); Auditoria auditoria = new
	 * Auditoria(); auditoria.objeto = errors; auditoria.ejecucion_procedimiento =
	 * false; return new ResponseEntity<>(auditoria, HttpStatus.OK); }
	 */
}
