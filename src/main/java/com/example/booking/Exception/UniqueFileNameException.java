package com.example.booking.Exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UniqueFileNameException extends RuntimeException {
    private String message;
    public UniqueFileNameException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}