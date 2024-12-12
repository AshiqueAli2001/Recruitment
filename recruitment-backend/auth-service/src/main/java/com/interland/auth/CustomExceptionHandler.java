package com.interland.auth;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ast.Instanceof;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.interland.auth.dto.ServiceResponse;
import com.interland.auth.exception.RecordCreateException;
import com.interland.auth.exception.RecordNotFoundException;
import com.interland.auth.exception.RecordUpdateException;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private final MessageSource messageSource;

	@Autowired
	public CustomExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		
		if(ex instanceof BadCredentialsException) {
			
		}
		
		
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.OK);
	}

	@ExceptionHandler(RecordCreateException.class)
	public final ResponseEntity<Object> handleRecordCreationException(RecordCreateException ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.OK);
	}

	@ExceptionHandler(RecordUpdateException.class)
	public final ResponseEntity<Object> handleRecordUpdateException(RecordUpdateException ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.OK);
	}
	
//	@ExceptionHandler1()

//	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult result = ex.getBindingResult();
		List<ObjectError> allErrors = result.getAllErrors();
		List<JSONObject> errorList = new ArrayList<>();
		for (ObjectError objectError : allErrors) {
			JSONObject obj = new JSONObject();
			FieldError fieldError = (FieldError) objectError;
			obj.put(fieldError.getField(), messageSource.getMessage(objectError, request.getLocale()));
			errorList.add(obj);
		}
	
		ServiceResponse error = new ServiceResponse("VALERRCOD", "Validation Failed", errorList);
		return new ResponseEntity<>(error, HttpStatus.OK);
	}
}
