package com.bootcamp.books.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.bootcamp.books.security.jwt.JwtSecurityConfigurer;
import com.bootcamp.books.security.jwt.JwtTokenProvider;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/auth/signin").permitAll()
				.antMatchers(HttpMethod.GET, "/api/books").permitAll()
				.antMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/books/**").permitAll()
				.anyRequest()
				.authenticated().and()
				.apply(new JwtSecurityConfigurer(jwtTokenProvider));
		// @formatter:on
	}

}
