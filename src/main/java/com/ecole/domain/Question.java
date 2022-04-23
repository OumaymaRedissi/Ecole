package com.ecole.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idQuest;

    private String enonce;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String image="default.png";

    private String option_correcte;

    private String option_choisie;

    private int nbr_essai;


    @ManyToOne(fetch= FetchType.EAGER)
    private Examen examen;

    boolean question_correcte(){
        return this.getOption_correcte().equals(this.getOption_choisie());
    }

}
