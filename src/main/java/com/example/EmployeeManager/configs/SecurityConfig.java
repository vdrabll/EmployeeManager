package com.example.EmployeeManager.configs;

import com.example.EmployeeManager.enums.AuthRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails employee = User.builder()
                .username("s48358")
                .password(passwordEncoder.encode("s48358"))
                .roles(AuthRole.EMPLOYEE.toString())
                .build();
        UserDetails chief = User.builder()
                .username("s48778")
                .password(passwordEncoder.encode("s48778"))
                .roles(AuthRole.CHIEF.toString())
                .build();

        return new InMemoryUserDetailsManager(employee, chief);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http.csrf(AbstractHttpConfigurer::disable)
//                        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/start").permitAll()
//                        .requestMatchers("/api/v1/**").authenticated())
//                        .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                        .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
