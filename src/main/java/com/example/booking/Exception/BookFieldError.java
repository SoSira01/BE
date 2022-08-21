package com.example.booking.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BookFieldError {
    private String field;
    private String errorMessage;
    private HttpStatus status;

    public BookFieldError(String field, String errorMessage, HttpStatus status) {
        this.field = field;
        this.errorMessage = errorMessage;
        this.status = status;
    }
    public BookFieldError(String field, String errorMessage){
        this.field = field;
        this.errorMessage = errorMessage;
    }

}
