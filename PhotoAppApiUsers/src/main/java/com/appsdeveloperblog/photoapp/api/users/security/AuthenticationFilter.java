package com.appsdeveloperblog.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private Environment env;
	private UsersService usersService;

	public AuthenticationFilter(Environment env, 
			UsersService usersService, 
			AuthenticationManager auth) {
		this.env = env;
		this.usersService = usersService;
		super.setAuthenticationManager(auth);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			  
        	// This reads the request input stream and maps the content 
        	// to out LoginRequestModel class
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);
            
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    creds.getEmail(),		// username
                    creds.getPassword(),	// password
                    new ArrayList<>());		// list of roles (authorities), should not be null
            
            
            Authentication auth = getAuthenticationManager().authenticate(token);
            
            
            return auth;
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		/*
		 * Here we create our JsonWebToken which can have several values but now we are using the public user Id and the token
		 */

		// Get the username from Principal object but ... we already had it
		String username = ((User) authResult.getPrincipal()).getUsername();
		
		// loading the UserDto from the database using username (in this case email)
		UserDto userDetails = this.usersService.getUserDetailsByEmail(username);
		
		String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret") )
                .compact();
        
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
		
	}

}
