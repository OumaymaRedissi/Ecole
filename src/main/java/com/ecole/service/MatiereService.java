package com.ecole.service;

import com.ecole.domain.Matiere;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public interface MatiereService {
    public Matiere addMatiere(Matiere matiere);

    public Matiere updateMatiere(Matiere matiere);

    public Set<Matiere> getMatieres();

    public void deleteMatiere(Long idmat) throws Exception;

    public Matiere getMatiereById(Long idmat) throws Exception;

}
