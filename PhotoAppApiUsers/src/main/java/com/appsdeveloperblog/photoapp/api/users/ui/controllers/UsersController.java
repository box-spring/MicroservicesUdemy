package com.appsdeveloperblog.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@GetMapping("/status/check")
	public String status() {
		return "Working";
	}
	
	@PostMapping
	public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		return "Create User methos called";
	}

}
