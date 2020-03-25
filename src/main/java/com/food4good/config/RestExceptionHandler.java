package com.food4good.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ExceptionHandlerExceptionResolver {

	
	@ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
	
	@ExceptionHandler(value = { WebClientResponseException.class })
	public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
	    log.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
	    return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
	}
}