package com.example.booking.config;

import com.example.booking.Jwt.JwtAuthenticationEntryPoint;
import com.example.booking.Jwt.JwtRequestFilter;
import com.example.booking.services.JwtUserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        @Autowired
        private JwtRequestFilter jwtRequestFilter;
        @Autowired
        private JwtUserService jwtUserService;
        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","PUT","DELETE"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(jwtUserService).passwordEncoder(argon2PasswordEncoder());
        }
        private PasswordEncoder argon2PasswordEncoder() {
            return new Argon2PasswordEncoder(8,16,2,65586,4);
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .cors().and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/refresh").permitAll()
                    .antMatchers("/api/email/*").permitAll()
                    .antMatchers(HttpMethod.PATCH,"/api/booking","/api/booking/*")
                       .hasAnyAuthority("admin","student","lecturer")
                    .antMatchers("/api/category")
                      .hasAnyAuthority("student","admin","lecturer")
                    .antMatchers("/api/category/*")
                       .hasAnyAuthority("admin","lecturer")
                    .antMatchers(HttpMethod.PATCH,"/api/users","/api/users/*","/api/match")
                       .hasAuthority("admin")
                    .anyRequest().authenticated()
                    .and()

                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                      .sessionManagement()
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
//        @Bean
//        public Argon2PasswordEncoder argon2PasswordEncoder(){
//            return new Argon2PasswordEncoder(int16,29,1,16,2);
//        }

    }


