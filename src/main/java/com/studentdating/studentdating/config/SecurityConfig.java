package com.studentdating.studentdating.config;

import com.studentdating.studentdating.model.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.mvcMatchers("/signup").permitAll()
				.mvcMatchers("/relations").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login").permitAll()
				.and()
				.logout().permitAll();

		http.csrf().disable(); //todo om geen post 403 forbidden te krijgen //todo test
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				// ignore all URLs that start with /resources/ or /static/
				.antMatchers("/static/**", "/css/**", "/img/**", "/js/**");
	}
}
