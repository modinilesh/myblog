package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//this configuration needs to be done to achieve Basic Authentication

@Configuration 	//that will help in auto configuration at starting of the application
@EnableMethodSecurity 	//that will help us in achieving method level security check the definition for more details
public class SecurityConfig {

	private static final String[] SWAGGER_WHITELIST = {
			"/swagger-ui/**",
			"/v3/api-docs/**",
			"/swagger-resources/**",
			"/swagger-resources"
	};
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean //this will be declared as bean at runtime
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
				.authorizeHttpRequests((authorize) -> 
					//authorize.anyRequest().authenticated()	//for any request to be authenticated
					authorize
							.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
							.requestMatchers(SWAGGER_WHITELIST).permitAll()
					.anyRequest().authenticated()
				)
				.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		//creating User Details
		UserDetails nilesh = User.builder()
				.username("nilesh")
				.password(passwordEncoder().encode("nilesh"))			//password must be encrypted so using BCryptPasswordEncoder
				.roles("USER")
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();
		
		//InMemory Authentication
		return new InMemoryUserDetailsManager(nilesh, admin);
	}

}
