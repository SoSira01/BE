package com.example.booking.Jwt;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private  String title;
    private  String message;
    private  String token;
    private  String refreshToken;
    private  String role;

}
