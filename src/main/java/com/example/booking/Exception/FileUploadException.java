package com.example.booking.Exception;

import com.example.booking.Exception.BookExceptionModel;
import com.example.booking.Exception.BookFieldError;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class FileUploadException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        BookFieldError fieldError;
        BookExceptionModel eventExceptionModel;

        fieldError = new BookFieldError("file", "The file size cannot be larger than 10 MB.", HttpStatus.BAD_REQUEST);
        eventExceptionModel = new BookExceptionModel(fieldError.getStatus(), "Attributes validation failed !!!", fieldError);
        return new ResponseEntity<>(eventExceptionModel, fieldError.getStatus());
    }
}
