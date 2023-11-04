package com.rebuiltx.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rebuiltx.blog.exceptions.CustomAccessDeniedHandler;
import com.rebuiltx.blog.security.CustomUserDetailService;
import com.rebuiltx.blog.security.JwtAuthenticationEntryPoint;
import com.rebuiltx.blog.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;


@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
	};

	
	 @Autowired
	    private AccessDeniedHandler accessDeniedHandler; // Inject the custom AccessDeniedHandler

	  
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		   http.authorizeHttpRequests(authz -> authz
//				    .requestMatchers("/api/v1/auth/login").permitAll()
//			        .anyRequest().authenticated()
//			    );
//			    http.exceptionHandling(ex -> ex.authenticationEntryPoint(point));
//			    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//			    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//			    return http.build();

		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authz -> authz
				.requestMatchers(PUBLIC_URLS).permitAll()
				.requestMatchers(HttpMethod.GET).permitAll()
//				.hasRole("ADMIN")
				.anyRequest().authenticated()
				
				);
				http.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;

	}

	@Bean 
	//all Spring to create a new bean instance and 
	//manage it throughout the application lifecycle. 
	//This means that Spring will automatically instantiate the bean, 
	//inject any dependencies, and destroy the bean when the application is shutting down.
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
	
	  @Bean
	    public AccessDeniedHandler accessDeniedHandler() {
	        return new CustomAccessDeniedHandler();
    }
	

}
