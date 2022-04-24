package com.ecole.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecole.security.filter.JWTAuthenticationFilter;
import com.ecole.security.filter.JWTAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	JWTAuthenticationFilter jWTAuthenticationFilter = new JWTAuthenticationFilter(authenticationManagerBean());
	jWTAuthenticationFilter.setFilterProcessesUrl("/api/login");
	http.csrf().disable();
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh/**").permitAll();
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.GET,"/examen**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.POST,"/examen**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.GET,"/matiere**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.POST,"/matiere**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.GET,"/api/user/ajout**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.POST,"/api/users/**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.GET,"/api/users**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().anyRequest().authenticated();
	http.addFilter(jWTAuthenticationFilter); 
	//Authorization
	http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
}
	
	

