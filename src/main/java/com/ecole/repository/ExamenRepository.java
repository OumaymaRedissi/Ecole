package com.ecole.repository;

import com.ecole.domain.Examen;
import com.ecole.domain.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    List<Examen> findExamensByMatiere(Matiere matiere);

    List<Examen> findByEtat(boolean etat);

    List<Examen> findByMatiereAndEtat(Matiere matiere, boolean etat);
    Examen findByIdExam(Long idExamen);


}
