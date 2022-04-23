package com.ecole.api;

import com.ecole.domain.Examen;
import com.ecole.domain.Matiere;
import com.ecole.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/examen")
@CrossOrigin("*")
public class ExamenController {
    @Autowired
    private ExamenService examenService;


    @PostMapping("/add")
    public ResponseEntity<Examen> addExamen(@RequestBody Examen e){
        Examen examen=this.examenService.addExamen(e);
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

}
