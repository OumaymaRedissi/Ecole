package com.ecole.api;

import com.ecole.domain.*;
import com.ecole.repository.UserRepository;
import com.ecole.service.ExamenService;
import com.ecole.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/examen")
@CrossOrigin("*")
public class ExamenController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamenService examenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<Examen> addExamen(@RequestBody Examen e){
        Examen examen=this.examenService.saveExamen(e);
        return ResponseEntity.ok(examen);
    }

    @GetMapping("/{examenId}")
    public Examen getExamen(@PathVariable("examenId") Long qid) throws Exception {
        Examen examen=this.examenService.getExamenById(qid);
        if(examen==null) {
            throw new Exception("Examen not found exception");
        }
        return examen;
    }

    @GetMapping("/getAllExamen")
    public ResponseEntity<?> getAllMatiere() throws Exception{
        Set<Examen> examenzes=this.examenService.getExamens();
        if(examenzes==null) {
            throw new Exception("there is no examen in the database");
        }
        return ResponseEntity.ok(examenzes);
    }

    @PutMapping("/update")
    public ResponseEntity<Examen> updateExamen(@RequestBody Examen examen) {
        Examen theexamen=this.examenService.updateExamen(examen);
        return ResponseEntity.ok(theexamen);
    }

    @DeleteMapping("/{examenId}")
    public void deleteMatiere(@PathVariable("examenId") Long qid) throws Exception {
        Examen examen=this.examenService.getExamenById(qid);
        if(examen==null) {
            throw new Exception("Examen not found exception");
        }
        this.examenService.deleteExamen(qid);
    }

    @GetMapping("/ByMatiere/{idmat}")
    public ResponseEntity<?> getExamensByMatiereId(@PathVariable("idmat") Long idmat){
        Matiere matiere=new Matiere();
        matiere.setIdMat(idmat);
        return ResponseEntity.ok(this.examenService.findExamensByMatiereId(matiere));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveExamens(){
        return ResponseEntity.ok(this.examenService.findAllExamensActives());
    }

    @GetMapping("/matiere/active/{idmat}")
    public ResponseEntity<?> getAllActiveExamensOfMatiere(@PathVariable("idmat") Long idmat){
        Matiere matiere=new Matiere();
        matiere.setIdMat(idmat);
        return ResponseEntity.ok(this.examenService.findAllExamensActivesOfMatiere(matiere));
    }
    @PostMapping("/etudiant/evaluation-examen")
    public ResponseEntity<?> evaluationExamen (@RequestBody Examen examen){
        System.out.println(examen.getQuestions());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        //Initialization du resultat et affectation d'un etudiant
        User u=this.userRepository.findByUsername(username);
        Resultat resultat=new Resultat();
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
        return ResponseEntity.ok(resultat);
    }
}
