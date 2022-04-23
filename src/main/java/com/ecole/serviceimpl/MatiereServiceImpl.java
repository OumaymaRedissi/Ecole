package com.ecole.serviceimpl;

import com.ecole.domain.Matiere;
import com.ecole.repository.MatiereRepository;
import com.ecole.service.MatiereService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Service@RequiredArgsConstructor
@Transactional
@Slf4j
public class MatiereServiceImpl implements MatiereService {
    @Autowired
    private MatiereRepository matiereRepository;
    @Override
    public Matiere addMatiere(Matiere matiere) {
        return this.matiereRepository.save(matiere);
    }

    @Override
    public Matiere updateMatiere(Matiere matiere) {
        return this.matiereRepository.save(matiere);
    }

    @Override
    public Set<Matiere> getMatieres() {
        return new LinkedHashSet<>(this.matiereRepository.findAll());

    }

    @Override
    public void deleteMatiere(Long idmat) throws Exception {
        this.matiereRepository.deleteById(idmat);
    }

    @Override
    public Matiere getMatiereById(Long idmat) throws Exception {
        Matiere matiere=this.matiereRepository.findById(idmat).get();
        System.out.println("Matiere:"+matiere.getTitle()+" "+matiere.getDescription()+" "+matiere.getIdMat());
        return matiere;
    }
}
