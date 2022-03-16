package com.legallync.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/register",
            "/hello",
            "/verifyRegistration*",
            "/resendVerificationToken*",
            "/users",
            "/login",
            "/client",
            "**/swagger-resources/**",
            "/swagger-ui.html",
            "/v1/api-docs",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable().sessionManagement()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(WHITE_LIST_URL).permitAll();

        return httpSecurity.build();
    }
}
