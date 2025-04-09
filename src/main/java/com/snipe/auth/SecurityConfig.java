package com.snipe.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.List;

@Configuration
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	public SecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	// Define Role-based Route Lists
	private static final List<String> PUBLIC_ROUTES = List.of("/auth/**");
	private static final List<String> USER_ROUTES = List.of("/users/**");
	private static final List<String> ADMIN_ROUTES = List.of("/admin/**");
	private static final List<String> ORG_ROUTES = List.of("/organization/**");
	private static final List<String> HR_ROUTES = List.of("/hr/**");
	private static final List<String> EMPLOYEE_ROUTES = List.of("/employee/**");
	private static final List<String> ROLE_ROUTES = List.of("/roles/**");

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.disable()) // CORS handled separately in CorsConfig
				.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity, adjust as needed
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(PUBLIC_ROUTES.toArray(new String[0])).permitAll() 
						.requestMatchers(ADMIN_ROUTES.toArray(new String[0])).hasAnyAuthority("ADMIN")
						.requestMatchers(USER_ROUTES.toArray(new String[0])).hasAnyAuthority("USER","ORGANIZATION", "ADMIN", "HR")
						.requestMatchers(ORG_ROUTES.toArray(new String[0])).hasAnyAuthority("ORGANIZATION", "ADMIN", "HR")
						.requestMatchers(HR_ROUTES.toArray(new String[0])).hasAnyAuthority("HR", "ADMIN")
						.requestMatchers(EMPLOYEE_ROUTES.toArray(new String[0])).hasAnyAuthority("EMPLOYEE", "ADMIN", "HR")
						.requestMatchers(ROLE_ROUTES.toArray(new String[0])).hasAnyAuthority("ADMIN", "HR").anyRequest()
						.authenticated())
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	

	// ✅ Define JwtAuthenticationFilter as a Bean
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}
}
