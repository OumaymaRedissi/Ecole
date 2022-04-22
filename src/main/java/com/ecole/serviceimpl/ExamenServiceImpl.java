package com.ecole.serviceimpl;

import com.ecole.domain.Examen;
import com.ecole.domain.Matiere;
import com.ecole.repository.ExamenRepository;
import com.ecole.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExamenServiceImpl implements ExamenService {

    @Autowired
    private ExamenRepository examenRepository;

    @Override
    public Examen addExamen(Examen examen) {
        return this.examenRepository.save(examen);
    }

    @Override
    public Examen updateExamen(Examen examen) {
        return this.examenRepository.save(examen);

    }

    @Override
    public Set<Examen> getExamens() {
        return new HashSet<>(this.examenRepository.findAll());

    }

    @Override
    public Examen getExamenById(Long exid) throws Exception {
        Examen examen=this.examenRepository.findById(exid).get();
        if(examen==null) {
            throw new Exception("Examen introuvable ");
        }
        return examen;
    }

    @Override
    public void deleteExamen(Long exid) throws Exception {
        Examen examen=this.examenRepository.findById(exid).get();
        if(examen==null) {
            throw new Exception("Examen introuvable ! ");
        }
        this.examenRepository.deleteById(exid);
    }

    @Override
    public List<Examen> findExamensByMatiereId(Matiere matiere) {
        List<Examen> listOfExamensByMatiereId=this.examenRepository.findExamensByMatiere(matiere);

        System.out.println(listOfExamensByMatiereId);

        return this.examenRepository.findExamensByMatiere(matiere);

    }

    @Override
    public List<Examen> findAllExamensActives() {

        return this.examenRepository.findByEtat(true);

    }

    @Override
    public List<Examen> findAllExamensActivesOfMatiere(Matiere matiere) {
        return this.examenRepository.findByMatiereAndEtat(matiere, true);
    }

}
