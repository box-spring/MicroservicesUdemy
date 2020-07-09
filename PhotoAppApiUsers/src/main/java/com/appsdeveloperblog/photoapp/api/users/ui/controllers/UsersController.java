package com.appsdeveloperblog.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	UsersService usersService;
	
	@GetMapping("/status/check")
	public String status() {
		return "Working";
	}
	
	@PostMapping
	public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(userDetails, UserDto.class);
		
		usersService.createUser(userDto);
		return "Create User methos called";
	}

}
