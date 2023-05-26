package com.cavalcanti.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cavalcanti.domain.User;
import com.cavalcanti.dto.user.RegisterUserDTO;
import com.cavalcanti.dto.user.RegisterUserResponseDTO;
import com.cavalcanti.repository.UserRepository;

@Service
public class UserService {

//	public RegisterUserResponseDTO create(RegisterUserDTO newUser) {
//		return newUser;
//		
//	}

	@Autowired
	private UserRepository userRepository;

	public List<RegisterUserResponseDTO> getAllUsers() {
		List<RegisterUserResponseDTO> list = userRepository.findAll().stream()
				.map(u -> RegisterUserResponseDTO.fromModel(u)).collect(Collectors.toList());

		return list;
	}

	public RegisterUserResponseDTO getUserById(Long id) {
		User user = userRepository.findById(id).orElse(null);

		return RegisterUserResponseDTO.fromModel(user);
	}

	public RegisterUserResponseDTO createUser(RegisterUserDTO newUserDTO) {
		
		var newUser  = User.builder()
		        .firstname(newUserDTO.getFirstname())
		        .lastname(newUserDTO.getLastname())
		        .email(newUserDTO.getEmail())
		        .password(newUserDTO.getPassword())
		        .role(newUserDTO.getRole())
		        .build();
		User u = userRepository.save(newUser);
		return RegisterUserResponseDTO.fromModel(u);
	}

	public RegisterUserResponseDTO updateUser(Long id, RegisterUserDTO user) {
		User existingUser = userRepository.findById(id).orElseThrow();
		if(user.getFirstname() != null && !user.getFirstname().isBlank()) {
			existingUser.setFirstname(user.getFirstname());	
		}
		if(user.getLastname() != null && !user.getLastname().isBlank()) {
			existingUser.setLastname(user.getLastname());	
		}
		if(user.getEmail() != null && !user.getEmail().isBlank()) {
			existingUser.setEmail(user.getEmail());	
		} 
		User u = userRepository.save(existingUser);
		return RegisterUserResponseDTO.fromModel(u);
	}

	public String deleteUser(Long id) {
		try {
			userRepository.findById(id).get();
			userRepository.deleteById(id);
			return "User deleted successfully";
		} catch (Exception e) {
			return "User not found";
		}
	}

}
