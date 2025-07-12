package com.monaum.Money_Management.security;

import com.monaum.Money_Management.exception.CustomAccessDeniedHandler;
import com.monaum.Money_Management.exception.CustomAuthenticationEntryPoint;
import com.monaum.Money_Management.module.auth.LogoutService;
import com.monaum.Money_Management.module.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired private UserService usersService;
	@Autowired private JwtAuthenticationFilter jwtAuthFilter;
	@Autowired private LogoutService logoutHandler;
	@Autowired private CustomAccessDeniedHandler accessDeniedHandler;
	@Autowired private CustomAuthenticationEntryPoint authenticationEntryPoint;

	private static final String[] WHITE_LIST_URL = new String[] {
			"/api/v1/auth/**",
	};

	// Define PasswordEncoder Bean
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers(WHITE_LIST_URL).permitAll()
								.anyRequest().authenticated()
				)
				.exceptionHandling(
						ex -> ex.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
				)
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(
						l -> l.logoutUrl("/api/v1/auth/logout")
								.addLogoutHandler(logoutHandler)
								.logoutSuccessHandler(
										(request, response, authentication) -> SecurityContextHolder.clearContext()
								)
				);

		return http.build();
	}

	// AuthenticationProvider Bean with PasswordEncoder
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(usersService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	// AuthenticationManager Bean
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
