package com.bbcron.team.exceptions;

import com.bnauk.bbcron.exceptions.BBCronError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller Advice
 */
@RestControllerAdvice
public class BBCronTeamExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {TeamNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ResponseEntity<Object> notFound(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(ex,
        BBCronError.builder().name(ex.getClass().getName()).message(ex.getMessage())
            .statusName(HttpStatus.NOT_FOUND.name()).status(HttpStatus.NOT_FOUND.value()).build(),
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {TeamAlreadyExistException.class, UserAlreadyExistException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  protected ResponseEntity<Object> alreadyExist(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(ex,
        BBCronError.builder().name(ex.getClass().getName()).message(ex.getMessage())
            .statusName(HttpStatus.CONFLICT.name()).status(HttpStatus.CONFLICT.value()).build(),
        new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  
}
