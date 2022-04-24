package com.ecole.serviceimpl;

import com.ecole.domain.*;
import com.ecole.repository.ExamenRepository;
import com.ecole.repository.UserRepository;
import com.ecole.service.ExamenService;
import com.ecole.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExamenServiceImpl implements ExamenService {

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private QuestionService questionService;

    @Override
    public Examen saveExamen(Examen examen) {
        log.info("Ajout examen {}",examen.getTitle());
        return this.examenRepository.save(examen);
    }

    @Override
    public Examen updateExamen(Examen examen) {
        log.info("Mise à jour  examen {}",examen.getTitle());
        return this.examenRepository.save(examen);

    }

    @Override
    public Set<Examen> getExamens() {
        log.info("Listes de tous les examens");
        return new HashSet<>(this.examenRepository.findAll());

    }

    @Override
    public Examen getExamenById(Long exid) throws Exception {

        Examen examen=this.examenRepository.findById(exid).get();
        if(examen==null) {
            log.error("Id examen introuvable");

            throw new Exception("Examen introuvable ");
        }
        log.info("Extraction examen");
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

    @Override
    public Resultat evaluationExamen(Examen examen){
        System.out.println(examen.getQuestions());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User u=this.userRepository.findByUsername(username);
        Resultat resultat=new Resultat();
        resultat.setExamen(examen);
        resultat.setUser_etud(u);
        Integer nbr_reponses_correctes= 0;
        float note_obtenue = 0;
        Integer essai=0;
        for(Question question:examen.getQuestions()) {
            try {
                Question q=this.questionService.getQuestionById(question.getIdQuest());
                if(question.getOption_choisie().trim().equals(question.getOption_correcte().trim())) {
                    nbr_reponses_correctes++;
                    essai++;
                }
                else {
                    essai++;
                }
                float note_obtenue_par_question=examen.getNoteMax()/examen.getQuestions().size();
                note_obtenue=nbr_reponses_correctes*note_obtenue_par_question;
                //set a list to questions in users attempted quiz
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resultat.setNbr_reponses_correctes(nbr_reponses_correctes);
        resultat.setNbr_essai(essai);
        resultat.setNote_obtenue(note_obtenue);

        //examen.getResultats().add(resultat);
        this.examenRepository.save(examen);

        return resultat;
    }

}
