package com.kiatkoding.ecommerce.configuration;

import com.kiatkoding.ecommerce.component.AuthFilter;
import com.kiatkoding.ecommerce.component.ExceptionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ALLOWED = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/products",
            "/api/v1/categories",
            "/error",
            "/api/v1/orders/webhook"
    };

    private final AuthFilter authFilter;
    private final ExceptionUtils exceptionUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(ALLOWED)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(managementConfigurer ->
                        managementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer
                            .accessDeniedHandler(new AccessDeniedHandler() {
                                @Override
                                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                    System.out.println("error AccessDeniedHandler ------> " + accessDeniedException.getLocalizedMessage());
                                    exceptionUtils.sendExceptionEntryPoint(request, response, accessDeniedException);
                                }
                            })
                            .authenticationEntryPoint(new AuthenticationEntryPoint() {
                                @Override
                                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                    System.out.println("error AuthenticationEntryPoint ------> " + authException.getLocalizedMessage());
                                    exceptionUtils.sendExceptionEntryPoint(request, response, authException);
                                }
                            });
                })
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
