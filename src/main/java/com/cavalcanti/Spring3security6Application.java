package com.cavalcanti;
  

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
 
import com.cavalcanti.domain.enums.Role;
import com.cavalcanti.dto.user.RegisterUserDTO;
import com.cavalcanti.service.UserService;

@SpringBootApplication
public class Spring3security6Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring3security6Application.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			UserService service
	) {
		return args -> {
			var admin = RegisterUserDTO.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.createUser(admin));  //.getAccessToken());

			var manager = RegisterUserDTO.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("manager@mail.com")
					.password("password")
					.role(Role.MANAGER)
					.build();
			System.out.println("Manager token: " + service.createUser(manager));//.getAccessToken());

		};
	}
}
