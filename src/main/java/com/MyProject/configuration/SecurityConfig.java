package com.MyProject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.MyProject.service.UserServiceImpl;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserServiceImpl userService;
	
	   @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	    }
	
	   @Bean
	   SecurityFilterChain web (HttpSecurity https)throws Exception{
		   
		   https
		   .cors(cors -> cors.configurationSource(request -> {
                var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                corsConfiguration.setAllowedOrigins(java.util.List.of("http://localhost:3000"));
                corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                return corsConfiguration;
            }))
			.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/hikSystem/**")
            )

		   	.authorizeHttpRequests(authorize->authorize
		   		.requestMatchers("/static/**").permitAll()
		   		.requestMatchers( "/js/**", "/css/**").permitAll()
				.requestMatchers("/resources/**").permitAll()
			   	.requestMatchers("/").permitAll()
			   	.requestMatchers("/introduce").permitAll()
			   	.requestMatchers("/login").permitAll()
		   		.requestMatchers("/index").permitAll()
		   		.requestMatchers("/reg").permitAll()
		   		.requestMatchers("/registration").permitAll()
		   		.requestMatchers("/activate").permitAll()
		   		.requestMatchers("/activ").permitAll()
				.requestMatchers("/api/hikSystem/**").permitAll()
		   		
		   		.anyRequest().authenticated())
		   	
		   	.formLogin(login->login
		   			.loginPage("/login").permitAll())
		   	
		   	.logout(out->out
		   			.logoutSuccessUrl("/login?logout").permitAll());
		    https
		    	.headers(headers->headers
		    	.contentSecurityPolicy(csp->csp
		    			.policyDirectives("default-src 'self'; "
		    						+ "script-src 'self' https://code.jquery.com https://localhost:8080; "
		    						+ "style-src 'self' https://maxcdn.bootstrapcdn.com; "
		    						+ "img-src 'self' https://localhost:8080; "
		    						+ "frame-ancestors 'self' https://localhost:8080;"
		    						+ "connect-src 'self' https://localhost:8080; "
		    						+ "font-src 'self' https://maxcdn.bootstrapcdn.com; "
		    						+ "object-src 'self';")
		    			)
		    	);
		   return https.build();
	   }
	

}
