package com.example.booking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "password.hashing.algo.argon2")
@Getter
@Setter
public class PasswordConfig {
    private int type = 1 ;
    private int hashLength = 16;
    private int SaltLength = 8;
    private int iterations = 4;
    private int memory = 65586;
    private int parralellism = 2;
}
