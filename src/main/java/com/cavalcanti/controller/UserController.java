package com.cavalcanti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cavalcanti.dto.user.RegisterUserDTO;
import com.cavalcanti.dto.user.RegisterUserResponseDTO;
import com.cavalcanti.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profiles", description = "Profiles management ")
@RestController
@RequestMapping("/api/v1/profiles")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<RegisterUserResponseDTO> getAll() {
		return userService.getAllUsers();
	}

	@Operation(summary = "Retrieve a User by Id", description = "Get a User object by specifying its id. The response is User object "
			+ "with id, first name, last name, email, role", tags = { "Profiles" })
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = RegisterUserResponseDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}")
	public RegisterUserResponseDTO getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@Operation(summary = "Create a User", description = "Create a User object by specifying its properties. The response is created User object "
			+ "with id, first name, last name, email, role", tags = { "Profiles" }, 
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = RegisterUserDTO.class) )  ))
	
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = RegisterUserResponseDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping
	public RegisterUserResponseDTO createUser(@RequestBody RegisterUserDTO user) {
		return userService.createUser(user);
	}

	@Operation(summary = "Update a User", description = "Update a User object by specifying its properties to be updated. The response is updated User object "
			+ "with id, first name, last name, email, role", tags = { "Profiles" })

	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = RegisterUserResponseDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/{id}")
	public RegisterUserResponseDTO updateUser(@PathVariable Long id, @RequestBody RegisterUserDTO user) {
		return userService.updateUser(id, user);
	}

	@Operation(summary = "Delete a User", description = "Delete a User object by specifying its id", tags = {
			"Profiles" })
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = RegisterUserResponseDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {

		return userService.deleteUser(id);

	}
}
