package com.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Examen {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idExam;

    private String title;
    private String description;
    private String nbrQuest;
    private String note;
    private boolean etat=false;

    @ManyToOne(fetch=FetchType.EAGER)
    private Matiere matiere;

    @OneToMany(mappedBy="examen",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();

    @ManyToOne
    private Set<Resultat> resultats = new HashSet<>();
}
