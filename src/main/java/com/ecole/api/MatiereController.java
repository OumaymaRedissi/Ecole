package com.ecole.api;

import com.ecole.domain.Matiere;
import com.ecole.service.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/matiere")
@CrossOrigin("*")
public class MatiereController {
    @Autowired
    private MatiereService matiereService;

    @PostMapping("/admin/add")
    public ResponseEntity<?> addMatiere(@RequestBody Matiere matiere){
        Matiere matiere1=this.matiereService.saveMatiere(matiere);
        return ResponseEntity.ok(matiere1);
    }

    @GetMapping("/{matiereId}")
    public ResponseEntity<Matiere> getMatiere(@PathVariable("matiereId") Long idmat) throws Exception {
        System.out.println("Matiere id requested from the user is "+idmat);
        Matiere matiere=this.matiereService.getMatiereById(idmat);
        if(matiere==null) {
            throw new Exception("Matiere not found exception");
        }
        System.out.println("matiere data fetch from db is "+matiere.getTitle()+" "+matiere.getDescription()+" "+matiere.getIdMat());
        return ResponseEntity.ok(matiere);
    }

    @GetMapping("/getAllMatiere")
    public ResponseEntity<?> getAllMatiere() throws Exception{
        Set<Matiere> matieres=this.matiereService.getMatieres();
        if(matieres==null) {
            throw new Exception("there is no matiere in the database");
        }
        return ResponseEntity.ok(matieres);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateMatiere(@RequestBody Matiere matiere) {
        Matiere mat=this.matiereService.updateMatiere(matiere);
        return ResponseEntity.ok(mat);
    }

    @DeleteMapping("/admin/{idmat}")
    public ResponseEntity<?>deleteMatiere(@PathVariable("idmat") Long idmat) throws Exception {
        System.out.println("matiere to be deleted with request matiere id is: "+idmat);
        Matiere matiere=this.matiereService.getMatiereById(idmat);
        if(matiere==null) {
            throw new Exception("Matiere not found exception");
        }
        this.matiereService.deleteMatiere(idmat);

        return ResponseEntity.ok(matiere);
    }
}
