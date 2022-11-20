package com.example.booking.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@NoArgsConstructor
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class RuntimeExceptionHandler extends RuntimeException {
        private String message;
        public RuntimeExceptionHandler(String msg)
        {
            super(msg);
            this.message = msg;
        }
}