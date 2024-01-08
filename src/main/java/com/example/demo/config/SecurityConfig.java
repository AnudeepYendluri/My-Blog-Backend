package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	
	private UserAuthenticationProvider userAuthenticationProvider;
	
	
	
	public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
			UserAuthenticationProvider userAuthenticationProvider) {
		super();
		this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
		this.userAuthenticationProvider = userAuthenticationProvider;
	}



	@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
			http
			  .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
			  .and()
			  .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
	          .csrf().disable()
			  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			  .and()
			  .authorizeHttpRequests((requests)-> requests
					  .requestMatchers(HttpMethod.POST,"/login","/register").permitAll()
					  .anyRequest().authenticated()
					  
					  );
			  
			return http.build();
		}
}
