package com.ecole.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class User {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUser;
	private String nomPrenom;

	@Column(nullable=false)
    private String username;
	@Column(nullable=false)
	private String password;

	private String email;
	private String telephone;
	private boolean active=true;

	@ManyToOne
	private Set<Resultat> resultats = new HashSet<>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<Role> roles= new ArrayList<>();

}
