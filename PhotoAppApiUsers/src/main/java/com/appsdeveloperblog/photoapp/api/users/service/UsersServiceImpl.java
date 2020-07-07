package com.appsdeveloperblog.photoapp.api.users.service;

import java.util.UUID;

import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;

public class UsersServiceImpl implements UsersService {

	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		return null;
	}

}