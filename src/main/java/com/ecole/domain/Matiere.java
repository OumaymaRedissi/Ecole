package com.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idMat;
    @Column(unique = true)
    private String title;
    private String description;

    @OneToMany(mappedBy="matiere",cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Examen> examens=new LinkedHashSet<>();


}
