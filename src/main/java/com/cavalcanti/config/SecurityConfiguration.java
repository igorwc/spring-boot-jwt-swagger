package com.cavalcanti.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.cavalcanti.domain.enums.Permission;
import com.cavalcanti.domain.enums.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
        JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http.cors(cors -> { cors.disable();
	})
	.csrf(csrf -> csrf.disable());
//	.csrf(CsrfConfigurer::disable);

//	http
//		.csrf(csrf -> csrf
//			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

	
//	http.authorizeHttpRequests((auth) -> auth.requestMatchers(
//		"/api/v1/auth/**",
//		"/v2/api-docs",
//		"/v3/api-docs",
//		"/v3/api-docs/**",
//		"/swagger-resources",
//		"/swagger-resources/**",
//		"/configuration/ui",
//		"/configuration/security",
//		"/swagger-ui/**",
//		"/webjars/**",
//		"/swagger-ui.html")
//		.permitAll()
//
////		.anyRequest()
//////		.authenticated()
//		);

	http.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/v1/management/**").hasAnyRole(
		Role.ADMIN.name(),
		Role.MANAGER.name())
		.requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(
			Permission.ADMIN_READ.name(),
			Permission.MANAGER_READ.name())
		.requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(
			Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name())
		.requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(
			Permission.ADMIN_UPDATE.name(),
			Permission.MANAGER_UPDATE.name())
		.requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(
			Permission.ADMIN_DELETE.name(),
			Permission.MANAGER_DELETE.name()) 

//		.anyRequest()
		
		);
	http
	.authorizeHttpRequests((auth) -> auth.requestMatchers(
		"/api/v1/auth/**",
		"/api/v1/auth/login",
		"/v2/api-docs",
		"/v3/api-docs",
		"/v3/api-docs/**",
		"/swagger-resources",
		"/swagger-resources/**",
		"/configuration/ui",
		"/configuration/security",
		"/swagger-ui/**",
		"/webjars/**",
		"/swagger-ui.html")
		.permitAll()
		.requestMatchers("/api/v1/management/**").hasAnyRole(
			Role.ADMIN.name(),
			Role.MANAGER.name())
			.requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(
				Permission.ADMIN_READ.name(),
				Permission.MANAGER_READ.name())
			.requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(
				Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name())
			.requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(
				Permission.ADMIN_UPDATE.name(),
				Permission.MANAGER_UPDATE.name())
			.requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(
				Permission.ADMIN_DELETE.name(),
				Permission.MANAGER_DELETE.name()) 
	        .anyRequest().authenticated()
		);
	http.sessionManagement((management) -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	/*
	 * .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
	 * 
	 * .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
	 * .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
	 * .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
	 * .requestMatchers(DELETE,
	 * "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
	 */

	http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	http.authenticationProvider(authenticationProvider);
	http.logout((logout) -> logout.logoutSuccessUrl("/api/v1/auth/logout")
		.addLogoutHandler(logoutHandler)
		.logoutSuccessHandler(
			(request, response, authentication) -> SecurityContextHolder.clearContext()
			)
		); 

	return http.build();
    }
}
