package com.ecole;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecole.domain.Role;
import com.ecole.domain.User;
import com.ecole.service.UserService;

@SpringBootApplication
public class EcoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoleApplication.class, args);
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args ->{
			
			Role role_admin = new Role(null,"ROLE_ADMIN");
			Role role_prof = new Role(null,"ROLE_PROF");
			Role role_etudiant = new Role(null,"ROLE_ETUDIANT");
			Role role_user = new Role(null,"ROLE_USER");
			
			
			userService.saveRole(role_admin);
			userService.saveRole(role_prof);
			userService.saveRole(role_etudiant);
			userService.saveRole(role_user);

			
			userService.saveUser(new User(null,"Phoebe Buffay","phoebe","Azerty123","phoebe@gmail.com","90909090",true,new ArrayList<>()));
			userService.saveUser(new User(null,"Ross Geller","ross","Azerty123","ross@gmail.com","90909090",true,new ArrayList<>()));
			userService.saveUser(new User(null,"Rachel Green","rachel","Azerty123","rachel@gmail.com","90909090",true,new ArrayList<>()));
			userService.saveUser(new User(null,"Joey Tribbiani","joey","Azerty123","joey@gmail.com","90909090",true,new ArrayList<>()));

			
			userService.addRoleToUser("phoebe","ROLE_ADMIN");
			userService.addRoleToUser("ross","ROLE_PROF");
			userService.addRoleToUser("rachel","ROLE_ETUDIANT");
			userService.addRoleToUser("joey","ROLE_USER");



			
		};
	}

}
