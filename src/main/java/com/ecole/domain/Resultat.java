package com.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resultat {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRes;
    private int nbr_reponses_correctes;
    private float note_obtenue;
    private int nbr_essai;


    @ManyToOne(fetch= FetchType.EAGER)
    @JsonIgnore
    private Examen examen;

    @ManyToOne(fetch= FetchType.EAGER)
    @JsonIgnore
    private User user_etud;
}
