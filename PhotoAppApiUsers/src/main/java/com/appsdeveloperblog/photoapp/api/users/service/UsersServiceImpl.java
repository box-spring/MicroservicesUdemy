package com.appsdeveloperblog.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.data.UsersRepository;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		
		usersRepository.save(userEntity);
		
		UserDto createdUser = mapper.map(userEntity, UserDto.class);
		return createdUser;
	}

}
