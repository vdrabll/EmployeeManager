package com.example.EmployeeManager.configs;

import com.example.EmployeeManager.enums.AuthRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails employee = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(AuthRole.EMPLOYEE.toString())
                .build();
        UserDetails chief = User.builder()
                .username("employee")
                .password(passwordEncoder.encode("employee"))
                .roles(AuthRole.CHIEF.toString())
                .build();

        return new InMemoryUserDetailsManager(employee, chief);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
