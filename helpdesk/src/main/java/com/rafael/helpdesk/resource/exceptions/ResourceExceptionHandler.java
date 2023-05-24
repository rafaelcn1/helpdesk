package com.rafael.helpdesk.resource.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	/* Classe para personalizar a execption! */
	
	@ExceptionHandler(ObjectNotFoundException.class) //Anotação para dizer que esse metodo é um manipulador de exerção da classe ObjectNotFoundException.class
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {
		
		/*Motando o corpo da resposta*/
		
		int valorHttpStatus = HttpStatus.NOT_FOUND.value();
		long timestamp = System.currentTimeMillis();
		String error = "Objeto Not Found";
		String message = ex.getMessage();
		String path = request.getRequestURI();
		
		StandardError standardError = new StandardError(timestamp, valorHttpStatus, error, message, path);
		
		// Vai retornar o status do ResponseEntity, adicionando no corpo o standardError, quando não for encontrado
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);	
	}

}
