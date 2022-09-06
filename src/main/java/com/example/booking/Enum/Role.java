package com.example.booking.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    student, admin, lecturer ;

    public String getAuthority() {
        return name();
    }
}