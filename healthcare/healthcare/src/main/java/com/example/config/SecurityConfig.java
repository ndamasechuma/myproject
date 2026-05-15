package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Explicitly disable CSRF (if not needed)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") // Protect admin endpoints
                .requestMatchers("/user/**").hasRole("USER")   // Protect user endpoints
                .anyRequest().permitAll()                    // Allow all other requests
            )
            .formLogin(form -> form
                .loginPage("/login")                         // Custom login page
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Example for UserDetailsService (Replace with your actual implementation)
    @Bean
    public UserDetailsService userDetailsService() {
        // Create in-memory user details for demonstration purposes
        return username -> {
            if (username.equals("admin")) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin123"))
                        .roles("ADMIN")
                        .build();
            } else if (username.equals("user")) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user123"))
                        .roles("USER")
                        .build();
            }
            throw new IllegalArgumentException("User not found");
        };
    }
}

