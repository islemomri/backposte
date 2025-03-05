package com.project.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.app.filters.JWTAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JWTAuthenticationFilter jwtAuthenticationFilter;

   
	@Autowired
    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
		
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf().disable()
	            .authorizeHttpRequests(authorize -> authorize
	            		.requestMatchers( "/stages/**").hasRole("RH")
	                    .requestMatchers( "/formations/**", "/notifications/**", "/diplomes/**" ,"/typediplomes/**").hasRole("RH")
	                    .requestMatchers("/api/sites/**", "/api/directions/**", "/api/experiences/**").permitAll()
	                    .requestMatchers("/disciplines/**", "/login").permitAll()
	                    .requestMatchers("/api/employes/**").permitAll()
	                    .requestMatchers("/recrutement/**").permitAll()
	                    .requestMatchers("/recrutement/candidats/**").permitAll()
	                    .requestMatchers("/recrutement/postes/**").permitAll()
	                    .requestMatchers("/login", "/formations/**", "/notifications/**", "/diplomes/**" ,"/typediplomes/**").permitAll()
	                    
	                    .requestMatchers("/utilisateurs/**").permitAll()
	                    .requestMatchers("/api/employes/**", "/disciplines/**").permitAll()
	                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
	                    .anyRequest().authenticated()
	            )
	            
	            .sessionManagement(session -> session
	                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
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