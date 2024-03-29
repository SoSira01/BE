package com.example.booking.Exception;

import com.example.booking.services.BookingService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

     private BookingService service;
        public ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException ex,
                HttpHeaders headers,
                HttpStatus status,
                WebRequest request
        ) {
            List<BookFieldError> fieldErrors = new ArrayList<>();
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                String field = fieldError.getField();
                String error = fieldError.getDefaultMessage();
                fieldErrors.add(new BookFieldError(field, error));
                }
            BookExceptionModel eventExceptionModel = new BookExceptionModel(HttpStatus.BAD_REQUEST, " failed ", fieldErrors);
            return super.handleExceptionInternal(ex, eventExceptionModel, headers, status, request);
        }
    }