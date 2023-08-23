package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                                authorize.requestMatchers(HttpMethod.POST, "/api/user/login").permitAll().
                                        requestMatchers(HttpMethod.GET, "/api/user/all-users").hasAuthority("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/api/user/get-code-verification").hasAuthority("OWNER")
                                        .requestMatchers(HttpMethod.GET, "/api/restaurant/all-restaurant").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/restaurant/restaurant-food").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/order/make-order/*").hasAuthority("OWNER")
//                                        requestMatchers(HttpMethod.GET, "/api/user/temp").authenticated()
//                                .requestMatchers(HttpMethod.GET,"/api/temp").hasRole("ADMIN")
                );
//        http.authorizeHttpRequests(configurer ->
//                configurer.
//                    requestMatchers(HttpMethod.GET,"/api/user/temp").hasAuthority("ADMIN")
//                );
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
