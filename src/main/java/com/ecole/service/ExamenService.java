package com.ecole.service;

import com.ecole.domain.Examen;
import com.ecole.domain.Matiere;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ExamenService {

    public Examen addExamen(Examen Examen);

    public Examen updateExamen(Examen Examen);

    public Set<Examen> getExamens();

    public Examen getExamenById(Long qid) throws Exception;

    public void deleteExamen(Long qid) throws Exception;

    public List<Examen> findExamensByMatiereId(Matiere matiere);

    public List<Examen> findAllExamensActives();

    public List<Examen> findAllExamensActivesOfMatiere(Matiere matiere);


}
