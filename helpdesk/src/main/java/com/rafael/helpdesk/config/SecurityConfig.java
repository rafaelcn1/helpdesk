package com.rafael.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rafael.helpdesk.security.JWTAuthenticationFilter;
import com.rafael.helpdesk.security.JWTUtil;
import com.rafael.helpdesk.services.CustomAuthenticationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private Environment env;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationManager authenticationManager() {
		return new CustomAuthenticationManager(userDetailsService, bCryptPasswordEncoder());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
			http.headers().frameOptions().disable();
		}
		http.cors().and().csrf().disable();

		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
//		http.authorizeHttpRequests().requestMatchers("/**").hasRole("ADMIN").and().formLogin();

		http.authorizeHttpRequests().requestMatchers("/**").hasRole("ADMIN").and().authorizeHttpRequests()
				.requestMatchers("/chamados/**").hasRole("USER").and().formLogin();
		return http.build();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	public UserDetailsService userDetailsService() {
		// Criação dos usuários com senhas codificadas usando BCrypt
		String userPassword = bCryptPasswordEncoder().encode("user");
		UserDetails user = User.withUsername("user").password(userPassword).roles("USER").build();

		String adminPassword = bCryptPasswordEncoder().encode("admin");
		UserDetails admin = User.withUsername("admin").password(adminPassword).roles("ADMIN", "USER").build();

		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
