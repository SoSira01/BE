//package com.example.booking.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class AuthConfig extends WebSecurityConfigurerAdapter {
//    @Value(value = "${com.auth0.domain}")
//    private String domain;
//
//    @Value(value = "${com.auth0.email}")
//    private String email;
//
//    @Value(value = "${com.auth0.password}")
//    private String password;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http
//                .authorizeRequests()
//                .antMatchers("/callback", "/login", "/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .and()
//                .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
//    }
//
//}
