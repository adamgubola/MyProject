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
		   	.authorizeHttpRequests(authorize->authorize
		   		.requestMatchers("/static/**").permitAll()
		   		.requestMatchers( "/js/**", "/css/**").permitAll()
				.requestMatchers("/resources/**").permitAll()
			   	.requestMatchers("/").permitAll()
			   	.requestMatchers("/login").permitAll()
		   		.requestMatchers("/index").permitAll()
		   		.requestMatchers("/reg").permitAll()
		   		.requestMatchers("/registration").permitAll()
		   		.requestMatchers("/activate").permitAll()
		   		.requestMatchers("/activ").permitAll()
		   		
		   		.anyRequest().authenticated())
		   	
		   	.formLogin(login->login
		   			.loginPage("/login").permitAll())
		   	
		   	.logout(out->out
		   			.logoutSuccessUrl("/login?logout").permitAll());
		   		
		   
		   return https.build();
	   }
	

}
