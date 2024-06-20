package com.ggardet.logbookerror;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        final var userDetails = User
                .withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();
        final var userDetailsService = new InMemoryUserDetailsManager(userDetails);
        httpSecurity.userDetailsService(userDetailsService);
        httpSecurity.httpBasic(Customizer.withDefaults());
        /* SPRINGBOOT 2.7.X START */
        httpSecurity.authorizeRequests(authorizeRequests -> authorizeRequests
                .mvcMatchers("/public").permitAll()
                .mvcMatchers("/private").authenticated()
                .anyRequest().denyAll()
        );
        final var httpStatusEntryPoint = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
        httpSecurity.exceptionHandling().authenticationEntryPoint(httpStatusEntryPoint);
        httpSecurity.csrf().disable();
        httpSecurity.cors().disable();
        /* SPRINGBOOT 2.7.X END */
        /* SPRINGBOOT 3.X.X START */
//        httpSecurity.authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                .requestMatchers("/public").permitAll()
//                .requestMatchers("/private").authenticated()
//                .anyRequest().denyAll()
//        );
//        final var httpStatusEntryPoint = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
//        httpSecurity.exceptionHandling(handler -> handler.authenticationEntryPoint(httpStatusEntryPoint));
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        httpSecurity.cors(AbstractHttpConfigurer::disable);
        /* SPRINGBOOT 3.X.X END */
        return httpSecurity.build();
    }
}
