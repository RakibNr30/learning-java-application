package com.resume.config;

import com.resume.service.ums.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    AuthenticationProvider authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new  DaoAuthenticationProvider(this.getPasswordEncoder());
        authenticationProvider.setUserDetailsService(this.getUserDetailsService());

        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/dashboard/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/dashboard/ums/**", "/dashboard/cms/**", "/dashboard/setting/**").hasRole("ADMIN")
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .build();
    }
}
