package com.ecole.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecole.domain.Role;
import com.ecole.domain.User;
import com.ecole.repository.RoleRepository;
import com.ecole.repository.UserRepository;
import com.ecole.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService{

	private final UserRepository userRepository ;
	private final RoleRepository roleRepository ;
	private final PasswordEncoder passwordEncoder ;
	
	
	//Methode de UserDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByUsername(username);
		if(user == null) {
			log.error("le username {} n'existe pas dans la BD",username);
			throw new UsernameNotFoundException ("le username n'existe pas dans la BD");
		}else {
			log.info("le username {} trouvé dans la BD",username);			
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role ->{authorities.add(new SimpleGrantedAuthority(role.getName()));
		} );
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}
	
	//Methodes de UserService
	@Override
	public User saveUser(User user) {
		log.info("Enregistrer nouveau User {} à la BD",user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Enregistrer nouveau Role à la BD",role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		log.info("Ajout du role {} à l'utilisateur {} ",rolename,username);
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(rolename);
		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching {}",username);
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching tous les Users");
		return userRepository.findAll();
	}

	

}
