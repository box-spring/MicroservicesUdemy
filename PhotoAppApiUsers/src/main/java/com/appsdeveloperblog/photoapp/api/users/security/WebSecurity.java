package com.appsdeveloperblog.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	Environment env;
	UsersService usersService;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	public WebSecurity(Environment env, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = env;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
		.and()
		.addFilter(getAuthenticationFilter()); // add an Authentication FIlter
	
	// to be able to connect to h2-console which uses frames
	http.headers().frameOptions().disable();
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(this.env, this.usersService,
				authenticationManager());

		// set the default authentication manager which comes with Spring Security
		// available here because of @EnableWebSecurity annotation
		// and this method throws an exception
		authenticationFilter.setAuthenticationManager(authenticationManager());

		// this is the path for login request
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));

		return authenticationFilter;

	}
}
