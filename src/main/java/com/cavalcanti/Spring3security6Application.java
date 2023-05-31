package com.cavalcanti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.cavalcanti.domain.User;
import com.cavalcanti.domain.enums.Role;
import com.cavalcanti.repository.UserRepository;
import com.cavalcanti.service.auth.AuthenticationService;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EntityScan( basePackages = {"com.cavalcanti.domain"})
public class Spring3security6Application { 


    @Autowired
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
	SpringApplication.run(Spring3security6Application.class, args);
    }

//    @Bean(name = "fdfdfdf")
//    @Qualifier("serviceInitial")
//    public CommandLineRunner commandLineRunner(
//	    UserRepository repository) {
//	return args -> {
//	    var admin = User.builder()
//		    .firstname("Admin")
//		    .lastname("Admin")
//		    .email("admin@mail.com")
//		    .password(authenticationService.getPasswordEncoder().encode("password"))
//		    .role(Role.ADMIN)
//		    .build();
//	    System.out.println("Admin token: " + repository.save(admin)); // .getAccessToken());
//
//	    var manager = User.builder()
//		    .firstname("Admin")
//		    .lastname("Admin")
//		    .email("manager@mail.com")
//		    .password(authenticationService.getPasswordEncoder().encode("password"))
//		    .role(Role.MANAGER)
//		    .build();
//	    System.out.println("Manager token: " + repository.save(manager));// .getAccessToken());
//
//	};
//    }
}
