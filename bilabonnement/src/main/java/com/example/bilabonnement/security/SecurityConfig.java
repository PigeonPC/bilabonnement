
package com.example.bilabonnement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomSuccessHandler successHandler) throws Exception {

        /* Den udkommenterede del skal tilbage når login skal aktiveres!:
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)
                )
                .logout(logout -> logout.logoutSuccessUrl("/login"));

        return http.build();
        */



        //Denne del fjernes når login skal virke igen:
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()   // ALT er frit
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        return http.build();
        //fjern hertil

    }



    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        UserDetails skade = User.withDefaultPasswordEncoder()
                .username("skade")
                .password("1234")
                .roles("SKADE")
                .build();

        UserDetails data = User.withDefaultPasswordEncoder()
                .username("data")
                .password("1234")
                .roles("DATA")
                .build();

        UserDetails forretning = User.withDefaultPasswordEncoder()
                .username("forretning")
                .password("1234")
                .roles("FORRETNING")
                .build();

        UserDetails økonomi = User.withDefaultPasswordEncoder()
                .username("økonomi")
                .password("1234")
                .roles("ECONOMY")
                .build();

        return new InMemoryUserDetailsManager(admin, skade, data, forretning);
    }
}

