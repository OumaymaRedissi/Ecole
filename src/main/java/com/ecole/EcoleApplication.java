package com.ecole;

import java.util.ArrayList;
import java.util.HashSet;

import com.ecole.domain.*;
import com.ecole.service.ExamenService;
import com.ecole.service.MatiereService;
import com.ecole.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	CommandLineRunner run(UserService userService, QuestionService questionService, ExamenService examenService, MatiereService matiereService) {
		return args ->{
			
			Role role_admin = new Role(null,"ROLE_ADMIN");
			Role role_prof = new Role(null,"ROLE_PROF");
			Role role_etudiant = new Role(null,"ROLE_ETUDIANT");

			
			userService.saveRole(role_admin);
			userService.saveRole(role_prof);
			userService.saveRole(role_etudiant);
			userService.saveRole(role_etudiant);

			
			userService.saveUser(new User(null,"Phoebe Buffay","phoebe","Azerty123","phoebe@gmail.com","90909090",true,new ArrayList<>()));
			userService.saveUser(new User(null,"Ross Geller","ross","Azerty123","ross@gmail.com","90909090",true,new ArrayList<>()));
			userService.saveUser(new User(null,"Rachel Green","rachel","Azerty123","rachel@gmail.com","90909090",true,new ArrayList<>()));
			userService.saveUser(new User(null,"Joey Tribbiani","joey","Azerty123","joey@gmail.com","90909090",true,new ArrayList<>()));

			
			userService.addRoleToUser("phoebe","ROLE_ADMIN");
			userService.addRoleToUser("ross","ROLE_PROF");
			userService.addRoleToUser("rachel","ROLE_ETUDIANT");
			userService.addRoleToUser("joey","ROLE_USER");

			matiereService.saveMatiere(new Matiere(null,"Algo1","Algorithmique",null));
			matiereService.saveMatiere(new Matiere(null,"Algo2","Algorithmiques avancées",null));
			matiereService.saveMatiere(new Matiere(null,"Java","Algorithmique",null));
			matiereService.saveMatiere(new Matiere(null,"Prog","Algorithmique",null));

			examenService.saveExamen(new Examen(null,"DS1","Examen de DS", 20,true,matiereService.getMatiereById(3L),new HashSet<>()));
			examenService.saveExamen(new Examen(null,"TP3","Examen de TP3", 20,true,matiereService.getMatiereById(2L),new HashSet<>()));



			questionService.saveQuestion(new Question(null,"CRUD est l'abreviation de :","Create Read Update Delete","Create Read Update Delete","Cry Read Untill Die","Create Role Update Dumb",null,"Create Read Update Delete","",0,null));
			questionService.saveQuestion(new Question(null,"MVC est l'abreviation de :","Model View Controller","Movie View Controller","Move Very Carefully","My View Controls",null,"Model View Controller","",0,null));
			questionService.saveQuestion(new Question(null,"Spring est un framework de  :","Java","Python","C","SQL",null,"Java","",0,null));

			questionService.addQuestionToExamens(1L,1L);
			questionService.addQuestionToExamens(1L,2L);
			questionService.addQuestionToExamens(1L,3L);



		};
	}

}
