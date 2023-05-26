package com.cavalcanti.dto.user;

import com.cavalcanti.domain.User;
import com.cavalcanti.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponseDTO {

	private String firstname;
	private String lastname;
	private String email;
	private Role role;

	public static RegisterUserResponseDTO fromModel(User user) {
		RegisterUserResponseDTO dto = RegisterUserResponseDTO.builder()
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.email(user.getEmail())
				.role(user.getRole()).build();
		return dto;

	}
}
