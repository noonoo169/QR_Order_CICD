package com.qrcafe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http 
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/product/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/combo/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/combo/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/combo/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/combo/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/category/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/category/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/category/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/category/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/table/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/table/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/table/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/table/**").hasAuthority("ADMIN")


                .and()
                .httpBasic();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
