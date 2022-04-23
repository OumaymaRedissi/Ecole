package com.ecole.api;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecole.domain.*;
import com.ecole.repository.UserRepository;
import com.ecole.service.ExamenService;
import com.ecole.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecole.service.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	@Autowired
	private QuestionService questionService;

	@Autowired
	private ExamenService examenService;

	@Autowired
	private UserRepository userRepository;

	private final UserService userService;    
	
	@GetMapping("/users")
	public ResponseEntity<List<User>>getUsers(){
		
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@PostMapping("/user/ajout")
	public ResponseEntity<User>ajoutUser(@RequestBody User user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/ajout").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}
	
	@PostMapping("/role/ajout")
	public ResponseEntity<Role>ajoutRole(@RequestBody Role role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/ajout").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}
	
	@PostMapping("/user/ajout-role")
	public ResponseEntity<?>ajoutRoleAUser(@RequestBody RoleToUserForm form){
		userService.addRoleToUser( form.getUsername(),form.getRolename());
		return ResponseEntity.ok().build();
	}
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token =authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.getUser(username);
				String access_token =JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String,String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			}catch(Exception exception) {
				response.setHeader("error",exception.getMessage());
				response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
				//response.sendError(org.springframework.http.HttpStatus.FORBIDDEN.value());
				Map<String,String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		}else {
					throw new RuntimeException("Refresh token is missing ");		}

	}

	@PostMapping("/etudiant/evaluation-examen")
	public ResponseEntity<?> evaluationExamen (@RequestBody Examen examen){
		System.out.println(examen.getQuestions());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		//Initialization du resultat et affectation d'un etudiant
		User u=this.userRepository.findByUsername(username);
		Resultat resultat=new Resultat();
		resultat.setUser_etud(u);
		Integer nbr_reponses_correctes= 0;
		float note_obtenue = 0;
		Integer essai=0;
		for(Question question:examen.getQuestions()) {
			try {
				Question q=this.questionService.getQuestionById(question.getIdQuest());
				//set specific question attended by user in his/her attempted quiz so it can access limited number of time to quiz
				if(question.getOption_choisie().trim().equals(question.getOption_correcte().trim())) {
					nbr_reponses_correctes=nbr_reponses_correctes+1;
					essai++;
				}
				else {
					essai++;
				}
				float note_obtenue_par_question=examen.getNoteMax()/examen.getQuestions().size();
				note_obtenue=nbr_reponses_correctes*note_obtenue_par_question;
				//set a list to questions in users attempted quiz
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		resultat.setNbr_reponses_correctes(nbr_reponses_correctes);
		resultat.setNbr_essai(essai);
		resultat.setNote_obtenue(note_obtenue);
		return ResponseEntity.ok(resultat);
	}
}


