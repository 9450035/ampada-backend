package com.ampadabackend.tms.controller.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Object> translate(SystemException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, Map.of("errors", new BodyException(ex.getMessage(),
                        ex.getStatus().value(), ex.getStatus().getReasonPhrase())), new HttpHeaders(),
                ex.getStatus(), webRequest);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> translate(SQLIntegrityConstraintViolationException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, Map.of("errors",
                        List.of(new BodyException(ex.getMessage(), 400, "Bad request"))),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
//                                                                     WebRequest webRequest) {
//        return handleExceptionInternal(ex, Map.of("errors", List.of(new BodyException(ex.getMessage(),
//                400, "Bad request"))), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
//    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest webRequest) {
        BindingResult result = ex.getBindingResult();
        List<BodyException> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new BodyException(f.getDefaultMessage() + " " + f.getField(), 400, f.getDefaultMessage()))
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, Map.of("errors", fieldErrors), new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, Map.of("errors", List.of(new BodyException(ex.getMessage(),
                400, "Bad request"))), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<BodyException> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new BodyException(f.getDefaultMessage() + " " + f.getField(), 400, f.getDefaultMessage()))
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, Map.of("errors", fieldErrors), new HttpHeaders(), HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<Object> handlePropertyReferenceException(PropertyReferenceException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, Map.of("errors", List.of(new BodyException(ex.getMessage(),
                400, "Bad request"))), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        return handleExceptionInternal(ex, Map.of("errors", List
                .of(new BodyException(ex.getMessage(), 400, "Bad request"))),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


}
