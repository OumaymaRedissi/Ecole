package com.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idMat;
    private String title;
    private String description;

    @OneToMany(mappedBy="matiere",cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Examen> examens=new LinkedHashSet<>();


}
