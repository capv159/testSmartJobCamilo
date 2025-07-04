package com.test.smartjob.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 
 * clase que restringe los endpoint y utiliza el componente de filtrado del interceptor
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) 
            .headers(headers -> headers
                    .frameOptions(frame -> frame.sameOrigin()))// permite los iframe de h2 en otros contextos debo evaluarla
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()// rutas autorizadas
                .requestMatchers(HttpMethod.POST,"/api/login").permitAll() 
                .requestMatchers("/h2-console/**").permitAll() // autorizo h2              
                .anyRequest().authenticated() // todo lo demas reuiere autorizacion
            )
            .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}
